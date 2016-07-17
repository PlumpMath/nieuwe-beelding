(set-env!
 :source-paths #{"src"}
 :resource-paths #{"src"}
 :dependencies '[])

(def +version+ "0.0.1")

(task-options!
 pom {:project 'org.danielsz/nieuwe-beelding
      :version +version+
      :scm {:name "git"
            :url "https://github.com/danielsz/nieuwe-beelding"}})

(deftask dev
  []
  (comp
   (repl :server true)
   (wait)))

(deftask local-install
  []
  (comp
   (pom)
   (jar)
   (install)))
