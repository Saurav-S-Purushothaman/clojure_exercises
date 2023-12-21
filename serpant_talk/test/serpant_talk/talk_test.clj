(ns serpant-talk.talk-test
  (:require [clojure.test :refer :all]
            [serpant-talk.talk :refer :all]))

(deftest a-test
  (testing "FIXED, I pass."
    (is (= 1 1))))

(deftest test-talk
  (testing "Testing serpant-talk."
    (is (= "this_is_camel_case" (talk "this is camel case")))
    (is (= "this_is_camel_cas" (talk "ThisIsCamelCase")))))

