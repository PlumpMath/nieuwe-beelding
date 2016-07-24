(ns nieuwe-beelding.utils.jframe
  (:require [nieuwe-beelding.abstractions.point :refer [get-color]])
  (:import java.awt.Graphics
           java.awt.Color
           java.awt.Image
           java.awt.image.BufferedImage
           java.awt.Dimension
           javax.swing.JFrame
           javax.swing.JPanel))

(defn display [bi d]
  (let [panel (proxy [javax.swing.JPanel] []
              (paintComponent [c]
                (proxy-super paintComponent c)
                (.drawImage c bi 0 0 (.getWidth this) (.getHeight this) nil)))]
    (doto (javax.swing.JFrame.)
      (.setContentPane panel)
      (.setSize (.width d) (.height d))
      (.show))))

(defn draw [points]
  (let [d (Dimension. 300 300)
        bi (BufferedImage. (.width d) (.height d) BufferedImage/TYPE_INT_ARGB)
        draw-pixel (fn [p] (let [c (or (get-color p) (.getRGB Color/BLACK))]
                            (.setRGB bi (:x p) (:y p) c)))]
    (doseq [p points]
      (draw-pixel p))
    (display bi d)))


(defn draw-line [p1 p2]
  (let [d (Dimension. 300 300)
        bi (BufferedImage. (.width d) (.height d) BufferedImage/TYPE_INT_ARGB)
        g (.createGraphics bi)]
    (doto g
      (.setColor Color/BLACK)
      (.drawLine (:x p1) (:y p1) (:x p2) (:y p2)))
    (display bi d)))


