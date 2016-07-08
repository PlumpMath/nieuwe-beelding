(ns dibujo.algorithms.development
  (:require [dibujo.abstractions.point :refer [->Point round add to-float]]
            [dibujo.utils.math :as math]))

(defn infinite-line
  ([𝝷 p] (infinite-line 𝝷 p (next (range))))
  ([𝝷 p len] (lazy-seq
              (cons (round p) (infinite-line 𝝷 (->Point (+ (:x p) (* (first len) (math/cos 𝝷))) (+ (:y p) (* (first len) (math/sin 𝝷)))) (next len))))))

(defn bresenham-integer-math
  "https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
  http://ocw.unican.es/ensenanzas-tecnicas/visualizacion-e-interaccion-grafica/material-de-clase-2/03-LineAlgorithms.pdf
  https://www.siggraph.org/education/materials/HyperGraph/scanline/outprims/drawline.htm"
  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]
  (loop [p p1
         D (- 𝝙y 𝝙x)
         result [p1]]
    (let [x (+ (:x p) 1)
          [D y] (if (pos? D) [(- D 𝝙x) (+ (:y p) 1)] [D (:y p)])
          next-point (->Point x y)]
      (if (= x (:x p2))
        (conj result p2)
        (recur next-point (+ D 𝝙y) (conj result next-point))))))
