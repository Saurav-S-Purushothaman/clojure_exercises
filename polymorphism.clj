;; lets say you want to make the function to behave different way based on the input of the funciton, then use polymorphism

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

;; we can experss the same thing using multimethod.
;; first we need to specify the multi method and then we need to create function to dispatch the method.

;; (defmulti name dispatch-fn) -> based on the dispatch function we can create different methods
(defmulti who class)

;; this is how you create a method
;; (defmethod name match
;;  [args]
;;  )

(defmethod who java.lang.String [args]
  (str "You are string" args))

(defmethod who java.lang.Boolean [args]
  (str "You are a boolean"))


(defmethod who clojure.lang.Keyword [args]
  (str "You are keyword" args))

(defmethod who java.lang.Long [args]
  (str "You are long" args))


(who :alice) ;; returns "You are a keyword"
(who "Alcie") ;; returns "You are string"
(who 112) ;; returns "You are a long"
;; (who 1.2) ;; returns error

;; so inorder to avoid error, we can use default input value in the method

(defmethod who :default [args]
  (str "I don't know who you are." args))

(who 1.2)


(defmulti greater #(> % 3))

(defmethod greater true [_]
 (str "The number is greater than 3") )

(defmethod greater false [_]
 (str "The number is less than 3") )

(greater 5)
(greater 0)

;; PROTOCOL another way to use polymorphism in clojure is to use protocol multi methods are useful to use when there is only one function.
;; but if group of funcitons require polymorphism, then protocol is better
(defmulti eat-mushroom #((if (< % 3) :grow :shrink)))

;; learn protocol later

;; how do we create a class that has its own field in clojure
;; the way to do it is by using the defrecord function

(defrecord Mushroom [color height])

(def regular-mushroom (Mushroom. "White" 2)) ;;remember to use . (dot)
(class regular-mushroom) ;; user.Mushroom

;; we can access the value latter using .- symbol
(str
(.-color regular-mushroom)
(.-height regular-mushroom))
