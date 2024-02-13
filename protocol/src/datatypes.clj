(ns datatypes
  (:require [ protocol :refer [IOFactory make-reader make-writer]] )
  (:require [clojure.java.io :as io])
  (:import (java.security KeyStore KeyStore$SecretKeyEntry KeyStore$PasswordProtection))
  (:import (javax.crypto KeyGenerator Cipher CipherOutputStream CipherInputStream))
  (:import (java.io File FileInputStream FileOutputStream
                    InputStream InputStreamReader
                    OutputStream OutputStreamWriter
                    BufferedReader BufferedWriter)))

(deftype CryptoVault2 [filename keystore password])
; now to create an instance of CryptoVault2
(def my-first-instance (->CryptoVault "vault1" "my-keystore" "secret-secret"))
; the fields can be accessed using the same prefix .symbols 
(.filename my-first-instance)
(.keystore my-first-instance)
(.password my-first-instance)

; now that fields have been created, lets create behavior. 
; datatypes can only implement the fields 

(defprotocol Vault
  (init-vault [vault])
  (vault-ouptut-stream [vault])
  (vault-input-stream [vault]))


; now let us create the vault datatype sample that 
; has fields and behaviors 
(defn vault-key [vault]
  (let [password (.toCharArray (.password vault))]
    (with-open [fis (FileInputStream. (.keystore vault))]
      (-> (doto (KeyStore/getInstance "JCEKS")
            (.load fis password))
          (.getKey "vault-key" password)))))

(deftype CryptoVault [filename keystore password]
  Vault 
    (init-vault [vault]
      (let [password (.toCharArray (.password vault))
            key (.generateKey (KeyGenerator/getInstance "AES"))
            keystore (doto (KeyStore/getInstance "JCEKS")
                       (.load nil password)
                       (.setEntry "vault-key"
                                  (KeyStore$SecretKeyEntry. key)
                                  (KeyStore$PasswordProtection. password)))]
        (with-open [fos (FileOutputStream. (.keystore vault))]
          (.store keystore fos password))))  
    (vault-ouptut-stream [vault]
      (let [cipher (doto (Cipher/getInstance "AES")
                     (.init Cipher/ENCRYPT_MODE (vault-key vault)))]
        (CipherOutputStream. (io/output-stream (.filename vault)) cipher)))
    (vault-input-stream [vault]
      (let [cipher (doto (Cipher/getInstance "AES")
                     (.init Cipher/DECRYPT_MODE (vault-key vault)))]
        (CipherInputStream. (io/input-stream (.filename vault)) cipher)))
    
    IOFactory
     (make-reader [vault] 
       (make-reader (vault-input-stream vault)))
     (make-writer [vault]
       (make-writer (vault-input-stream vault))))

