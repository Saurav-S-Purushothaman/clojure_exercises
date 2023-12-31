(defn binary-search
  "Finds earliest occurrence of target in arr (a vector) using binary search."
  ([arr target]
     (loop [low 0 high (unchecked-dec (count arr))]
       (if (<= high (inc low))
         (cond
           (== target (arr low)) low
           (== target (arr high)) high
           :else nil)
         (let [m (unchecked-add low (bit-shift-right (unchecked-subtract high low) 1))]
           (if (< (arr m) target)
             (recur (unchecked-inc m) high)
             (recur low m)))))))

(println (binary-search [1 2 3 4 5 7 8 9 10 11 12 13 14 15 16 17 19 20 21 22 23 24 25 26 28] 28))

