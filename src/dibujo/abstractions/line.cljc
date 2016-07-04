(ns dibujo.abstractions.line)

(defprotocol ILine
  (delta-x [_])
  (delta-y [_])
  (slope [_])
  (y-intercept [_]))

;; p1 - Point 1
;; p2 - Point 2
;; 𝝙x - delta x, x1 - x0
;; 𝝙y - delta y, y1 - y0
;; m - slope
;; b - y intercept, sometimes referred as pitch over X-axis 

(defrecord Line 
  [p1 p2 𝝙x 𝝙y m b]
  ILine
  (delta-x [_] (- (:x p2) (:x p1)))
  (delta-y [_] (- (:y p2) (:y p1)))
  (slope [_] (/ 𝝙y 𝝙x))
  (y-intercept [_] (- (:y p1) (* m (:x p1)))))

(defn new-line [p1 p2]
  {:pre [(not= p1 p2)]}
  (let [line (map->Line {:p1 p1 :p2 p2})
        line (assoc line :𝝙x (delta-x line) :𝝙y (delta-y line))
        line (assoc line :m (slope line))
        line (assoc line :b (y-intercept line))]
    line))

(defn compute [p1 p2 fn]
  (let [line (new-line p1 p2)]
    (fn line)))
