(ns dibujo.algorithms.circle
  (:require [dibujo.utils.math :as math]))

;; http://www.cse.iitm.ac.in/~vplab/courses/CG/PDF/LINE_CIRCLE_DRAW.pdf

(defn breshenham [{:keys p r :as circle}]
  (let [x r
        y 0
        𝝴 0
        increment-x (- 1 (* r 2))
        increment-y 1])
  (let [𝝴 (math/abs (- (+ (math/exp x 2) (math/exp y 2)) (math/exp r 2)))]) )
