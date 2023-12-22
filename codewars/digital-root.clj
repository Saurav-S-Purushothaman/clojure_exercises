(defn check-sum  [args]
  (->> args 
      (str)  
      (map #(Integer/parseInt (str %)))
      (into [])
      (reduce +)
      (str)))


(defn digital-root
  [n]
  (loop [number n]
    (let [sum (check-sum number)]
      (if 
        (= (count sum) 1) (Integer/parseInt sum)
          (recur sum)))))
