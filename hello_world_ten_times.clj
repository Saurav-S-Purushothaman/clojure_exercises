;; printing hello world ten times with their index in clojure

(defn print-hello [n]
  (loop [i 0]
    (println ((str i) ". Hello world"))
    (when (< i n) (recur (inc i)))))
