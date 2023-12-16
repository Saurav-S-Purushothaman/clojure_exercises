;; Learning Clojure
;; ----------------
;; - REPL - Read Eval Print Loop.
;; - Values/literals - expression that evaluates to themselves.
;; - Ratios are not evaluated to decimal points 
;;   for eg. 1/2 -> 1/2
;;           2/4 -> 1/2
;; 
;; Operator goes first, followed by the value.
;; for eg. to divide:
;;     (/ 1 3) -> 1/3
;; 
;; keywords name start with colon
;; :jam

;; string - "text" ;;strings are declared in quotes 
;; char - \t ;;characters are declared with back slash infrom
;; boolean - true,false 
;; null - nil
;; collections
;; -----------------
;; list - '(1 2 3 4) ;;just add ' in front
;;     - (list 1 2 3 4) ;; this also works
  ;; how to access list 
  ;; use the first function -> returns the first element of the list
(first '(1 2 3 4))
  
  ;; use the second function -> returns the rest of the element of the list
  ;; you can also nest first and rest functions to get a particular element of the list 
(first (rest (rest '(1 2 3 4))))

  ;; how to add an elemet into a list 
  ;; use the cons function -> takes two argument, first the element to be added
  ;; and then the list to which we want to add the element
  cons(5 '()) 
  ;; this is similar to cons(5 nil)

;; Vectors
;; ----------
;; vectors can be accessed by index
[:jar1 1 2 3 4 :jar2]
;; The first and rest operator works on vectors 

;; accessing useing index 

(nth [:jam 1 2 3 4 :jam2] 0) ;; returns :jam 

(last  [:jam 1 2 3 4 :jam2] 0) -> returns :jam2 ;; last function is used to return the last element of a list.


;; LAST and NTH ALSO WORK ON LIST BUT BETTER INDEX ACTION PERFORMANCE ON VECTORS 
;; reason - list has to go throught the begining to reach the index it wants, but vectors can directly access that index. Cool

;; Collections in Clojure
;; ----------------
;; All collections are immutable and persistant. 
;; immutable - means cons return new collection instead of modifying the collection. 
;; persistant - means 
;; 
;; count - returns the size of the collection. 

(count [1 2 3 4]) -> returns 4 
;; difference between conj and cons -> cons is specifically for list to add element to the begninng of the list. 
;; think of conj like append in python.
;; conj add element in the most natural way of that data structure
;; for example for list it adds at the begining and for vector, it adds at the end.
(conj [1 2 3 4] 2 3 4) -> returns [1 2 3 4 2 3 4]

;; Maps
;; ----
;; key value 
{:jam1 "apple", :jam2 "mixed"}
{:jam1 "apple" :jam2 "mixed"}
;; both works

;; how to get value out of map 
;; get takes two arguments - first one the map, second is the key
(get {:jam1 "apple" :jam2 "mixed"} :jam1)

;; if the key is not present, then it will return nil. 
;; But you can use another argument at the end, it will return that argument if the key is not found
;; But the best way to get a value from a map is by not using the get function. instead use the key as the function.

(:jam1 {:jam1 "apple" :jam2 "mixed"} :jam1}) -> return apple

;; keys and vals function 
(keys {:jam1 "apple" :jam2 "mixed"} ) ;; -> return (:jam1 :jam2) -> ;;this isn't a list, its a APersistentMap$KeySeq
(vals {:jam1 "apple" :jam2 "mixed"} ) ;; -> return ("apple","mixed") ;;-> this isn't a list, its a APersistentMap$ValSeq

;; to update and delete element in Maps
(assoc {:jam1 "apple" :jam2 "mixed"} :jam1 "Apple") ;; -> return {:jam1 "Apple" :jam2 "mixed"} 
(dissoc {:jam1 "apple" :jam2 "mixed"} :jam1 ) ;; -> return { :jam2 "mixed"} 

;; to add an element - you can use to conj - it adds element in the most natural way
(conj {:jam1 "apple"} {:jam2 "mixed"}) ;; -> returns {:jam1 "apple", :jam2 "mixed"}

;; same thing can be achieved using the merge functions, infact its idiomatic that way
(merge {:jam1 "apple"} {:jam2 "mixed"} {:jam3 "orange"}) ;; returns {:jam1 "apple" :jam2 "mixed" :jam2 "orange"}

;; SETS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; sets are collection of element with not duplicates 
;; you can perform set operations like union intersection difference
;; union 
(clojure.set/union #{:r :b :w} #{:w :p :y}) ;; returns  #{:y :r :w :b :p}
(clojure.set/difference #{:r :b :w} #{:w :p :y}) ;; returns  #{:r :b} 
(clojure.set/intersection #{:r :b :w} #{:w :p :y}) ;; returns  #{:w} 
;; for set keyword can be used as function
;; get can be used as a function 
;; set itself can be used as a function
(:rabbit #{:rabbit :dear :dog}) ;; returns :rabbit
(get #{:rabbit :dear :dog} :rabbit ) ;; returns :rabbit
(#{:rabbit :dear :dog} :rabbit ) ;; 
;; contains function to see if an element is there in a set. it can also be used with maps
(contains? #{1 2 3 4} 1) ;; retunrs true 
;; convert other datastructure to set using the set funciton 
;; even map can be converted to set
(set [1 2 3 4 5]) ;; returns #{1 2 3 4 5}
(set {:jam "apple", :jam2 "mixed"}) ;; returns #{[:jam "apple"] [:jam2 "mixed"]}
;; to add an element use conj
;; to remove an element use disj
(conj #{1 2} 3) ;; returns #{1 2 3}
(disj #{1 2} 2) ;; returns #{1}

