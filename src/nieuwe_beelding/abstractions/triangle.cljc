(ns nieuwe-beelding.abstractions.triangle)

(def protocol Itriangle
  (circumcircle [_]))

(defrecord triangle [p1 p2 p3]
  Itriangle
  (circumcircle [_]))
