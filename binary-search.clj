(defn binary-search [arr target] 
  (let [cnt (- (count arr) 1)]
   (loop [left 0 right cnt]
     (if (>= left right) nil 
       (let [mid (/ (+ left right) 2) mid-val (nth arr mid)]
         (cond 
           (= mid-val target) mid-val
           (> mid-val target) (recur (inc mid) right)
           :else (recur left (dec mid))))))))

(def vec [1 2 3 4 5 6])
(def target 4)
