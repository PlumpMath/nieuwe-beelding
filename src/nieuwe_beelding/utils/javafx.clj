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
   :main true))

(defn -init-state []
  [[] (atom nil)])

(defn draw-line []
  (let [line (Line. 0 0 30 40)]
    (.setStroke line Color/GREEN)
    (.setStrokeWidth line 3)
    line))

(defn create-content []
  (let [root (Pane.)]
    (.setPrefSize root 300 300)
    (.add (.getChildren root) (draw-line))
    root))

(def content (atom (create-content)))

(defn -start [this stage]
  (Platform/setImplicitExit false)
  (let [scene (Scene. @content)]
    (.setScene stage scene)
    (reset! (.state this) stage)
    (.show stage)))

(defn -main [& args]
   (Application/launch nieuwe-beelding.utils.javafx.Stage (into-array String [])))

;(defn update [] (Platform/runLater #(show. @stage)))

