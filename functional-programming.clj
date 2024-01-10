;; Mathematics. and Functional programming in clojure. 

;; let me try to implement fibinocci series using clojure. 
;; input is n - which means I will have to print n fibinocci numbers 
;; starting from zero.

;; lets make the algorithm. Let me use vs code instead of this. To try the effectiveness

;; first let me create the plan for executions
;; I will return a vector for sure. 

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


;; now let me try to implemnt the same stuff using repeatedly. 
;; i need two functions. 
;; first functions will add the sum of last two element of the vector.
(defn add-last-two [my-vec]
  (+ (nth my-vec (- (count my-vec) 1) )(nth my-vec (- (count my-vec) 2) )))

(comment 
  (add-last-two [12 2 23 4])
  )

(repeatedly 9 #(conj my-vec (add-last-two my-vec)))
