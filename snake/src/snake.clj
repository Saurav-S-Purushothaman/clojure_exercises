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
(def point-szie 10)
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


