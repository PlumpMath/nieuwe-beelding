(ns dibujo.abstractions.circle)


(defprotocol ICircle
 )

; p is the center point
(defrecord Circle [p r]
  ICircle)

(defn new-circle [p r] (Circle. p r))

(defn compute [p r fn]
  (let [circle (new-circle p r)]
    (fn circle)))
