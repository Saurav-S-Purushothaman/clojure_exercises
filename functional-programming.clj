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

(defn tail-fibo [n]
  (letfn [(fib
           [current next n]
           (if (zero? n)
             current
              (fib next (+ current next) (dec n))))]
         (fib 0N 1N n)))


;; lets talk about lazy seq in clojure
(defn generate-numbers [n] 
  (lazy-seq 
    (cons n (generate-numbers (+ n 1)))))

(take 5 (generate-numbers 1))

;; (defn lazy-seq-fibo
;; ([] (concat [0 1] (lazy-seq-fibo 0N 1N)))
;; ([a b]
;; (let [n (+ a b)]
;; (lazy-seq
;; (cons n (lazy-seq-fibo b n))))))

;; i can write this much more simpler manner. 
;; easiest way to create a fib series using functional programming style is 

(defn generate-fib [a b] 
  (lazy-seq
    (cons a (generate-fib b (+ a b)))))

(def fib-sequence (generate-fib 0 1))
(take 10 fib-sequence)
;; this is absolutely marvelous. 
;; now lets make this a teeny tiny bit complex.
(defn lazy-in-fib 
  ([] (concat [0 1] (lazy-in-fib 0 1)))
  ([a b]
   (lazy-seq 
     (cons (+ a b) (lazy-in-fib b (+ a b))))))

(take 5 (lazy-in-fib 0 1))
(take 5 (lazy-in-fib))
;; (take 5 (iterate (fn [[a b]] [b (+ a b)]) [0 1]))

;; I need to write and understand actually what is happening here. 
(take 5 
  (iterate 
    (fn [[a b]] [b (+ a b)]) 
    [0 1]))

;; creating fibinocci series in clojure
(defn func-fib [n] (take n (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1]))))
(func-fib 5)

;; lets do a coin tose problem 
;; [:h :t :h :t :t :h :h]
;; we need to figure out a way to become lazy and do this problem. 
;; it will be easy to do this problem using recur. 
;; for example
(defn find-head-count [colls]
  (loop [cnt 0 colls colls]
    (if (empty? colls) 
      cnt
      (recur (if (= :h (first colls) (second colls)) (inc cnt)
               cnt) (rest colls)))))


(def coin-toss 
 [:h :t :h :t :t :h :h])

(find-head-count coin-toss)
;; this is the pretty much the straight forward and correct way to implement this stuff. 
;; but we can also be lazier than lazy. 
;; compartmentalization. 

;; how to think.  
;; how to make this lazy, 
;; think, clojure seq library almost always interact with each element of the sequences
;; so lets transform the seq such that each element will have its immediate neighbour to 
;; the side. 
(defn by-pairs [coll]
  (let [take-pair (fn [c] (when (next c) (take 2 c)))]
    (when-let [pairs (seq (take-pair coll))]
      (cons pairs (by-pairs (rest coll))))))

;; this gives the result ((:h :t) (:t :h) (:h :t) (:t :t) (:t :h) (:h :h))

;; now let us write the implementation of the problem.
(defn head-count-fnl [colls] 
  (count (filter (fn [c] (every? #(= :h %) c)) 
                 (by-pairs colls))))


(head-count-fnl coin-toss) ;; returns 1 which is the answer. Fantastic. 

;; to make things even simpler, we can make use of the partition function of sequences. 
;; syntax: partiton

;; we can achieve the same result using partition. 
;; (partition size step vec) 
;; step is optional but its sort of like windowing. 
(partition 2 1 [:h :t :h :t :t]) ;; would give the same result. 

;; function composition. 
;; syntax (comp f & fs) - right most function will be applied to its arg. 
;; then the second right most function will be applied to its result and so on. 

(def count-if 
  (comp count filter))

(count-if odd? [1 2 3 4 5])

;; now we can use the count-if function and partition feature combined 
;; to create count-runs function. 
(defn count-runs
  "Count the runs of n, where pred is true in coll" 
  [n pred coll]
  (count-if #(every? pred %) (partition n 1 coll)))

;; this is more generalised.
;; if we need to check if there were any three heads in a row?
(count-runs 3 #(= :h %) [:h :h :h :h :h :h :t :h :h])

;; if we suddenly want tail count,  then 
(count-runs 3 #(= :t %) [:h :h :h :h :h :h :t :h :h])

;; currying and partial applications. 
;; if you still want partials in your functions. 

;; if you still want the name, then you can implement using partial.
(def count-heads (partial count-runs 1 #(= :h %)))

;; lets understand what is trampoline. 
;; if the result of something is a function, then we can use trampoline. 
;; which calls that function and go on until the result of the function 
;; is a scalar value. 

;; eager functional prorgramming. 
(defn square [x] (* x x))

(defn sum-of-square-seq [n]
  (vec (map square (range n))))

;; this will take more mem. 
;; range takes a sequence mem 
;; map takes another seq mem. 
;; vec takes another seq mem. 
;; remember all seq operations are cached, so mem will be taken. 

;; alternative good approach for this problem is 

(defn sum-of-square-best [n]
  (into [] (map square)  (range n)))

;; here map square function is applied to each elemment of the input functions
;; so there is no extra need of stack. 

;; best approach to optimize multiple transducers is to use the comp function.
;; comps take a set of fns and returns a fn that is composition of all those fns. 

;;  the key and most important learning from functional programming is this. 
(defn functional-fib [n]
  (take n (first (iterate (fn [[a b]] [b (+ a b)]) [0 1]))))

(functional-fib 9)