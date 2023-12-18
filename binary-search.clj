;; binary search algorithm implemented in clojure.
(defn binary-search [arr target]
  (loop [left 0 right (count arr)]
    (if (>= left right) nil 
      (let [mid (/ (+ left right) 2)]
        (cond
          (= target (nth arr mid)) mid
          (> target (nth arr mid)) (recur (inc mid) right)
          :else (recur left (dec mid)))))))

(def vecs [1 2 3 4 5 6])
(def x (binary-search vecs 4))
(str "The value of x is " x )
