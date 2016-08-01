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
   javafx.event.EventHandler
   javafx.stage.WindowEvent
   javafx.scene.shape.Line)
  (:gen-class
   :name nieuwe-beelding.utils.javafx.Stage
   :extends javafx.application.Application
   :methods [^:static [getStage [] clojure.lang.Atom]]
   :main true))

(defn event-handler*
  [f]
  (reify javafx.event.EventHandler
    (handle [this e] (f e))))

(defmacro event-handler [arg & body]
  `(event-handler* (fn ~arg ~@body)))

(defn display [scene]
  (let []
    (if-let [stage @(nieuwe-beelding.utils.javafx.Stage/getStage)]
      (do (Platform/runLater #(.setScene stage scene))
          (Platform/runLater #(.show stage)))
      (future (Application/launch nieuwe-beelding.utils.javafx.Stage nil)))))

(defn draw-line [p1 p2]
  (let [root (Pane.)
        line (Line. (:x p1) (:y p1) (:x p2) (:y p2))]
    (.setStroke line Color/RED)
    (.setStrokeWidth line 3)
    (.setPrefSize root 300 300)
    (.add (.getChildren root) line)
    (display (Scene. root))))

(def primary-stage (atom nil))

(defn -getStage [] primary-stage)

(defn -start [this stage]
  (Platform/setImplicitExit false)
  (.addEventHandler stage WindowEvent/ANY (event-handler [e] (println e)))
  (reset! primary-stage stage))

;; (defn -main [& args]
;;   (Application/launch nieuwe-beelding.utils.javafx.Stage args))

