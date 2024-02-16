(ns records
  :import (javax.sound.midi.MidiSystem Note))

(defrecord Note [pitch octave duration])

(def first-note (->Note :D# 4 1/2))

; accessing elements of records 
(.pitch first-note) 
(:pitch first-note) ; this also works because records are map like and therefore 
; we can access through keywords. 

; updating records 
(assoc first-note :pitch :Db :duration 1/4)

;; records are open, therefore we can add extra fields into record.
(assoc first-note :velocity 100)

;; dissoc functions works differently.
; if we remove optional or extra added key, then it returns a new record. 
; if we remove non extra added key, then it returns and new plain map. 

(dissoc first-note :velocity) 

(defprotocol MidiNote
  (to-msec [this tempo])
  (key-number [this])
  (play [this tempo midi-channel]))


