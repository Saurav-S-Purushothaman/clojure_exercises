(ns serpant-talk.talk-test
  (:require [clojure.test :refer :all]
            [serpant-talk.talk :refer :all]))

;; here deftest is from the namespace clojure.test, we don't have to qualify the namespace while using it.
;; that is because we have used :refer :all in our code.
(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))
;; lets break this down.
;; deftest - defines a test function.
;; testing - used within deftest to provide context of what is being tested. 
;; is - provide assertion that is being tested.

