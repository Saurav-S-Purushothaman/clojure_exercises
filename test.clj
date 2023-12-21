(def print-hello 
  #(println "Hello " %))

(def print-hello2 (fn [args] 
    (println "hello " args)))

(let [n 4]
  (println n))
