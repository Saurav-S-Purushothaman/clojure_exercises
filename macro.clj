;; learning powerful macro of clojure
;; macros are the way to do meta programming in clojure.
;; most of the clojure core experessions are actually macro
;; For eg. when is a macro. If we do macro expand, then we can see the actual implementation of when.
(when (= 2 2) (println "It is two"))

(macroexpand-1
 '(when (= 2 2) (println "It is two")))

;; to create macro use the defmacro command.
;; lets create a macro for if-not

(defmacro if-nots [condition expression]
  (list 'if condition  nil expression))

(if-nots (> 3 5) (println "Hi"))
;; this would actually look like 
;; (if (> 3 5) nil (println "Hi"))


(defn hi-queen [phrase]
  (str phrase "So please your majesty!"))

;; (defn alice-hi-queen []
;;   (hi-queen "My name is Alice"))

;; doing this using macro 

(defmacro def-hi-queen [name, phrase]
  (list 'defn (symbol name) [] 
       (list 'hi-queen phrase) ))

(def-hi-queen alice-hi-queen "My name is Alice")

(alice-hi-queen)

;; instead of doing this there is something called templating which can be done here. 
;; templating is done using syntax quote.
;; there is also something called unquote, using syntax quote and 
;; unquote together is the best way to build macro in clojure

;; (let [x 5] `(first [x 1 2 4])) returns 
;; (clojure.core/first [user/x 1 2 4])

;; now in this, if we want the symbol to be executed or referencing the actual value 
;; then we need to use tilde ~

;; (let [x 5] `(first [~x 1 2 3 4]))
;; (clojure.core/first [5 1 2 3 4])

(defmacro def-hi-queen [name phrase]
  `(defn ~(symbol name) [] 
     (hi-queen ~phrase)))

(macroexpand-1 '(def-hi-queen Alice "My name is alice"))

(macroexpand-1 '(def hello "saurav"))


