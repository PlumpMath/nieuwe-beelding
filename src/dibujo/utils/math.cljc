(ns dibujo.utils.math)

(defn abs [n] #?(:clj (Math/abs n)
                 :cljs (js/Math.abs n)))

(defn round [n] #?(:clj (if (float? n) (Math/round n) n)
                   :cljs (js/Math.round n)))

(defn floor [n] #?(:clj (Math/floor n)
                   :cljs (js/Math.floor n)))

(defn ceil [n] #?(:clj (Math/ceil n)
                   :cljs (js/Math.ceil n)))

(defn sin [n] #?(:clj (Math/sin n)
                 :cljs (js/Math.sin n)))

(defn cos [n] #?(:clj (Math/cos n)
                 :cljs (js/Math.cos n)))

(defn tan [n] #?(:clj (Math/tan n)
                 :cljs (js/Math.tan n)))
;; cotangent
(defn cot [n] (/ 1 (tan n)))

(defn atan [n] #?(:clj (Math/atan n)
                  :cljs (js/Math.atan n)))

(defn atan2 [p] #?(:clj (Math/atan2 (:y p) (:x p))
                   :cljs (js/Math.atan (:y p) (:x p))))

(def pi #?(:clj Math/PI
           :cljs js/Math.PI))

(defn exp [x n]
  (reduce * (repeat n x)))

(defn degrees-to-radians [𝝷] (* 𝝷 (/ pi 180)))
(defn radians-to-degrees [rad] (* rad (/ 180 pi)))
