(ns dibujo.abstractions.line)

(defprotocol ILine
  (delta-x [_])
  (delta-y [_])
  (slope [_])
  (intercept [_]))

(defrecord Line [p1 p2 dx dy m b]
  ILine
  (delta-x [_] (- (:x p2) (:x p1)))
  (delta-y [_] (- (:y p2) (:y p1)))
  (slope [_] (/ dy dx))
  (intercept [_] (- (:y p1) (* m (:x p1)))))

(defn make-line [p1 p2]
  {:pre [(not= p1 p2)]}
  (let [line (map->Line {:p1 p1 :p2 p2})
        line (assoc line :dx (delta-x line) :dy (delta-y line))
        line (assoc line :m (slope line))
        line (assoc line :b (intercept line))]
    line))

(defn compute [p1 p2 fn]
  (let [line (make-line p1 p2)]
    (fn line)))
