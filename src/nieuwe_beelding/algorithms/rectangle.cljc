(ns nieuwe-beelding.algorithms.rectangle
  (:require [nieuwe-beelding.abstractions.point :refer [->Point equal?]]
            [nieuwe-beelding.utils.math :as math]))


(defn grid
  "n is number of columns, m is number of rows"
  [{:keys [p w h] :as rectangle} n m]
  {:pre [(= 0 (mod w n)) (= 0 (mod h m))]}
  (let [n-increment (/ w n)
        m-increment (/ h m)
        bottom-right (->Point (+ (:x p) w) (+ (:y p) h))]
    (loop [current-point p
           points [current-point]]
      (if (equal? current-point bottom-right)
        points
        (if (< (:x current-point) (:x bottom-right))
          (let [next-point (->Point (+ (:x current-point) n-increment) (:y current-point))]
            (recur next-point (conj points next-point)))
          (let [next-point (->Point (:x p) (+ (:y current-point) m-increment))]
            (recur next-point (conj points next-point))))))))
