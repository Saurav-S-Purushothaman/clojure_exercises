
(defn string-to-vec 
  "Converts a string into a vector with element being each letter"
  [arg]
  (->> arg 
       (into [])
       (map #(str %))
       (vec)))



(defn solution [strng ending]
  (cond
    ;; if the length of first string is greater than second, then no need to compare
    (> (count ending) (count strng)) false 
    ;; converting each string to vector and checking if the last element ar same 
    (= ( string-to-vec ending) 
       (-> strng 
           (string-to-vec)
           (subvec 
             (- (count strng) (count ending))))) true
    :else false))
