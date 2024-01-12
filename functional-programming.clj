;; Mathematics. and Functional programming in clojure. 

;; let me try to implement fibinocci series using clojure. 
;; input is n - which means I will have to print n fibinocci numbers 
;; starting from zero.

(defn fib 
  "Implementing Fibinocci series using recursion in clojure"
  [n]
  (loop [fib-series [0 1]]
    (if (= n (count fib-series))
      fib-series
      (let [ele (+ (nth fib-series (- (count fib-series) 1))
                   (nth fib-series (- (count fib-series) 2)))]
        (recur (conj fib-series ele))))))

(fib 9)
;; length of the output of fib functions depends on the input 
;; therefore it is wise to not use recur and use laziness instead. 


;; now let me try to implemnt the same stuff using repeatedly. 
;; i need two functions. 
;; first functions will add the sum of last two element of the vector.
;; one optmization I could have done to reduce the code is to use 
;; another function that adds last two number of a vector. 
;; let that functions be user defined function.

;; PERSISTENT DATA STRUCTURE
;; all clojure datastructure are persistant. What does this mean. 
;; suppose I am trying to change a data structure, then what happens is that 
;; a copy of that datastructure will be made due to immutability and my 
;; changes will be added on top of it. 
;; this is not the case in clojure. 
;; in clojure datastructures are persistent.
;; meaning the datastructure preserve the old copy among themselves. 
;; by efficient sharing of structure between new and old version. 
;; it is very complext to explain this concept. 

;; REALIZING the expression - evaluating lazy expression. 
;; REFERENTIAL TRANSPARITY - ability to replace result of a functions call

;; somthings to note here. 
;; when producing scalar value (which include fixed sized sequences) use recur. 
;; it is tail call optimized. 
;; when producing variable sized output, always be lazy. 

;; (leftn [(fname [*params] expr+)])
;; letfn is just like let but instead of variables it has functions. 









