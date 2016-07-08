(ns dibujo.usage
  (:require [dibujo.abstractions.point :refer [->Point round add]]
            [dibujo.abstractions.line :refer [compute new-line]]
            [dibujo.algorithms.line :as line-algos :refer [dda dsc dda2 bresenham1 bresenham2 bresenham3 bresenham4]]
            [dibujo.algorithms.development :refer [infinite-line]]
            [dibujo.jframe :refer [draw]]))

(def p1 (->Point 0 1))
(def p2 (->Point 26 24))

(def by-point #(-> (compute p1 p2 bresenham4)
                   draw))

(def by-angle #(draw (take 25 (infinite-line 45 p1))))
