(ns nieuwe-beelding.usage
  (:require [nieuwe-beelding.abstractions.point :refer [->Point round add equal? set-color]]
            [nieuwe-beelding.abstractions.rectangle :refer [->Rectangle]]
            [nieuwe-beelding.abstractions.line :as line :refer [new-line]]
            [nieuwe-beelding.abstractions.circle :as circle :refer [new-circle]]
            [nieuwe-beelding.algorithms.line :as line-algos :refer [dda dsc dda2 bresenham1 bresenham2 bresenham3 bresenham4]]
            [nieuwe-beelding.algorithms.circle :as circle-algos :refer [bresenham]]
            [nieuwe-beelding.algorithms.rectangle :refer [grid]]
            [nieuwe-beelding.algorithms.development :as dev :refer [infinite-line]]
            #?(:clj [nieuwe-beelding.utils.javafx :as fx])
            #?(:clj [nieuwe-beelding.utils.jframe :refer [draw draw-line]]
               :cljs [nieuwe-beelding.utils.canvas :refer [draw]]))
  #?(:clj (:import java.awt.Color)))

(def p1 (->Point 1 1))
(def p2 (->Point 126 124))

(def line-by-point #(-> (line/compute p1 p2 bresenham4)
                        draw))

(def native-line #(draw-line p1 p2))
(def native-line #(fx/draw-line p1 p2))
(def red-line #(->> (line/compute p1 p2 bresenham4)
                    (map (fn [p] (set-color p (.getRGB Color/RED))) )
                    draw))

(def triangle #(let [p1 (->Point 10 10)
                     p2 (->Point 26 24)
                     p3 (->Point 2 24)
                     l1 (line/compute p1 p2 bresenham4)
                     l2 (line/compute p1 p3 bresenham4)
                     l3 (line/compute p3 p2 bresenham4)]
                 (draw (concat l1 l2 l3))))

(def triangle2 #(let [p1 (->Point 10 10)
                      p2 (->Point 26 24)
                      p3 (->Point 10 24)
                     l1 (line/compute p1 p2 bresenham4)
                     l2 (line/compute p1 p3 bresenham4)
                     l3 (line/compute p3 p2 bresenham4)]
                 (draw (concat l1 l2 l3))))

(def line-by-angle #(draw (take 25 (infinite-line 30 p2))))

(def circle (circle/compute p2 20 bresenham))

(defn octant [n circle]
  {:pre [(<= 0 n 7)]}
  (let [octant (fn [n circle] (map #(nth % n) (partition 8 circle)))]
    (octant n circle)))

(def octants #(concat (octant 3 circle) (octant 7 circle)))

(def less-dense (take-nth 3 circle))
(def random (random-sample 0.05 circle))

(defn missing-head [n circle]
  (let [points (apply interleave (partition 8 circle))]
    (drop n points)))

(defn missing-tail [n circle]
  (let [points (apply interleave (partition 8 circle))]
    (drop-last n points)))

(defn arcs [m]
  (flatten (for [n (range 8)]
             (drop m (octant n circle)))))

(def continuous #(concat (drop 4 (octant 0 circle)) (octant 1 circle) (octant 2 circle)))

(def ordered #(let [octants (apply interleave (partition 8 (circle/compute (->Point 35 24) 20 bresenham)))
                    octants (partition (/ (count octants) 8) octants)
                    octants (map-indexed (fn [i coll] (if (odd? i) (reverse coll) coll)) octants)]
                (flatten octants)))

(def r (->Rectangle p1 40 40))

(def grid-4x4 #(draw (grid r 4 4)))
