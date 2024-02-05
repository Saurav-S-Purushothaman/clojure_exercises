(ns core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(s/valid? string? "a string")

(def short-string? (s/and string? #(< (count %) 5)))

(s/valid? short-string? "and")

(def col-of-short-string? (s/coll-of short-string?))

(s/valid? col-of-short-string? ["this" "that" "mine"])

;; now we want something to be either a string or a number inside a list, if string, then it should be short string. 
;; and? does not require any keyword but or do require keywords
(def number-or-short-string? (s/or :short short-string? :integer int?))
(def coll-of-num-short? (s/coll-of number-or-short-string?))
(s/explain coll-of-num-short? [1 "this" 2 "that is not true"])

;; for map its lil bit different. 
(def f1-car {
             :team "Mercedez"
             :driver "Lewis Hamilton"
             :starting-pos 1
             :positions [25 1 2 3 10]})

(s/def ::f1-car-spec 
  (s/keys :req-un [::team ::driver ::starting-pos ::positions]))

(s/conform ::f1-car-spec f1-car)

;; so found that the s/keys should always have fully qualified keys.
;; now if we want to check the type of map, then 
(s/def ::team string?)
(s/def ::driver string?)
(s/def ::starting-pos int?)
(s/def ::positions (s/coll-of int?))

;; lets create a function called position. check if the position is has some value, that is it is scorable. 
(defn scored?
  "Checks if scored given point is greater than threshold to get a score."
  [last-pos min-pos]
  (> last-pos min-pos))

(scored? (first (:positions f1-car)) 10)

;; now lest spec our function scored. 
(s/fdef scored?
  :args (s/cat :last-pos int? :min-pos int?)
  :ret boolean?)

;; inside the instrument function of clojure, pass the quoted fully qualified function name. 
(stest/instrument 'core/scored?)
(stest/check 'core/scored?)