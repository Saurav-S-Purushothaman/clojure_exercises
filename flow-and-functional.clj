;; third chapter of the book
;; an expression is code that can be evaluated to a result
;; a form is a valid expression
(first [:a :b :c]) ;; this is an expression and this is a vlid form
(first) ;; this is not a valid form 

;; how to determine whether something is true or false
(true? true) ;; return true
(true? false) ;; return false
(false? false) ;; return true
;; clojure convention if the result returns a boolean then its named with question mark at the end. 

;; how do we test if soemthing is not there.
(nil? nil) ;; return true
(nil? 1) ;; return false

;; how to check negation. 
(not true) ;; return false
(not false) ;; return true 
;; interesting note: nil is considered false in logical test, so negation of nil will return true
(not nill) ;; return true
(not "hi") ;; return false, not of any other value will return false

;; how to comapre values, use the = function
(= :thanks :thanks) ;; return true
(= 4 "4") ;; return false
(= '(1 2) [1 2]) ;; return true, can compare different collections
(not= 1 1) ;; returns false

;; Logic tests that can be used on Collection 

(empty? [:table :docker]) ;; return false
(empty? '()) ;; return true
;; actual definition of empty 
(defn empty? [col1]
  (not (seq col1))
;; here (seq col1) returns nil 
;; not nil returns true as nil is logical false in clojure
;; seq is an abstraction that the collection data structures make use of 
;; it has in built methods like first conj cons etc.
;; when these methods are called we don't need to convert the collection datastructure to sequence
;; its alredy part of the implementation of these struct. 
;;  seq of non empty collection returns nil
;; so to check non empty status use 
(seq '()) ;; return nil ;; and not 
(not (empty? '()))
;;
;; how to check if something is true for every element in collection
;; use every? - it takes predicate to test and the collection data as its argument
;; for eg. 
(every? odd? [1 2 3])
;; predicate is a function that returns a value in logic test. 
;; we can create our own predicate 
;; we can use named function or ananymous function to create predicate 

(defn is-drinkable? [x] ;; not its idiomatic to use ? for function that returns boolean
  (= x :drinkable) 
  )

(every? is-drinkable? [:drinkable :drinkable]) ;; returns true
;; using anyonymous fucntion
(every? (fn [x] (= x :drinkable)) [:drinkable :drinkable])
(every? #(= % :drinkable) [:drinkable :drinkable])
;; not any is the exact opposite of every? 
(not-any? #(= % :drinkable) [:poison :poison]) ;; returns true
;; what if we want to test if some of the elements pass our logic test. 
;; we have some function for that. But it behaves a little differently. It returns true for the  first value that matches the logic and nil otherwise
#(= % :drinkable) ;; this is same a s
(defn drinkable? [x] 
  (= x :drinkable)
some #(= % :drinkable) [:poison :drinkable]);; returns :drinkable
(some #(> % 3) [ 1 2 3 4 5 6]);; return true 
;; set - remember - non-nil value of the predicate is also considered true
(some #{3} #{1 2 3}) ;; return 3 
(some #{4 5} #{ 1 2 3 4}) ;; return 4 (the first matching value)
(some #{3} #{ 1 2}) ;; returns nil
;; an interesting scenario, the following return nil
(some #{nil} #{nil nil nil nil}) ;; return nil
(some #{nil} [false false false false]) ;; return nil

;; HARNESSING THE POWER OF CONTROL FLOW
;; if takes 3 param, 1st - predicate 2nd - expression if predicate return true 3rd - expression if predicate return false
(if true "it is true" "it is false") ;; returns "it is true"
(if false "it is true" "it is false") ;; retunrs "it is false"
;; nil is logical false
(if nil "it is true" "it is false");; returns "it is false"

;; combining let and if 
;; normal usage
(let [need-to-grow (> 5 3)]
  (if need-to-grow
  "Need to grow"
  "No need to grow"))
;; there is a better way, we can combine if and let 
(if-let [need-to-grow (> 5 3)]
  "Need to grow"
  "No need to grow")
;; suppose you don't want else condition then use when 
defn drink [need-to-grow] 
  (when need-to-grow "Drink Bottle")
(drink true) ;; this will return "Drink Bottle"
(drink false) ;; this will return nil
;; similar to if-let there is also a when-let 
;; to check for multiple condition use cond
;; syntax 
;; (cond 
;;   (exp1 bool) return value1
;;   (exp2 bool) return value2:else default return value
(defn check_greater_than_ten [x]
  (cond
    (> x 10) "Yes it is greater 10"
    (> x 5) "No but its greater than 5"
    :else "Its less than 10 and 5"
    )
  )

;; if there is only equal to operator in checking a condition then it is better to use case statements

(defn check_bottle [x]
  (cond
   (= x :bottle) "This is not a bottle" 
   (= x :bottles) "But these are bottles"
   (= x :bottle) "This is a bottle"
   :else "There are not bottles in the list" ))

(defn check_bottle [x]
  (case x
    "Poison" "Don't touch"
    "Drinkme" "Super tasty"
    "default" "Unkown"
    )
  )

(defn categorize-string [x]
  (case x
    "apple" "It's a fruit!"
    "banana" "It's another fruit!"
    "carrot" "It's a vegetable!"
    "default" "Input not recognized"))


