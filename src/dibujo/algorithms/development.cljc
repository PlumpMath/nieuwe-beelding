(ns dibujo.algorithms.development
  (:require [dibujo.abstractions.point :refer [->Point round add]]
            [dibujo.utils.math :as math]))

;; 𝝷 is the angle
(defn infinite-line
  ([𝝷 p] (let [m (math/tan (math/degrees-to-radians 𝝷))
               b (- (:y p) (* m (:x p)))]
           (infinite-line p m b)))
  ([p m b] (lazy-seq (cons (round p) (infinite-line (->Point (inc (:x p)) (+ (* m (:x p) b))) m b)))))

(defn infinite-line2
  ([𝝷 p]
   (let [𝝷 (math/degrees-to-radians 𝝷)]
     (infinite-line2 𝝷 p (next (range)))))
  ([𝝷 p len] (lazy-seq
              (cons (round p) (infinite-line2 𝝷 (->Point (+ (:x p) (* (first len) (math/cos 𝝷))) (+ (:y p) (* (first len) (math/sin 𝝷)))) (next len))))))


