;; LAZINESS OF CLOJURE
;; -------------------------------------
(take 5 (range))
;; take function takes two argument, the number of elements to take and the sequence
;; calling range returns a lazy sequence, you can specify the end of the range with a parameter
(range 5) ;; this too returns the same value
(take 3 (range 2))
;; range works just like how python's range work
(range 1 10 2)    ; returns (1 3 5 7 9)
(range 5)         ; Generates (0 1 2 3 4)
(range 2 7)       ; Generates (2 3 4 5 6)
(range 0 10 2)    ; Generates (0 2 4 6 8)
;; NOTE : Do not evaluate (range) in your repl, it will crash

;; other ways to generate infinite sequences
;; using repeat function.
;; it takes two args, one is the number of times the element needed to be repeated and other one is 
;; the value to be repeated.
(repeat 3 "rabit")
(rand-int 10) ;; create random number between 0 and ten
(repeat 3 (rand-int 10)) ;; returns (n n n) n being a number inside 10
;; difference between repeat and repeatedly
(repeatedly 3 (rand-int 10)) ;; this doesn't work, repeatedly takes function with no argument 
;; so inorder to achieve the output you should wrap (rand-int 10) inside a function
;; using anonymous function is one of the best way to do it.
(repeatedly 3 #(rand-int 10)) ;; returns (n1 n2 n3)

;; NOTE : repeat and repeatedly without any argument creats an infinite sequence of number
(take 5 (repeatedly #(rand-int 10)))

;; anothe way to create an infinite sequence
(take 5 (cycle ["big" "small"]))
;; NOTE: if cycle is not handled, then the system will crash

;; the rest function will return a lazy sequence when it is operated on a lazy sequence








