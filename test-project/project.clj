(require 'leiningen.core.project)

(def project-version
  (:version (leiningen.core.project/read "../project.clj")))

(printf "Testing against project version %s\n" project-version)

(defproject test-project "1.0.0-SNAPSHOT"
  :dependencies
  [[org.clojure/clojure "LATEST"]]

  :plugins
  [[docstring-checker #=(eval project-version)]]

  :profiles
  {:bad
   {:source-paths ["bad"]}

   :bad-with-patterns
   [:bad
    {:docstring-checker {:include [#"^bad"]
                         :exclude []}}]

   :good
   {:source-paths ["good"]}

   :good-with-patterns
   [:bad
    :good
    {:docstring-checker {:include [#"^good"]
                         :exclude [#"^bad"]}}]})
