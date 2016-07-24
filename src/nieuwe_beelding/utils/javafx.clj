(ns nieuwe-beelding.utils.javafx
  (:import
   javafx.application.Platform
   javafx.application.Application
   javafx.stage.StageBuilder
   javafx.stage.Stage
   javafx.stage.Screen
   javafx.scene.SceneBuilder
   javafx.scene.Parent
   javafx.scene.Scene
   javafx.scene.layout.Pane
   javafx.scene.layout.Region
   javafx.scene.paint.Color
   javafx.scene.shape.Arc
   javafx.scene.shape.Line)
  (:gen-class
   :init init-state
   :state state
   :name nieuwe-beelding.utils.javafx.Stage
   :extends javafx.application.Application
   :methods [^:static [getStage [] clojure.lang.Atom]]
   :main true))

(defn draw-line []
  (let [line (Line. 10 10 40 50)]
    (.setStroke line Color/RED)
    (.setStrokeWidth line 3)
    line))

(defn create-content []
  (let [root (Pane.)]
    (.setPrefSize root 300 300)
    (.add (.getChildren root) (draw-line))
    root))

(def primary-stage (atom nil))

(defn -getStage [] primary-stage)

(defn -init-state []
  [[] (atom nil)])

(defn -start [this stage]
  (Platform/setImplicitExit false)
  (let [scene (Scene. (create-content))]
    (.setScene stage scene)
    (reset! (.state this) this)
    (reset! primary-stage stage)
    (.show stage)))

(defn -main [& args]
  (Application/launch nieuwe-beelding.utils.javafx.Stage args))


(defn display []
  (if-let [stage @(nieuwe-beelding.utils.javafx.Stage/getStage)]
    (Platform/runLater #(.show stage))
    (future (Application/launch nieuwe-beelding.utils.javafx.Stage nil))))


; (def new-scene (Scene. (fx/create-content)))
; (Platform/runLater #(.setScene @(nieuwe-beelding.utils.javafx.Stage/getStage) new-scene))
