(ns cb.core)

;; simple values

;; define some new values

(def board-maximum-x 40)
(def board-maxumum-y 40)

;; create a vector of strings to describe the directions a rover can face

["north" "east" "south" "west"]
["north" "north-east" "east" "south-east" "south" "south-west" "west" "north-west"]

;; maps
;; model a mars rover

{:direction "north"
 :x-coordinate 0
 :y-coordinate 0}

(def rover {:direction "north"
            :x-coordinate 0
            :y-coordinate 0})

;; get out just the direction
(get rover :direction)

;; model the history of a mars rover

(def history [{:x 0 :y 0} {:x 1 :y 0} {:x 1 :y 1}])

;; add a new position to the history

(conj history {:x 1 :y 2})

;; add our history to the rover

(merge rover {:history history})
(assoc rover :history history)

;; move the rover

(update rover :x-coordinate inc)


;; nesting function calls

(merge (update rover :x-coordinate inc) {:history history})

;; should we touch on threading

;; functions

;; write a function that will move a rover north (increase x)

(defn move-north [rover]
  (update rover :x-coordinate inc))

;; modify this function to take a distance to move

(defn move-north [rover distance]
  (update rover :x-coordinate + distance))

;; flow control

;; write a new move-north function that won't move the rover north if it's going to move off the board

(defn move-north [rover]
  (if (> board-maximum-x (get rover :x-coordinate))
    (update rover :x-coordinate inc)
    rover))

;; add the distance parameter back in

(defn move-north [rover distance]
  (if (>= board-maximum-x (+ distance (get rover :x-coordinate)))
    (update rover :x-coordinate + distance)
    rover))

;; what happens when someone sends a negative distance

(defn move-north [rover distance]
  (cond
    (< board-maximum-x (+ distance (get rover :x-coordinate))) rover
    (< (+ distance (get rover :x-coordinate)) 0)               rover
    :else (update rover :x-coordinate + distance)))

;; can we refactor using let
(defn move-north [rover distance]
  (let [potential-new-x (+ distance (get rover :x-coordinate))]
    (cond
      (< board-maximum-x potential-new-x) rover
      (< potential-new-x 0)               rover
      :else (update rover :x-coordinate + distance))))

;; can we re-write this again using or
(defn move-north [rover distance]
  (let [potential-new-x (+ distance (get rover :x-coordinate))]
    (if (or (< board-maximum-x potential-new-x)
            (< potential-new-x 0))
      rover
      (update rover :x-coordinate + distance))))

