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
(def turn-millis 75)
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
; mutable states are 
; game can restart. 
; every turn snake update its position 
; if snake eats an apple, new apple is placed. 
; a snake can turn. 

; reset game 
(defn reset-game 
  "To reset the game to its initial state."
  [snake apple]
  (dosync (ref-set apple (create-apple))
          (ref-set snake (create-snake))) nil)

; update directionn. 
; wrapper around snake. 
(defn update-direction [snake newdir]
  (when newdir (dosync (alter snake turn newdir))))

; update-position
; if snake eats apple, new apple created and snake grows. 
; otherwise snake simply moves. 
(defn update-positions [snake apple]
  (dosync 
   (if (eats? @snake @apple) 
     (do (ref-set apple (create-apple))
         (alter snake move :grow))
     (alter snake move))) nil)

;; ----------------------
;; snake gui
;; ----------------------

(defn fill-point [g pt color]
  (let [[x y width height] (point-to-screen-rect pt)]
    (.setColor g color)
    (.fillRect g x y width height)))

(defmulti paint (fn [g object & _] (:type object)))

(defmethod paint :apple [g {:keys [location color]}]
  (fill-point g location color))

(defmethod paint :snake [g {:keys [body color]}]
  (doseq [point body] 
    (fill-point g point color)))


; START: game-panel
(defn game-panel [frame snake apple]
  (proxy [JPanel ActionListener KeyListener] []
    (paintComponent [g] ; <label id="code.game-panel.paintComponent"/>
      (proxy-super paintComponent g)
      (paint g @snake)
      (paint g @apple))
    (actionPerformed [e] ; <label id="code.game-panel.actionPerformed"/>
      (update-positions snake apple)
      (when (lose? @snake)
	(reset-game snake apple)
	(JOptionPane/showMessageDialog frame "You lose!"))
      (when (win? @snake)
	(reset-game snake apple)
	(JOptionPane/showMessageDialog frame "You win!"))
      (.repaint this))
    (keyPressed [e] ; <label id="code.game-panel.keyPressed"/>
      (update-direction snake (dirs (.getKeyCode e))))
    (getPreferredSize [] 
      (Dimension. (* (inc width) point-size) 
		  (* (inc height) point-size)))
    (keyReleased [e])
    (keyTyped [e])))
; END: game-panel

; START: game
(defn game [] 
  (let [snake (ref (create-snake)) ; <label id="code.game.let"/>
	apple (ref (create-apple))
	frame (JFrame. "Snake")
	panel (game-panel frame snake apple)
	timer (Timer. turn-millis panel)]
    (doto panel ; <label id="code.game.panel"/>
      (.setFocusable true)
      (.addKeyListener panel))
    (doto frame ; <label id="code.game.frame"/>
      (.add panel)
      (.pack)
      (.setVisible true))
    (.start timer) ; <label id="code.game.timer"/>
    [snake, apple, timer]))
; <label id="code.game.return"/>
; END: game
(defn -main
      "Entry point of the application"
      [& args]
      (println "Hello world"))

