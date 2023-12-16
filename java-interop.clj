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
