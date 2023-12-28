;; things from programming clojure book 
(macroexpand-1 
  '(every? #(Character/isWhitespace %) "saurav"))

(defrecord Person [first-name last-name age])

;; (def person (Person. "John" "Cena" 55))
;; this syntax can be used but more 
;; idiomatic syntax would be 
(def person (->Person "John" "Cena" 55))

(prn person) ;; prints the record

;; some features 
(println (:first-name person))
(println (:last-name person))
(println (:age person))

(defn hello-world [username]
  (str (format "Hello %s" username)))

(hello-world "Saurav")

;; there is no for-loop in clojure but there is list comprehension

;; just remember this syntax its good. Its list comprehension.
;;(for [c compositions :when (= (:name c) "Requiem")] (:composer c))
;;-> ("W. A. Mozart" "Giuseppe Verdi")

(def compositions [{:name "Love story" :composer "Taylor swift"}
    {:name "I wanna be yours" :composer "Arctic Monkey"}
    {:name "black december" :composer "Taylor swift"}])

(for [c compositions
      :when (= (:composer c) "Taylor swift" )](:name c))

;; converting this clojure code to java just for my fun
;; (.start (new Thread (fn [] (println "Hello" (Thread/currentThread)))))

;; Much of clojure is written in clojure.
;; to view the source code of clojure. 
;; you can use the repl namespace 
(require '[clojure.repl :refer [source]])
;; (source reduce)

;; Prefix notation - The style of keeping the operands/functions first  
;; Infix notation - The normal way of using

;; clojure functions enforces arity. Otherwise Arity Exception 
;; will be called. 

;; here is very interesting implementation of the fact that
;; different arities of same function can call one another

(defn greeting
  "Returns a greeting of the form 'Hello username' 
  Default username is world"
 ([] (greeting "world")) 
 ([username] (str "Hello " username)))






