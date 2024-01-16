(ns serpant-talk.talk
  (:require [camel-snake-kebab.core :as csk]))


(defn foo
  "I don't do a lot."
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

((let [new-binding binary-search]
   new-binding) [1 2 3 4] 5)
(macroexpand-1 ('binary-search [1 2 3 4] 12))




(println "Hello world this is so cool")

(defn binary-search [arr target]
  (loop [left 0 right (count arr)]
    (let [mid (/ (+ left right) 2) mid-val (nth arr mid)]
      (cond 
        (<= left right) -1
        (= mid-val target) mid
        (< mid-val target) (recur left (dec mid))
        :else (recur (inc mid) right)))))

(println "Hello world!")
(+ 1 2)

(macroexpand-1
 '(defn l [] (load-file "talk.clj")))

(comment
  "All these thing will be ")


;; hello world macro when called with a name prints 
;; "hello {name} to the  world" in the repl

(defmacro hello-world [name]
  `(defn hello []
     (str "Hello " ~name " to the world!")))

;; unfortunately this macro isn't working

(defmacro if-nots [condition expression]
  (list 'if condition nil expression ))

(if-nots (> 3 5) (println "Hello world!"))

(/ (+ 1 2) 3)

(defn generate-fib [a b]
  (lazy-seq
    (cons a (generate-fib b (+ a b)))))

(def fib-sequence (generate-fib 0 1))
(take 5 (generate-fib 0 1))


;; lazy method of fibinocci series. 
(defn generate-fib [a b]
  (lazy-seq
    (cons a (generate-fib b (+ a b)))))

(take 5 (generate-fib 0 1))

;; even lazier method. 
(defn lazier-lazier-fib [n] 
  (take n (map first (iterate (fn [[a b]] [b (+ b a)]) [0 1]))))












