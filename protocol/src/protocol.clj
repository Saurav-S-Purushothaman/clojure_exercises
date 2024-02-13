(ns protocol
  (:import (java.io File FileInputStream FileOutputStream
                    InputStream InputStreamReader
                    OutputStream OutputStreamWriter
                    BufferedReader BufferedWriter)
           (java.net Socket URL)))

(defn make-reader [src]
  (-> src 
      FileInputStream. InputStreamReader. BufferedReader.))

(defn make-writer [src]
  (-> src
      FileOutputStream. OutputStreamWriter. BufferedWriter.))

(defn gulp [src]
  (let [sb (StringBuilder.)]
    (with-open [reader (make-reader src)]
      (loop [c (.read reader)]
        (if (neg? c)
          (str sb)
          (do
            (.append sb (char c))
            (recur (.read reader))))))))


(defn expectorate [dst content]
  (with-open [writer (make-writer dst)]
    (.write writer (str content))))

; implementing interface in clojure
; (definterface IOFactory
;   (^java.io.BufferedWriter make-writer [this])
;   (^java.io.BufferedReader make-reader [this]))

; means IOFactory should implement the two methods and the return type of those methods should be 
; as specified by the macro. 

; protocol
; how to create a protocol
; (def protocol name & opt + sings) 

; redifining IOFactory
(defprotocol IOFactory
  "Protocol for things that can be read from and written to"
  (make-reader [this] "Creates Buffered reader")
  (make-writer [this] "Creates Buffered writer"))

; this is the complete version of protocol. 

(extend-protocol IOFactory
  InputStream
  (make-reader [src] 
    (-> src InputStreamReader. BufferedReader.))
  (make-writer [dst] 
    (throw (IllegalArgumentException. "Can't open input stream.")))
  
  OutputStream
  (make-reader [src]
    (throw (IllegalArgumentException. "Can't open as output stream")))
  (make-writer [dst]
    (-> dst OutputStreamWriter. BufferedWriter.))
  
  File
  (make-reader [src]
    (make-reader (FileInputStream. src)))
  (make-writer [dst]
    (make-writer (FileOutputStream. dst)))
  
  Socket
  (make-reader [src]
    (make-reader (.getInputStream src)))
  (make-writer [dst]
    (make-writer (.getOutputStream dst)))
  
  URL
  (make-reader [src]
    (make-reader
      (if (= "file" (.getProtocol src))
        (-> src .getPath FileInputStream.)
        (.openStream src))))
  (make-writer [dst]
    (make-writer 
      (if (= "file" (.getProtocol dst))
        (-> dst .getPath FileInputStream.)
        (throw (IllegalArgumentException. "Can't write to non file URL"))))))
