;; FUNCTION CREATING OTHER FUNCTION AND NEAT EXPRESSION CLOJURE
;; currying - it is the process of converting a function that takes multiple arguments into a chain of single argument funciton.

(defn grow [name direction]
  (case direction
    :small (str name " is growing smaller.")
    :large (str name " is growing larger.")
    "Unrecognized input"
    )
  )

;; we can convert this mult arg function to single arg
((partial grow "Alice") :small)
((partial / 1) 2) ;; returns 1/2

;; if we want to combine multiple function into one function then you can use comp.
;; so what is composition
;; in mathematical tersm FoG(x) = F(G(x)) ;; this is called composition

(defn toggle-direction [direction]
  (cond 
    (= direction :small) :big
    (= direction :big) :small
    :else :error
    )
  )

;; another funciton 
(defn oh-my [direction] 
  (str "Oh my, you are growing " direction)
  )

;; lets say you want to combine both these functions then you use this 
(oh-my (toggle-direction :big));; returns Oh my, you are growing :small" 
;; but there is another way to do this, thats using comp

((comp oh-my toggle-direction ) :small) ;; this returns "Oh my, you are growing :big"
;; also you can wrap this under a new function if you want

(defn surprise [direction]
  ((comp oh-my toggle-direction) direction)
  )

;; some more practice with partial 

(defn adder [x y] 
  (+ x y))

(def adder-3 
  (partial adder 3))

(adder-3 5) ;;retruns 8