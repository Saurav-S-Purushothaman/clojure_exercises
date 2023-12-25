;; binary search algorithm implemented in clojure.
(defn binary-search 
  "Binary-Search algorithm implemented in clojure"
  [arr target]
    (loop [left 0 right (count arr)]
      (if (>= left right) -1 
        (let [mid (/ (+ left right) 2)]
          (cond 
            (= target (nth arr mid)) mid 
            (> target (nth arr mid)) (recur (inc mid) right)
            :else (recur left (dec mid)))))))

(str "This is not good at the moment. Require some changes.")


(def vecs [1 2 3 4 5 6])
(def x (binary-search vecs 4))
(str "The value of x is " x )
