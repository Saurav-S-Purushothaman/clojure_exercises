;---
; Excerpted from "Programming Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/shcloj3 for more book information.
;---
(ns snake
  (:import (java.awt Color Dimension) 
	   (javax.swing JPanel JFrame Timer JOptionPane)
           (java.awt.event ActionListener KeyListener))
  (:refer import-static :refer :all))
(import-static java.awt.event.KeyEvent VK_LEFT VK_RIGHT VK_UP VK_DOWN)

; TODO: implement the Snake!

;; functional model 
; define a set of constants
(def width 75)
(def height 50)
(def point-size 10)
(def turn-milis 75)
(def win-length 5)
(def dirs {VK_LEFT [-1 0]
           VK_RIGHT [1 0]
           VK_UP [0 -1]
           VK_DOWN [0 1]})

; basic math function for the game. 
; adds two pionts together. 
(defn add-points [& pts] 
  (vec (apply map + pts)))

; converts and point in a game space into rectange. 
(defn point-to-screen-rect [pt]
  (map #(* point-size %)
       [(pt 0) (pt 1) 1 1]))

(defn create-apple []
  {:location [(rand-int width) (rand-int height)]
   :color (Color. 210 50 90)
   :type :apple})

(defn create-snake []
  {:body (list [1 1])
   :dir [1 0]
   :type :snake
   :color (Color. 15 160 70)})

(defn move 
  "Function to move the snake.
   This is pure function, it returns new snake.
   if the snake grows, the entire body is kept as it is. 
   Otherwise the butlast element will be removed."
  [{:keys [body dir] :as snake} & grow]
   (assoc snake :body (cons (add-points (first body) dir)
                            (if grow body (butlast body)))))

(defn win? 
  "Return true if the game is won."
  [{body :body}]
  (>= (count body) win-length))

; snake lose when head overlaps its body. 
(defn head-overlaps-body? [{[head & body] :body}]
  (contains? (set body) head))

(def lose? head-overlaps-body?)

;snake eats an apple if its head occupies the apples location. 
(defn eats? [{[snake-head] :body} {apple :location}]
  (= snake-head apple))

; turn the snake, change its direction. 
(defn turn [snake newdir]
  (assoc snake :dir newdir))

; Mutable model of the game using STM. 




