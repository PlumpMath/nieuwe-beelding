(ns dibujo.utils.math)

(defn abs [n] #?(:clj (Math/abs n)
                 :cljs (js/Math.abs n)))

(defn round [n] #?(:clj (Math/round n)
                   :cljs (js/Math.round n)))

(defn floor [n] #?(:clj (Math/floor n)
                   :cljs (js/Math.floor n)))

(defn ceil [n] #?(:clj (Math/ceil n)
                   :cljs (js/Math.ceil n)))
