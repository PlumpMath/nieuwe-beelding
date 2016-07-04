(ns dibujo.abstractions.point)

(defprotocol IPoint
  (add [this p])
  (to-float [_])
  (to-double [_])
  (to-native [_])
  (round [_]))

(defrecord Point [x y]
  IPoint
  (add [this p] (->Point (+ x (:x p)) (+ y (:y p))))
  (to-double [_] (->Point (double x) (double y)))
  (to-float [_] (->Point (float x) (float y)))
  (to-native [_] (java.awt.Point. x y))
  (round [this] (->Point (Math/round x) (Math/round y))))
