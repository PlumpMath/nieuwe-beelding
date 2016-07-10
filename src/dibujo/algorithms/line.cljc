(ns dibujo.algorithms.line
  (:require [dibujo.abstractions.point :refer [->Point round add to-float]]
            [dibujo.utils.math :as math]))

(defn dsc
  "Direct Scan Conversion
  
  http://ocw.unican.es/ensenanzas-tecnicas/visualizacion-e-interaccion-grafica/material-de-clase-2/03-LineAlgorithms.pdf"

  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (loop [p p1
         result [p1]]
    (let [x (inc (:x p))
          y (+ (* m (:x p)) b)
          next-point (->Point x (math/round (float y)))]
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
                    next-point (->Point x (math/round (float y)))]
                (if (= x (:x p2))
                  (conj result p2)
                  (recur next-point (conj result next-point)))))
    (> m 1) (loop [p p1
                   result [p1]]
              (let [y (inc (:y p))
                    x (+ (:x p) (/ 1 m))
                    next-point (->Point (math/round (float x)) y)]
                (if (= y (:y p2))
                  (conj result p2)
                  (recur next-point (conj result next-point)))))))

(defn dda2
  "Digital Differential Analyzer
  
  http://www.tutorialspoint.com/computer_graphics/line_generation_algorithm.htm"

  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (let [steps (if (> 𝝙x 𝝙y) (math/abs 𝝙x) (math/abs 𝝙y))
        x-increment (/ 𝝙x steps)
        y-increment (/ 𝝙y steps)]
    (loop [p p1
           n 1
           result [p1]]
      (let [next-point (->> (->Point x-increment y-increment)
                            (add p)
                            to-float
                            round)]
        (if (= n steps)
          (conj result p2)
          (recur next-point (inc n) (conj result next-point)))))))

(defn bresenham1
  "Non-optimized ideal line 
  
  When dy is greater than dx, the inner-loop of the algorithm must run
  stepwise in y-direction, otherwise vise versa in x-direction. This
  means that, we just change the x- and y-axis, or rotate the whole
  coordinate system by 90 degrees, making the new dy less than dx.

  http://www.codeproject.com/Articles/16564/Drawing-lines-in-Mozilla-based-browsers-and-the-In"

  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (let [sx (if (> 𝝙x 0) 1 -1)
        sy (if (> 𝝙y 0) 1 -1)]
    (cond
      (> (math/abs 𝝙x) (math/abs 𝝙y)) (loop [p p1
                                             result [p1]]
                                        (let [x  (+ (:x p) sx)
                                              y (+ (* m (:x p)) b)
                                              next-point (->Point x (math/round (float y)))]
                                          (if (= x (:x p2))
                                            (conj result p2)
                                            (recur next-point (conj result next-point)))))
      :else (let [m (/ 𝝙x 𝝙y)
                  b (- (:x p1) (* m (:y p1)))]
              (loop [p p1
                     result [p1]]
                (let [x (+ (* m (:y p)) b)
                      y (+ (:y p) sy)
                      next-point (->Point (math/round (float x)) y)]
                  (if (= y (:y p2))
                    (conj result p2)
                    (recur next-point (conj result next-point)))))))))

