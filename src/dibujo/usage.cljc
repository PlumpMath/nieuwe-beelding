(ns dibujo.usage
  (:require [dibujo.abstractions.point :refer [->Point round add]]
            [dibujo.abstractions.line :refer [compute new-line]]
            [dibujo.algorithms.line :refer [dda dsc dda2 bresenham ideal-line ideal-line2]]
            [dibujo.jframe :refer [draw]]))

(def p1 (->Point 4 15))
(def p2 (->Point 18 75))

(-> (compute p1 p2 ideal-line2)
    draw)
