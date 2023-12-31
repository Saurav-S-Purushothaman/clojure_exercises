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

;; destructuring 
;; 
;; (defn greeting
;;   [{:keys [fname lname]}]
;;   (println "Hell " fname))

;; the above code doesn't work. 
;; instead use this.
(defn greeting
  [{fname :first-name}]
  (println "Hello " fname))

(defn greet-author-2 [{fname :first-name}]
(println "Hello," fname))

(require '[clojure.string :as str])

(defn ellipsize [words]
(let [[w1 w2 w3] (str/split words #"\s+")]
(str/join " " [w1 w2 w3 "..."])))
(ellipsize "The quick brown fox jumps over the lazy dog.")

;; resolve 
;; it tells the symbol or var it resolves to in the current namespace. 
;; (resolve sym)
(resolve 'ellipsize)
;; namespace. 
;; best way to switch namespace is by using the command
;; (in-ns namespace-name)
(in-ns 'myapp)
;; when you are in new namespace always use this

(clojure.core/use 'clojure.core)

;; be default class name outside of java.lang must be fully qualified in clojure
;; for example 
;;(File/separator) ;; this throws an error.
;; you must use

(java.io.File/separator)

;; you should use import for importing java classes 
;; require for importing vars from another namespace.
;; syntax (import ('package class1 class2 ...))

;; different type of requre statement
(require 'clojure.string)
;; even if you import using this statement, you are still required to use 
;; the fully qualified name of the underlying methods of this namespace. 
;; one way to avoid that is to use alias
(require ['clojure.string :as str])
;; now you may use the alias to use the functions of clojure.string.
;; it is common practice to set namespace using ns command.
;; then use the additional :import, :require and :use commands to 
;; import java classes, and namespaces.
;; an eg. would be

;; (ns example.exploring
;;   (:require ['clojure.string :as str])
;;   (:import (java.io File )))


