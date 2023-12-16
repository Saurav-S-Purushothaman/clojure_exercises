;; everything in clojure is a list
;; code is data!

;; we can represent datastructure/data using symbols
;; def allows you to give something a name so we can refer to it anywhere from the code. 
;; it doesn't bind the symbol directly, it does it through a var
(def developer "Alice") ;; this creates a var object in our default namespace
;; we can also reference symbol from the namespace usign slash / 
user/developer ;; this is the actual name of the vars since we are in the same namespace we are avoiding user/
;; let is another way to create a symbol 
;; its temp. Binding in let are in vector form and expects a key val pari in vector
(let [developer "alice" age 12] [developer age]) ;;["alice" 12]

;; FUNCTIONS
;; -----------------------------------------------------
;; to create functions use defn
;; it take three arguments (name_of_the fucntion, vector of parameter, body of the function)
(defn follow-the-rabbit [] "Off we go!")

(defn shop-for-jams [j1 j2]
{:name "Jam Basket", :jam1 j1 , :jam2 j2})

(shop-for-jams "Apple" "Orange") ;; returns {:name "Jam Basket", :jam1 "Apple", :jam2 "Orange"}
;; Anonymous function 
(fn [] ()) ;; fn, vector of args, body of function
(fn [] (str "Off we go")) ;; creates a function
((fn [] (str "Off we go"))) ;; invokes the function returns Off we go
;; defn is just the same as binding a name to the anonymous function
(def myfunc (fn [] "Off we go"))
;; there is shorthand form of anonymous function too. 
(#(str "Hello World"))
;; if there is one parameter you can pass it by using %
(#(str "Hello World " %)"Saurav")
;; if there are more arguments then 
(#(str "Hello World " %1 "And how are " %2)"Saurav" "you")
;; namespace 
;; ------------------------------------------------
;; namespace are for organizing your code, vars and functions
(ns Alice.fav-foods) ;; this is how you create a namespace
;; using clojure libs 
;; its associted with namespace 
;; three ways to use libs
;; first one to use require function this will load the namespace and enable access via fully qualified namespace
(require 'clojure.set) ;; this is not working
(require '[clojure.set :as set]) ;; this is working.


;; NOTE: This is binary search algorithm
;; TODO: Fix this.
(defn binary-search [arr target]
  "Search the index of an element in vector"
  (letfn [(search [start end]
            (if (<= start - end)
              (let [mid (quot (+ start end) 2)
                    mid-val (arr mid)]
                (cond
                  (= mid-val target) mid-val
                  (< mid-val target) (recur (inc mid) end) 
                  :else (recur start (dec mid) -1]
                               (search 0 (dec (count arr))))))))))

