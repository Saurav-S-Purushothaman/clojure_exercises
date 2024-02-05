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

;; stm uses multiversion concurrency control
;; if you do not care if any transaction has altered the state of your object, 
;; then you can use commute instead of alter. 
 
;; alter uses multiversion concurrency control (mvcc) 
;; commute doesn't care about the current state. 

;; agent 
;; once you have created an agent, you can send your agent for an updated task. 
;; you can see that vars created are global. 
;; that is for each thread we use, the value stored in vars will be the same. 

;; but we can create thread local binding for vars using the binding macro. 
;; structurally it looks like a let.

;; vars indented for dynamic bindings are called spatial variables. 





