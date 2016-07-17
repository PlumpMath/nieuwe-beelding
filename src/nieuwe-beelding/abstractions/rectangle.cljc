(ns nieuwe-beelding.abstractions.rectangle)

(defprotocol
    IRectangle)

;; p is top-left corner.
(defrecord Rectangle [p w h]
  IRectangle)
