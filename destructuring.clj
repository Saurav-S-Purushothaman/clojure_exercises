;; DESTRUCTURING
;; destructuring allows you to assign named bindings for an element in things like vectors  and map

;; destructured way
(defn door []
  (let [[color size]["green" "small"]]
    (str "The color of the door is " color " and the size is " size)))

;; the same function can be written in little verbose way which is not that good.
(defn door2[] (let [x ["green" "small"] 
        color (first x) 
        size (last x)]
    (str "The color of the door is " color " and the size is " size)))

;; vector destructuring also handles nested vectors quite easily 
(defn door3 []
  (let [[color [size]]["green" ["small" " and" "medium"]]]
    (str "The color of the door is " color " and the size is " size)))

;; what if we want to store the whole initial datastructure as binding, you cas use the :as keyword
(defn door4 []
  (let [[color [size] :as original]["green" ["small" " and" "medium"]]]
    (str "The color of the door is " color " and the size is " size " and the original"
         " is " original)))

;; destructuring can also be done with maps
(defn door5 []
  (let [{flower1 :flower1 flower2 :flower2}{:flower1 "red" :flower2 "blue"}]
    (str "The flowers are " flower1 " and " flower2)))
;; the syntax in simple terms is 
;;let [{binding_val key1}{key1 value1}] ;; here binding_val = actual value of key1 in the map
;; we can also specify default value for a key if that value is not present 
(defn door6 []
  (let [{flower1 :flower1 flower2 :flower2 :or {flower2 "missing"}}{:flower1 "red" }]
    (str "The flowers are " flower1 " and " flower2)))
;; in map too, we can specify the binding as the entire initial datastructure 
;; to do that use the :as keywordk
(defn door7 []
  (let [{flower1 :flower1 flower2 :flower2 :or {flower2 "missing"} :as all-flower}{:flower1 "red" }]
    (str "The flowers are " flower1 " and " flower2 " and this is the all flower "
         all-flower)))
;; since most of the time binding and keys name are same, there is a shortcut for destructuring
;; this using the :keys keyword
(defn door7 []
  (let [{ :keys [flower1 flower2] :or {flower2 "missing"} :as all-flower}{:flower1 "red" :flower2 "blue" }]
    (str "The flowers are " flower1 " and " flower2 " and this is the all flower "
         all-flower)))
;; if the name of the binding is different, then it will not take any value inside it. 

;; destructuring is also available while declaring a function parameter. You can use a binding on parameter which is very usefull
(defn flower-color [{:keys [flower1 flower2]}]
  (str "The flowers are " flower1 " and " flower2)
  )
