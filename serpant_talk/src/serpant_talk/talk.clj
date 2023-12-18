(ns serpant-talk.talk
  (:require [camel-snake-kebab.core :as csk]))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

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

