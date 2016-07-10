(ns dibujo.utils.jframe
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
        draw-pixel (fn [p] (let [c (.getRGB Color/BLACK)]
                            (.setRGB bi (:x p) (:y p) c)))]
    (doseq [p points]
      (draw-pixel p))
    (display bi d)))



 

;; (def g (.createGraphics bi))
;; (.setColor g Color/BLACK)
;; (.drawLine g 0 0 10 10)
;; (.drawLine g 0 15 15 0)


