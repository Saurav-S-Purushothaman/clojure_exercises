;; things from programming clojure book 
(macroexpand-1 
  '(every? #(Character/isWhitespace %) "saurav"))

(defrecord Person [first-name last-name age])

;; (def person (Person. "John" "Cena" 55))
;; this syntax can be used but more 
;; idiomatic syntax would be 
(def person (->Person "John" "Cena" 55))

(prn person) ;; prints the record

;; some features 
(println (:first-name person))
(println (:last-name person))
(println (:age person))

(defn hello-world [username]
  (str (format "Hello %s" username)))

(hello-world "Saurav")

;; there is no for-loop in clojure but there is list comprehension

;; just remember this syntax its good. Its list comprehension.
;;(for [c compositions :when (= (:name c) "Requiem")] (:composer c))
;;-> ("W. A. Mozart" "Giuseppe Verdi")

(def compositions [{:name "Love story" :composer "Taylor swift"}
    {:name "I wanna be yours" :composer "Arctic Monkey"}
    {:name "black december" :composer "Taylor swift"}])

(for [c compositions
      :when (= (:composer c) "Taylor swift" )](:name c))

;; converting this clojure code to java just for my fun
;; (.start (new Thread (fn [] (println "Hello" (Thread/currentThread)))))

;; Much of clojure is written in clojure.
;; to view the source code of clojure. 
;; you can use the repl namespace 
(require '[clojure.repl :refer [source]])
;; (source reduce)

;; Prefix notation - The style of keeping the operands/functions first  
;; Infix notation - The normal way of using

;; clojure functions enforces arity. Otherwise Arity Exception 
;; will be called. 

;; here is very interesting implementation of the fact that
;; different arities of same function can call one another

(defn greeting
  "Returns a greeting of the form 'Hello username' 
  Default username is world"
 ([] (greeting "world")) 
 ([username] (str "Hello " username)))

;; destructuring 
;; 
;; (defn greeting
;;   [{:keys [fname lname]}]
;;   (println "Hell " fname))

;; the above code doesn't work. 
;; instead use this.
(defn greeting
  [{fname :first-name}]
  (println "Hello " fname))

(defn greet-author-2 [{fname :first-name}]
(println "Hello," fname))

(require '[clojure.string :as str])

(defn ellipsize [words]
(let [[w1 w2 w3] (str/split words #"\s+")]
(str/join " " [w1 w2 w3 "..."])))
(ellipsize "The quick brown fox jumps over the lazy dog.")

;; resolve 
;; it tells the symbol or var it resolves to in the current namespace. 
;; (resolve sym)
(resolve 'ellipsize)
;; namespace. 
;; best way to switch namespace is by using the command
;; (in-ns namespace-name)
;; when you are in new namespace always use this

(clojure.core/use 'clojure.core)

;; be default class name outside of java.lang must be fully qualified in clojure
;; for example 
;;(File/separator) ;; this throws an error.
;; you must use

(java.io.File/separator)

;; you should use import for importing java classes 
;; require for importing vars from another namespace.
;; syntax (import ('package class1 class2 ...))

;; different type of requre statement
;; (require 'clojure.string)
;; even if you import using this statement, you are still required to use 
;; the fully qualified name of the underlying methods of this namespace. 
;; one way to avoid that is to use alias
;; (require ['clojure.string :as str])
;; now you may use the alias to use the functions of clojure.string.
;; it is common practice to set namespace using ns command.
;; then use the additional :import, :require and :use commands to 
;; import java classes, and namespaces.
;; an eg. would be

;; (ns example.exploring
;;   (:require ['clojure.string :as str])
;;   (:import (java.io File )))

;; metadata
;; a person without metadata.
(def perso {:name "John" :age 20})

;; a person with metadata. 
;; (def person-with 
;;   ^{:source Database} {:name "Saurav" :age 24})

(def person-with-metadata 
  ^{:source "Database"} {:name "John" :age 30})

;; trailing dot to create an instance of the object. 
(def rnd (java.util.Random.))

;; use leading dot to create invoke method or fields of the instance. 
(def nextInt (. rnd nextInt 10))
;; this can be also written in this way which is more concise.
(def nextInt2 (.nextInt rnd 10))

;; remember this syntax. It will be helpful
;; (.method instance & args)
;; (.field instance)
;; (.-field instance)
;; (Class/method & args)
;; Class/field
;; (java.util.Random/nextInt 10)

;; always remember for static method 
;; use (class/method args)
;; for instance method, use (.method instance args)
;; as simple as that.

;; if you want wrap around a code in clojure, then
;; use comment macro
;; there is also a read macro in clojure.

(defn tripple [number]
  #_(println "Tripples the number")
  (* number 3))

;; if you want to add some side effects in flow control
;; then use do.

;; demonstrating recur without loop

(defn countdown 
  "Creates a countdown for givern args" 
  [result x]
  (if (= x 0) result
    (recur (conj result x) (dec x))))



;; UNIFYING DATA SEQUENCE 
;; -----------------------
;; -----------------------
;; -----------------------
;; -----------------------
;; -----------------------

;; prgrams manipulate data. 
;; atl lowest level these data are list, vector, string, trees etc. 
;; at highest level it can be XML(tree), Database result (vector) , 
;; files (tree)

;; in clojure all datastructure can be accessed through single abstraction. 
;; that is SEQUENCE

;; collections that can be viewed as seqs are called seqable.
;; java collection, clojure collection, java arrays and string, 
;; regular expression, directory structure, I/O stream, XML trees

;; avoid loops as much as possible. 

;; (cons element sequence) ;; adds element in front of the sequence. 
;; seq function will return nill if the column is empty. 
;; if you want to check whether a column is empty, then use this. 
(not (seq '())) ;; this will return true;

;; when you use, rest, cons over a vector, the result is a seq
(seq? (rest [1 2 3])) ;; return true;

;; set is not sorted in clojure, 
;; if order is important, then you can use, sorted-set & elements) 
(sorted-set :the :quick :brown :fox)
;; likewise, there is sorted map for the same thing to be achieved. 

;; (conj coll & elements) 
;; (into to-seq from-seq)

;; for vectors it adds the element at the back.o

;; iterate functions begings with a value x and continues forever.
;; iterate begins with element and continues forever to apply the function f
;; (iterate f x) 

(take 10 (iterate inc 2))

;; cycle function takes a collection and cycles it infinitely. 
;; I have feeling that we can achieve for loop using cycle. I dunno. 

(range 3)
(take 10 (cycle (range 3)))

;; interleave is an interesting function. It takes two collections and 
;; cycles through each element of the collection (first collectino and 
;; then second collection until one collection is exhausted)

(interleave [:a :b :c :d] [1 2 3 4 5 6 7])

;; another interesting function is interpose. 
(def separator ",")
(def Coll [1 2 3 4])
(interpose separator Coll)
 ;; returns (1 "," 2 "," 3 "," 4)

(apply str (interpose separator Coll))
;; isntead of this, you can use clojure.string/join

(require '[clojure.string :refer [join]])
(join \, [:apple :banana])

(type (set (hash-set 1 2 3 4)) )

;; take-while is interesting, 
;; a while loop takes applies the return boolean function until its true
(def vowels? #{\a\e\i\o\u})
(def constants? (complement vowels?))

(take-while constants? "This is a great book")
;; returns (\T \h)

;; drop while is exact opposite of take-while. 
(drop-while constants? "This is a great book")
;; returns the rest of the string in character until constant? returns false

;; split-at and split-with 
(split-at 5 (range 10))
;; returns [(0 1 2 3 4) (5 6 7 8 9)]
(split-with #(< % 10) [1 2 10 1 2 3 3 30 123 123 1 2 3 ])
;; returns [(1 2) (10 1 2 3 3 30 123 123 1 2 3)]

;; sequence predicate, 
;; sequence predicate ask how some other predicate apply to this predicate
(every? odd? [1 2 3 4 4])
(some odd? [1 2 3 4])
;; some doens't have a ? at the end because, the return value of some 
;; is not boolean, instead it returns the fisrt logical true element of the list
;; so its invicible when used with even? and odd? which returns 
;; true and false,
;; here is an example, that proves how some works. 

(some identity [nil :first nil :second])
;; returns first

;; how do you check if something is present in a list or not. 
;; best way is to use some with seq I guess. 
(some #{3} [1 2 ]) ;; returns nil, else return 3 if 3 was present.

;; there is not every and not any as well in clojure

;; transforming sequence
;; ---------------------
;; ---------------------
;; ---------------------

(map #(format "<p>%s</p>" %) ["this" "that"])
;; returns ("<p>this</p>" "<p>that</p>")

;; map can also take more than one function. 
(map #(format "<%s>%s<%s>" %1 %2 %1) ["h1" "h2"] ["this" "that"])
;; returns ("<h1>this<h1>" "<h2>that<h2>")

;; reduce, f -> function of two arguments, 
;; it applies the f on first two element. 
;; then continues to apply the result with the next element. 
;; repeats until the list is completed. 

(reduce + (range 10))

;; list comprehension = grandaddy of all list transformation.
;; it consist of 
;; input list 
;; a binding for each element of the input list. 
;; a predicate on the input list
;; at last the output. 

;; (for [bindfing-form coll-expr filter-expr?] expr)

(def my-vec ["the" "quick" "brown" "fox"])
(for [word my-vec] 
  (format "<p>%s</p>" word))

(def whole-numbers (iterate inc 0))
(take 5 (for [ele whole-numbers :when (even? ele)] ele))

;; the real power of for comes when we are using multiple
;; bindings 
(for [alpha "ABCDEFGH" rank (range 1 9)]
  (format "%s%d" alpha rank ))

;; try to be lazy almost
;; string can be serialized in clojure I guess. Not sure though. 

;; clojure makes java seqable. 
;; in java world, collection api, regular expression, xml, file explorer, everything are 
;; seqable. 

(first (.getBytes "Hellow")) ;; returns 72
(rest (.getBytes "Hello"))
;; returns (101 108 108 111)
(cons (int \h) (rest (.getBytes "Hello")))
;; returns (104 101 108 108 111)

; system.getPorperties returns a hashtable, 
;; hashtable and maps are also seqable. 
(rest (System/getProperties))

;; clojure automatically converts elements into sequence while applying function. 
;; if you want to convert back, then you have to manually do it. 
;; for instance the idiomatic way of converting string back to string is by using apply str function. 
(apply str (reverse "Saurav"))


;; Don't do this 
(re-find #"\w+" "Hello world") ;; this is lazy, 
;; you can use loop to make it not lazy. 
;; however there is a better way to do this.
(re-seq #"\w+" "Hello world")
;; returns ("Hello" "world")

;; working with file system in clojure. 
(import 'java.io.File)

;; java code. 
;; String pathTofile = ".";
;; File directory = new File(pathTofile);
;; File [] files = directory.listFiles(); 

(map #(.getName %)  (seq(.listFiles (File. "."))))
;; instead of this you can directly use this 
(map #(.getName %)  (.listFiles (File. ".")))

; if you want to recursively walk through the whole directory, ]
;; clojure provides a fileseek option here as well.
;(file-seq (File. "."))
(count (file-seq (File. ".")))

(defn minutes-to-millis [mins] (* mins 1000 60))
(defn recently-modified? [file]
(> (.lastModified file)
(- (System/currentTimeMillis) (minutes-to-millis 30))))
;; this code shows the recently modified files in the text. 
(filter recently-modified? (file-seq (File. ".")))


;; let me read this code now. 
(defn recently-modif? [file]
  (.lastModified file (System/currentTimeMillis) (minutes-to-millis 30)))

(require '[clojure.java.io :refer [reader]])
;; lets just learn to use clojure and java reader library to read file, 
;; uri or url.

(with-open [rdr (reader "basics.clj")]
  (count (line-seq rdr)))

;; lets count all 
(with-open [rdr (reader "basics.clj")]
  (count (filter #(re-find #"\S" %) (line-seq rdr))))


;; lets do the following taks. 
;; a function to check non-blanc line. 
(use '[clojure.string :only (blank?)])
(defn non-blank? [line] (not (blank? line)))

;; a function to check if the file type is .svn 
(defn non-svn? [file] (not (.contains (.toString file) ".svn")))

;; a function to check if the file is clojure file ".clj"
(defn clojure-source? [file] (.endsWith (.toString file) ".clj"))

;; then clojure.ioc functions that counts the lines of clojure code in a directory. 
(defn clojure-ioc [directory]
  (reduce + 
          (for [file (file-seq directory)
                :when (and (non-svn? file) (clojure-source? file))]
            (with-open [rdr (reader file)]
              (count (filter non-blank? (line-seq rdr)))))))



(defn clojure-loc [base-file]
  (reduce
  +
  (for [file (file-seq base-file)
    :when (and (clojure-source? file) (non-svn? file))]
      (with-open [rdr (reader file)]
        (count (filter non-blank? (line-seq rdr)))))))

(clojure-ioc (java.io.File. "./serpant_talk"))

;; important thing about clojure sets. 
;; it can behave as a relational database as relational algebra is possible in it. 
;; firstly lets create a relational database first. 

(def compositions
  #{{:name "The Art of the Fugue" :composer "J. S. Bach"}
  {:name "Musical Offering" :composer "J. S. Bach"}
  {:name "Requiem" :composer "Giuseppe Verdi"}
  {:name "Requiem" :composer "W. A. Mozart"}})

(def composers
  #{{:composer "J. S. Bach" :country "Germany"}
  {:composer "W. A. Mozart" :country "Austria"}
  {:composer "Giuseppe Verdi" :country "Italy"}})

(def nations
  #{{:nation "Germany" :language "German"}
  {:nation "Austria" :language "German"}
  {:nation "Italy" :language "Italian"}})

;; set functions that can be used inside This
;; select  - select is like where clause gives all rows
;; project - is like the actual select statement. 
;; there are commands to join sets .
;; update set
