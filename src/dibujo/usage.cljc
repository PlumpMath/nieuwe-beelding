(ns dibujo.usage
  (:require [dibujo.abstractions.point :refer [->Point round add]]
            [dibujo.abstractions.line :as line :refer [new-line]]
            [dibujo.abstractions.circle :as circle :refer [new-circle]]
            [dibujo.algorithms.line :as line-algos :refer [dda dsc dda2 bresenham1 bresenham2 bresenham3 bresenham4]]
            [dibujo.algorithms.circle :as circle-algos :refer [bresenham]]
            [dibujo.algorithms.development :as dev :refer [infinite-line]]
            #?(:clj [dibujo.utils.jframe :refer [draw]]
               :cljs [dibujo.utils.canvas :refer [draw]])))

(def p1 (->Point 1 1))
(def p2 (->Point 26 24))

(def line-by-point #(-> (line/compute p1 p2 bresenham4)
                   draw))

(def line-by-angle #(draw (take 25 (infinite-line 30 p2))))

(def circles (fn [] (let [circle (circle/compute p2 20 bresenham)
                          less-dense (take-nth 3 circle)
                          random (random-sample 0.05 circle)
                          octant (fn [n circle] (map #(nth % n) (partition 8 circle)))]
                      (draw (octant 5 circle)))))

;(defn arc [points p1 p2] (filter #(<= 100 (:y %) 154) (circle/compute p 90 bresenham)))

