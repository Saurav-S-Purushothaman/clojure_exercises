(ns serpant-talk.talk
  (:require [camel-snake-kebab.core :as csk]))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn talk 
  [args]
  (csk/->snake_case args))


(def hello (csk/->snake_case "hello world"))

(def counter (ref 0))

(let [n 5]
  (future (dotimes [_ n] (alter counter inc)))
  (future (dotimes [_ n] (alter counter inc)))
  (future (dotimes [_ n] (alter counter inc))))

@counter

;; going to transfer 50 from account balance one to account balance 2 synchronously
;; using multi-thread

;; initial setup
(def account1 (ref 1000))
(def account2 (ref 2000))

(defn transfer [amt]
  (dosync (alter account1 #(- % amt)) (alter account2 #(+ % amt))))

(let [n 3] 
  (future (dotimes [_ n] (transfer 100)))
  (future (dotimes [_ n] (transfer 200))))

(str "The value of account 1 is " @account1 " and the value of account 2 is " @account2)

(defn binary-search [arr target] 
  (loop [left 0 right (count arr)]
    (if (<= left right) -1
      (let [mid (/ (+ left right) 2) mid-val (nth arr mid)]
        (cond (= mid-val target) mid (> mid-val target) (recur left (dec mid-val)) 
              :else (recur (inc mid-val) right))))))


(defn -main [& args]
  (println (talk (first args))))

