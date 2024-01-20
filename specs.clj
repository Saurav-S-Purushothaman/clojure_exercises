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
(s/conform ::point [1 2])


;; validating functions. 
;; important for generative function testing

;; s/cat - basically concatination. 
(s/def ::cat-example (s/cat :s string? :i int?))
(s/valid? ::cat-example ["saurav" 1])

;; s/alt - basically we can have alternate values. 
(s/def ::cat-example2 (s/alt :k keyword? :i int?))
(s/conform ::cat-example2 [:foo])
(s/conform ::cat-example2 [1])
(s/conform ::cat-example2 [1 :foo]) ;; but this is invalid. 

;; regular experssion style conformation is present. 
;; s/+ s/? s/*
(s/def ::oe (s/cat :odds (s/+ odd?) :even (s/? even?)))
;; one or more odd values with an optional even value at the end is a valid expression.

(s/conform ::oe [1 3 5 6]) ;; this is valid. 

;; we can combine specs. 
(s/def ::odds (s/+ odd?)) ;; one or more odd numbers is valid. 
(s/def ::optional-even (s/? even?)) ;; an optional even number is valid

;; combining these 
(s/def ::oe2 (s/cat :odds ::odds :even ::optional-even))
;; this is same as the below one. We can combine specs. 
(s/def ::oe (s/cat :odds (s/+ odd?) :even (s/? even?)))

(s/def ::intersection-args
  (s/cat :s1 set?
  :sets (s/* set?)))

;; what this implies is that intersection will have one arg which will be sets
;; and an another arg which will optional sets. 

;; best way to define a function is define args and ret separately and then use that 
;; in your function. 

;; lets skip the generative function testing for now. Not able to spin my
