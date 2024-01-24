(defn factorial [n]
  (reduce * (range 1 (inc n))))

(defn factorial [n](reduce *(range 1(inc n))))
