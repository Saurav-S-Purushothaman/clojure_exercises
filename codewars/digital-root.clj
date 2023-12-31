(defn check-sum  
  [args]
  (->> args (str) (map #(Integer/parseInt (str %))) (into [])(reduce +)(str)))

(defn digital-root
  [n]
  (loop [numbers n]
    (let [sum (check-sum numbers)]
      (if (= (count sum) 1) (Integer/parseInt sum)(recur sum)))))

(- 237000 90000)
