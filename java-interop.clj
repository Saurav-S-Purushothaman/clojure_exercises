;; new languages lack libraries for so many trivial task which makes developers life easy.
;; clojure has interoperability with java since its run on jvm.
;; time to learn java interop of clojure.

;; how to import java libraries and classes. How to create new instances of them. and how to use them? 

(class "caterpillar")
;; java.lang.String ->  string in java is an instance of java.lang.String. 
;; its the same in clojure as well.

;; in java we can transform this string to upper case using .toUpperCase() method. 
;; String myString = new String("caterpillar");
;; myString.toUpperCase() -> returns "CATERPILLAR"
;; in clojure you can use this 

(. "caterpillar" toUpperCase) ;; returns "CATERPILLAR"
;; so the syntax is dot(.) followed by the object, and then the method. (so cool)
;; there is also a shorthand way, use .ObjectMethodName and then the object
(.toUpperCase "caterpillar")

;; now what if the method takes argument
;; for example if we want to find the index of a string in another string you use method that takes arg
;; in java
;; String newString = String("cat");
;; int index = myString.indexOf(newString);

(.indexOf "caterpillar" "cat") ;; first arg is the arg with which we want to call the method and second one is the arg of the method

;; we can create instance of a method using two ways
;; using new 
(new String "Hello World!")
;; using dot
(String. "Hello World") ;; remember to initialize a object the dot should be at the end 
;; to use a method over the object . should be at begining.
(.toUpperCase (String. "hello World!"))

;; we can use java classes using their fully qualified name
;; we can also use them by importing. 
;; for eg. 
(.getHostName (java.net.InetAddress/getByName "localhost"))

;; doto macro 

;; clojure.core/doto ([x & forms]) Macro
;;   Evaluates x then calls all of the methods and functions with the
;;   value of x supplied at the front of the given arguments.  The forms
;;   are evaluated in order.  Returns x.
;; 
;;   (doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2))

;; if we need to mutate a java object in a series of steps, then we can use this
(def sb (doto (StringBuffer. "Who") (.append " are") (.append " you?")))
(str sb)

;; doto syntax is much nicer and easier to read.
(def sb2 (.append (.append (StringBuffer. "Who") "Are") "You"))
(.toString sb2)


;; generating UUID 
(import 'java.util.UUID)
(UUID/randomUUID)

;; POLYMORPHISM
;; lets say you want a function that behaves differently with different input

(defn who-are-you [input]
  (cond
    (= java.lang.String (class input)) "You are a string" 
    (= java.lang.Long (class input)) "You are a long"
    (= clojure.lang.Keyword (class input)) "You are keyword"
    :else "I don't know who you are"))

(who-are-you :alice) ;; returns "You are a keyword"
(who-are-you "Alcie") ;; returns "You are string"
(who-are-you 112) ;; returns "You are a long"
(who-are-you 1.2) ;; returns "I don't know who you are"