(defn bresenham2
  "Optimized ideal line 
  
  Therefore, our first optimization efforts will be, to get rid of
  this evaluation, especially the multiplication. We do this by
  incrementally calculating the right y- or x-value.

  http://www.codeproject.com/Articles/16564/Drawing-lines-in-Mozilla-based-browsers-and-the-In"

  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]

  (let [[sx 𝝙x] (if (> 𝝙x 0) [1 𝝙x] [-1 (- 𝝙x)])
        [sy 𝝙y] (if (> 𝝙y 0) [1 𝝙y] [-1 (- 𝝙y)])]
    (cond
      (> (math/abs 𝝙x) (math/abs 𝝙y)) (loop [fraction (+ m 0.5)
                                             p p1
                                             result [p1]]
                                        (let [x (+ (:x p) sx)
                                              [y fraction] (if (> fraction 1)
                                                             [(+ (:y p) sy) (- fraction 1)]
                                                             [(:y p) fraction])
                                              next-point (->Point x y)]
                                          (if (= x (:x p2))
                                            (conj result p2)
                                            (recur (+ fraction m) next-point (conj result next-point)))))
      :else (let [m (/ 𝝙x 𝝙y)]
              (loop [fraction (+ m 0.5)
                     p p1
                     result [p1]]
                (let [y (+ (:y p) sy)
                      [x fraction] (if (> fraction 1)
                                     [(+ (:x p) sx) (- fraction 1)]
                                     [(:x p) fraction])
                      next-point (->Point x y)]
                  (if (= y (:y p2))
                    (conj result p2)
                    (recur (+ fraction m) next-point (conj result next-point)))))))))


(defn bresenham3
  "http://www.idav.ucdavis.edu/education/GraphicsNotes/Bresenhams-Algorithm.pdf"
  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]
  (let [[increment-x 𝝙x] (if (pos? 𝝙x) [1 𝝙x] [-1 (- 𝝙x)])
        [increment-y 𝝙y] (if (pos? 𝝙y) [1 𝝙y] [-1 (- 𝝙y)])]
    (cond (>= 𝝙x 𝝙y) (loop [p p1
                            𝞊 (- m 1)
                            result [p1]]
                       (let [x (+ (:x p) 1)
                             [𝞊 y] (if (pos? 𝞊)
                                     [(- 𝞊 1) (+ (:y p) increment-y)]
                                     [𝞊 (:y p)])
                             next-point (->Point x y)]
                         (if (= x (:x p2))
                           (conj result p2)
                           (recur next-point (+ 𝞊 m) (conj result next-point)))))
          (> 𝝙y 𝝙x) (let [m (/ 𝝙x 𝝙y)]
                      (loop [p p1
                             𝞊 (- m 1)
                             result [p1]]
                        (let [y (+ (:y p) 1)
                              [𝞊 x] (if (pos? 𝞊)
                                      [(- 𝞊 1) (+ (:x p) increment-x)]
                                      [𝞊 (:x p)])
                              next-point (->Point x y)]
                          (if (= y (:y p2))
                            (conj result p2)
                            (recur next-point (+ 𝞊 m) (conj result next-point)))))))))

(defn bresenham4
  "Integer arithmetic
  http://www.idav.ucdavis.edu/education/GraphicsNotes/Bresenhams-Algorithm.pdf"
  [{:keys [p1 p2 𝝙x 𝝙y m b] :as line}]
  (let [[increment-x 𝝙x] (if (pos? 𝝙x) [1 𝝙x] [-1 (- 𝝙x)])
        [increment-y 𝝙y] (if (pos? 𝝙y) [1 𝝙y] [-1 (- 𝝙y)])]
    (cond (>= 𝝙x 𝝙y) (loop [p p1
                            𝞊 (- 𝝙y 𝝙x)
                            result [p1]]
                       (let [x (+ (:x p) 1)
                             [𝞊 y] (if (pos? 𝞊)
                                     [(- 𝞊 𝝙x) (+ (:y p) increment-y)]
                                     [𝞊 (:y p)])
                             next-point (->Point x y)]
                         (if (= x (:x p2))
                           (conj result p2)
                           (recur next-point (+ 𝞊 𝝙y) (conj result next-point)))))
          (> 𝝙y 𝝙x) (loop [p p1
                           𝞊 (- 𝝙x 𝝙y)
                           result [p1]]
                      (let [y (+ (:y p) 1)
                            [𝞊 x] (if (pos? 𝞊)
                                    [(- 𝞊 𝝙y) (+ (:x p) increment-x)]
                                    [𝞊 (:x p)])
                            next-point (->Point x y)]
                        (if (= y (:y p2))
                          (conj result p2)
                          (recur next-point (+ 𝞊 𝝙x) (conj result next-point))))))))
