(ns dibujo.usage
  (:require [dibujo.abstractions.point :refer [->Point round add]]
            [dibujo.abstractions.line :as line :refer [new-line]]
            [dibujo.abstractions.circle :as circle :refer [new-circle]]
            [dibujo.algorithms.line :as line-algos :refer [dda dsc dda2 bresenham1 bresenham2 bresenham3 bresenham4]]
            [dibujo.algorithms.circle :as circle-algos :refer [bresenham]]
            [dibujo.algorithms.development :refer [infinite-line]]
            #?(:clj [dibujo.utils.jframe :refer [draw]]
               :cljs [dibujo.utils.canvas :refer [draw]])))

(def p1 (->Point 0 1))
(def p2 (->Point 26 24))

(def line-by-point #(-> (line/compute p1 p2 bresenham4)
                   draw))

(def line-by-angle #(draw (take 25 (infinite-line 45 p1))))

(def circle1 #(-> (circle/compute p2 20 bresenham)
                  draw))
