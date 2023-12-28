(println "Hello world")
(Thread/sleep 1000)
(println "hello world again")

;; this was an interesting program.
(defn say-hello-ten-times[]
  (loop [counter 0]
    (when (< counter 10)
      (do 
        (println "INFO: Try number = " counter)
        (Thread/sleep 2000)
        (recur (inc counter))))))
