(ns dibujo.abstractions.circle)


(defprotocol ICircle
 )

; p is the center point
(defrecord Circle [p r]
  ICircle)
