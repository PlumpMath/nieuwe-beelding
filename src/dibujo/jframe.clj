(ns dibujo.jframe
  (:require [dibujo.abstractions.point :refer [->Point]]
            [dibujo.abstractions.line :refer [compute]]
            [dibujo.algorithms.line :refer [dda dsc dda2 bresenham ideal-line]])
  (:import java.awt.Graphics
           java.awt.Color
           java.awt.Image
           java.awt.image.BufferedImage
           java.awt.Dimension
           javax.swing.JFrame
           javax.swing.JPanel))


(def d (Dimension. 300 300))
(def bi (BufferedImage. (.width d) (.height d) BufferedImage/TYPE_INT_ARGB))

(defn draw-pixel [p]
  (let [c (.getRGB Color/BLACK)]
    (.setRGB bi (:x p) (:y p) c)))

(def p1 (->Point 4 15))
(def p2 (->Point 18 75))

(doseq [p (compute p1 p2 ideal-line)]
  (draw-pixel p)) 

(def g (.createGraphics bi))
(.setColor g Color/BLACK)
(.drawLine g 0 0 10 10)
(.drawLine g 0 15 15 0)

(let [panel (proxy [javax.swing.JPanel] []
              (paintComponent [c]
                (proxy-super paintComponent c)
                ;(println (type c) (.getWidth this) (.getHeight this))
                (.drawImage c bi 0 0 (.getWidth this) (.getHeight this) nil)))]
  (doto (javax.swing.JFrame.)
    (.setContentPane panel)
    (.setSize (.width d) (.height d))
    (.show)))
