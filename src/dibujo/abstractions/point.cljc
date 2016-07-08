(ns dibujo.abstractions.point
  (:require [dibujo.utils.math :as math]))

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
  (round [_] (->Point (math/round x) (math/round y)))
  (to-native [_] #?(:clj (java.awt.Point. x y))))
