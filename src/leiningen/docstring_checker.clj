(ns leiningen.docstring-checker
  (:require [bultitude.core :as b]
            [clojure.java.io :as io]
            [leiningen.core
             [eval :as lein]
             [project :as project]]))

(defn- docstring-checker-version
  "Get the current version of the Docstring Checker dependency."
  [{:keys [plugins]}]
  (some (fn [[plugin-name version]]
          (when (= plugin-name 'docstring-checker/docstring-checker)
            version))
        plugins))

(defn- docstring-checker-profile
  "Lein profile that includes a dependency for the Docstring Checker. Used for merging with the project we're
  checking's profile."
  [project]
  {:dependencies [['docstring-checker (docstring-checker-version project)]]})

(defn- get-packages
  "Generate a default set of namespace patterns to `:include` for docstring checking. This just finds all the
   namespaces belonging to the current project and generates a regex that will match each one."
  [paths]
  (mapv #(re-pattern (str "^" (name %)))
        (b/namespaces-on-classpath :classpath
                                   (map io/file paths))))

(defn- docstring-checker-options
  "Get the set of regular expressions for namespaces to `:include` and `:exclude`. Defaults to all project namespaces
   but you can customize this behavior by including `:docstring-checker` options in the project file."
  [project]
  (or (:docstring-checker project)
      {:include (get-packages (:source-paths project))
       :exclude []}))

(defn docstring-checker
  "Linter that checks all public vars in a Leiningen project have docstrings."
  [project]
  (println "Checking for docstrings...")
  (let [project (project/merge-profiles project [(docstring-checker-profile project)])]
    (lein/eval-in-project
     project
     `(docstring-checker.core/check-docstrings ~(docstring-checker-options project))
     '(require 'docstring-checker.core))))
