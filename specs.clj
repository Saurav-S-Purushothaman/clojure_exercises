;; clojure is dynamic typed.
;; inorder to make it static typed, we use specs. 
;; specs are logical composition of predicate. 
;; you need to import spec library to use spec

(require '[clojure.spec.alpha :as s])

;; an ingredient which is a map that has keys name quantity and unit. 
(s/def ::ingredient  (s/keys :req [::name ::quantiy ::unit]))
(s/def ::name        string?)
(s/def ::quantity    number?)
(s/def ::unit        keyword?)

;; the above were specs to define ingredient. 
;; now lets write spec for a function. 

(s/fdef scale-ingredient
        :args (s/cat :ingredient ::ingredient :factor number?)
        :ret ::ingredient)

;; now that you have crated the specs, lets validate it. 

(s/valid? ::ingredient "this is not right") ;; returns false
(s/valid? ::name "saurav") ;; returns true

;; lets see if we can accept only three values as input of a property, then we use specs. 
;; using sets is the best option here. It itself is a function predicate

(s/def ::colors #{:red :green :blue})
;; the color property will only accept red,green and blue
(s/valid? ::colors :red) ;; returns true
(s/valid? ::colors :yellow)

;; use s/int-in for range. s/double-in etc. are also valid. 

;; if we want our var to accept null value, the best method is to use nilable

(s/def ::names (s/nilable string?))
(s/valid? ::names nil) ;; returns true

;; logical specs
;; for checking odd integers 
(s/def ::my-num (s/and int? odd?))

;; collection specs
(s/def ::names (s/coll-of string?)) ;; each value of the collection must satisfy this 
;; condition. 

;; ;The s/coll-of spec also comes with many additional options supplied as keyword
;; ;arguments at the end of the spec.
;; ;• :kind - a predicate checked at the beginning of the spec. Common examples
;; ;are vector? and set?.
;; ;• :into - one of these literal collections: [], (), or #{}. Conformed values collect
;; ;into the specified collection.
;; ;• :count - an exact count for the collection.
;; ;• :min-count - a minimum count for the collection.
;; ;• :max-count - a maximum count for the collection.

(s/def ::my-set (s/coll-of int? :kind set? :min-count 2))
(s/valid? ::my-set #{10 20})

;; s/map-of is also there. 
(s/def ::scores (s/map-of string? int?))
(s/valid? ::scores {"Stu" 100, "Alex" 200})

;; s/tuple each element have its own specs. 
(s/def ::point (s/tuple float? float?))
(s/conform ::point [1.3 2.7]) ;; returns true





















