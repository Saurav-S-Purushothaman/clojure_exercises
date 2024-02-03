(def current-track (ref "Bruno Mars"))

;; (ref-set current-track "Cold Play") ;; this does not work.
(dosync (ref-set current-track "Cold Play"))

;; STM makes chaning states 
;; atomic - more than one refs are updated, result will have all the
;; refs updated. 
;; consistent - if any update fail, entire transaction is dropped. 
;; isolated - there are no partially completed results. 

;; lets see some complicated examples. 
(defrecord Message [sender text])
(->Message "Aaron" "Hello")

(def messages (ref ()))

;; this is bad way to implement. 
(defn naive-add-message [msg] 
  (dosync (ref-set messages (cons msg @messages))))

;; if you are trying to apply a function, use alter. 

(defn add-message [msg]
  (dosync (alter messages conj msg)))

