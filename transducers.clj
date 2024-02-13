;; simply put transducers are something that transforms a sequence.
;; we can use two predicate inside the transducer.
;; one would be reducing function.
;; otherone would be the function with which we have to apply
;; the transformation. For eg.
(def xform (comp (filter even?) (map inc)))
(transduce xfrom + [1 2 3 4 5 6]) ;; 
;; returns 18


