(ns dibujo.algorithms.circle
  (:require
   [dibujo.abstractions.point :refer [->Point round add to-float]]
   [dibujo.utils.math :as math]))

;; http://www.cse.iitm.ac.in/~vplab/courses/CG/PDF/LINE_CIRCLE_DRAW.pdf

(defn bresenham
  "http://web.engr.oregonstate.edu/~sllu/bcircle.pdf"
  [{:keys [p r] :as circle}]
  (let [plot-8-circle-points (fn [x y] [(add p (->Point x y))
                                        (add p (->Point (- x) y))
                                        (add p (->Point (- x) (- y)))
                                        (add p (->Point x (- y)))
                                        (add p (->Point y x))
                                        (add p (->Point (- y) x))
                                        (add p (->Point (- y) (- x)))
                                        (add p (->Point y (- x)))])]
    (loop [x r
           y 0
           x-change (- 1 (* r 2))
           y-change 1
           𝝴 0
           result []]
      (if (>= x y)
        (if (> (+ (* 2 𝝴) x-change) 0)
          (recur (dec x) (inc y) (+ x-change 2) (+ y-change 2) (+ 𝝴 y-change x-change) (concat result (plot-8-circle-points x y)))
          (recur x (inc y) x-change (+ y-change 2) (+ 𝝴 y-change) (concat result (plot-8-circle-points x y))))
        result))))
