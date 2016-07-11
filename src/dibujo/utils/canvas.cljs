(ns dibujo.utils.canvas)

;; will draw a black pixel in the canvas (The width attribute defaults to 300, and the height attribute defaults to 150).
(defn draw [points]
  (let [draw-pixel (fn [ctx p]
                     (let [pixel (.createImageData ctx 1 1)]
                       (aset (.-data pixel) 3 255)
                       (.putImageData ctx pixel (:x p) (:y p))))
        canvas (.createElement js/document "canvas")
        ctx (.getContext canvas "2d")]
    (doseq [p points]
      (draw-pixel ctx p))
    canvas))
