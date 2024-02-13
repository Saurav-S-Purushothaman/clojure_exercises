(ns wonderland.core
;; (:require [clojure.set :as set])
 )

;; (defn common-fav-foods [foods1 foods2]
;;   (let [food-set1 (set foods1)
;;         food-set2 (set foods2)
;;         common-foods (set/intersection food-set1 food-set2)]
;;    (str "Common foods" common-foods)))
;; 
;; (def common-food (common-fav-foods [:jam :butter :vanilla] [:jam :apple :orange]))

;; (common-fav-foods [:jam :butter :apple] [:jam :butter :apple :orange]) ;; [:jam :butter :apple ]
;(defn grow [name direction]
;  (case direction
;    :small (str name " is growing smaller")
;    :large (str name " is growing larger")
;    "No direction specified."
;    )
;  )

(defn -main
      "I don't do a whole lot yet"
      [& args]
      (println "HelloWorld!")
      )
