(ns serpant-talk.talk
  (:require [camel-snake-kebab.core :as csk]))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def hello (csk/->snake_case "hello world"))
