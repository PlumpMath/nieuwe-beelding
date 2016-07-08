(ns dibujo.usage
  (:require [dibujo.abstractions.point :refer [->Point round add]]
            [dibujo.abstractions.line :refer [compute new-line]]
            [dibujo.algorithms.line :refer [dda dsc dda2 bresenham ideal-line ideal-line2 infinite-line]]
            [dibujo.jframe :refer [draw]]))

(def p1 (->Point 4 15))
(def p2 (->Point 18 75))

(def by-point #(-> (compute p1 p2 ideal-line2)
                   draw))

(def by-angle #(draw (take 25 (infinite-line 45 p1))))