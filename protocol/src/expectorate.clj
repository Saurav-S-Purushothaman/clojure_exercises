(ns expectorate
  :import (java.io OutputStreamWriter BufferedWriter FileOutputStream))

(defn expectorate
  "writes to a file" 
  [dst content]
  (with-open [writer 
              (-> dst
                  FileOutputStream.
                  OutputStreamWriter.
                  BufferedWriter.)]
    (.write writer (str content))))

(println "Hello world!")

