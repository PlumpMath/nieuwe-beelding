(ns dibujo.algorithms.line
  (:require [dibujo.abstractions.point :refer [->Point round add]]))


(defn dsc
  "Direct Scan Conversion
  
  http://ocw.unican.es/ensenanzas-tecnicas/visualizacion-e-interaccion-grafica/material-de-clase-2/03-LineAlgorithms.pdf"

  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (loop [p p1
         result [p1]]
    (let [x (inc (:x p))
          y (+ (* m (:x p)) b)
          next-point (->Point x (Math/round (float y)))]
      (if (= x (:x p2))
        (conj result p2)
        (recur next-point (conj result next-point))))))

(defn dda
  "Digital Differential Analyzer
  http://ocw.unican.es/ensenanzas-tecnicas/visualizacion-e-interaccion-grafica/material-de-clase-2/03-LineAlgorithms.pdf
  http://web.cs.wpi.edu/~emmanuel/courses/cs543/slides/lecture9_p3.pdf
  https://www.siggraph.org/education/materials/HyperGraph/scanline/outprims/drawline.htm"
  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (cond
    (< m 1) (loop [p p1
                   result [p1]]
              (let [x (inc (:x p))
                    y (+ (:y p) m)
                    next-point (->Point x (Math/round (float y)))]
                (if (= x (:x p2))
                  (conj result p2)
                  (recur next-point (conj result next-point)))))
    (> m 1) (loop [p p1
                   result [p1]]
              (let [y (inc (:y p))
                    x (+ (:x p) (/ 1 m))
                    next-point (->Point (Math/round (float x)) y)]
                (if (= y (:y p2))
                  (conj result p2)
                  (recur next-point (conj result next-point)))))))

(defn dda2
  "Digital Differential Analyzer
  
  http://www.tutorialspoint.com/computer_graphics/line_generation_algorithm.htm"

  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (let [steps (if (> 𝝙x 𝝙y) (Math/abs 𝝙x) (Math/abs 𝝙y))
        x-increment (/ 𝝙x steps)
        y-increment (/ 𝝙y steps)]
    (loop [p p1
           n 1
           result [p1]]
      (let [next-point (round (add p (->Point x-increment y-increment)))]
        (if (= n steps)
          (conj result p2)
          (recur next-point (inc n) (conj result next-point)))))))

(defn bresenham
  "https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
  http://ocw.unican.es/ensenanzas-tecnicas/visualizacion-e-interaccion-grafica/material-de-clase-2/03-LineAlgorithms.pdf
  https://www.siggraph.org/education/materials/HyperGraph/scanline/outprims/drawline.htm"
  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}])

;; Midpoint Algorithm for Line Drawing http://www.cosc.canterbury.ac.nz/mukundan/cogr/LineMP.html

