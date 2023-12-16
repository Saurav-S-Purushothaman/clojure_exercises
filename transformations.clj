;; MAP REDUCE   
;; powerfull abstraction of data transformation that is implemented using clojure

;; MAP 
;; map take two args, one is a function and the next one is a collection.
;; result of map will be the collection with the function applied to each element. (Pretty powerful)

(def animals [:mouse :duck :rabbit :elephant :lion])

;; function that will transform keyword into str
(#(str %) :mouse)
(map #(str %) animals)
(class (map #(str %) animals)) ;; the return type is not vector is lazySeq

;; lazy seq means we can deal with infinite elements.
(take 3 (map #(str %) (range))) 

;; now lets learn about side effects 
;; Pure function - given the same input always returns the same output
;; side effect - is something else that occurs in the function that changes the outside world
;; outside world implies - printing,logging etc etc. 
;; lazy sequences must be used carefully with function that has side effects.

(def animals-print (map #(println %) animals))
;; simply creating a function won't print anything because its lazy. If we want it be printed while 
;; creating or calling it, we must call animals-print
animals-print ;; this would print everything

;; map funciton can take two collection. in that case design the function such that it process two args. 
;; it will terminate when the shortest collection end.
(def color [:red :blue :green :orange])

(defn map-color [animal color]
  (str animal "-" color) ) 

(map map-color animals color)

;; because the map function terminates with shortest collection, we can add infinite list with it. 
(map map-color animals (range))

;; REDUCE
;; It differs from map in that it change the shape of the result as you process through collections.
(reduce + [1 2 3 4 5])
;; reduce takes a function of two argument. 
;; the ongoing result and the element that it is processing. 

(reduce #(+ %1 (* %2 %2)) [1 2 3])

;; we can also change the shape of the vector by specifying the initial value for the result before
;; specifying the first element 

(reduce (fn [r x] (if (nil? x) r (conj r x))) [] 
[:mouse nil :duck nil nil :lory])

;; OTHER IMPORTANT TRANSFORMATION FUNCTIONS

;; inorder to filter out data, there is filter function.
;; it takes a predicate and a collection. 
;; complement - its function that takes another function and returns the a function with same number of args
;; but with the returned result opposite of the truth value of what the original function has returned.

(def anim [:mouse nil :duck nil nil :lory])
(filter (complement nil?) anim)
;; or you could use this 
;; there is also a remove function
(remove nil? anim)

;; for - it allows us to binding for each element of the collections and we can process the collections 
;; using that binding. 
(for [an animals]
  (str (name an)))
;; if more than 1 collection is specified it iterate over them in a nested fashion (like for loop inside for loop)
(for [an animals col color] 
  (str (name col) (name an)))
;; we can use :let (support modifier) which will be greatly helpful

(for [an animals 
col color
:let [animal-str (str "animal-" (name an)) 
color-str (str "color-" (name col))
display-str (str animal-str "-" color-str)]] 
display-str) 
;; another support modifier is when

(for [an animals 
col color
:let [animal-str (str "animal-" (name an)) 
color-str (str "color-" (name col))
display-str (str animal-str "-" color-str)]
:when (= col :blue)] 
display-str) 

;; some useful functions
;; flatten - flattens the nested collection 
(flatten [:blue :organge [:green :yellow] [:green]])

;; vec - converts the list into a vector 
(vec '(1 2 3 4))

;; into [] - conjugate everything in the argument to list 
(into [] '(1 2 3 4 5))
;; sorted-map - sortes the map in the order of the key
(sorted-map :b 1 :a 2 :c 3)
;; we can take regular map and turn it into sorted map-color

(into (sorted-map) {:b 1 , :a 2})
;; or vector of pair into maps
(into {} [[:a 1] [:b 3]])
;; or map into vector pair
(into [] {:b 1 , :a 2})

;; partition is useful for dividing up collections 
(partition 3 [1 2 3 4 5 6 76]) ;; returns ((1 2 3) (4 5 6))
;; by default partiton will only partition up those elements which will return all the 
;; partition with same number of element with which its partitioned.
;; to override this behavior there is partition-all function 
;; ((partition-all 3 [1 2 3 4 5 6 76]));; returns (1 2 3) (4 5 6) (76))
;; there is also a partitoin by where it will partition the vector based on the predicate
(partition-by #(= 6 %) [1 2 3 4 5 6 7 8 9 10])
