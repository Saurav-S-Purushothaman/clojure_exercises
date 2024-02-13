;; RECURSION

;; just for the matter of fact. 
;; factorial in clojure
(defn factorial [n] 
  (if (= n 1) 
  1 
  (* n (factorial (- n 1)))))

;; a scenario we want to turn this vector 
;; ["normal" "too small" "too big" "swimming"]
;; into 
;; ["Alice is normal" "Alice is to small" "Alice is too big" "Alice is swimming"]
;; doing this using recursion
(def adj ["normal" "too small" "too big" "swimming"])

(defn alice-is [in out] 
  (if (empty? in) out 
  (alice-is (rest in) (conj out (str "Alice is " (first in))))))
;; converting this function with loop

(defn alice-is-loop [input] (loop [in input out []] 
  (if (empty? in) 
     out 
  (recur (rest in) (conj out (str "Alice is " (first in)))))))

;; let me see if I can convert this into idiomatic way
;; (defn factorial [n] 
;;   (if (= n 1) 
;;   1 
;;   (* n (factorial (- n 1)))))

(defn factorial2 [n] 
  (loop [i n f 1] 
    (if (= i 1) f 
      (recur (dec i) (* f i)))))  

;; lets go through each iteration for this function factorial2
;; at firts, i is 4 and f is 1 
;; i : 4
;; f : 1
;; it passess the if condition. its false 
;; the recur will jump back to the loop with
;; 
;; f --> f*i = (4*1) -> 4
;; i --> 3
;; passes the if condition
;; f --> f*i (4*3)
;; i -->2 
;; passes the if condition
;; f --> f*i (4*3*2)
;; i --> 1
;; doesnt pass the if condition, if condition is true
;; returns f ((4*3*2))

;; NOTE: Always use recur for recursion because its Tail Call Optmized(TCO) by clojure 

;; iterate function in clojure. 
;; its used to generate infinite sequence by repititively 
;; applying function to its starting value 
;; syntax -> (iterate function starting-value)
;
