(ns leiningen.docstring-checker
  (:require [leiningen.core
             [eval :as lein]
             [project :as project]]))

(defn- docstring-checker-version [{:keys [plugins]}]
  {:post [(string? %)]}
  (some (fn [[plugin-name version]]
          (when (= plugin-name 'docstring-checker/docstring-checker)
            version))
        plugins))

(defn- add-docstring-checker-dep [project]
  (project/merge-profiles project [{:dependencies [['docstring-checker (docstring-checker-version project)]]}]))

(defn docstring-checker
  "Linter that checks all public vars in a Leiningen project have docstrings."
  [{:keys [source-paths docstring-checker], :as project}]
  (println "Checking for docstrings...")
  (lein/eval-in-project
   (add-docstring-checker-dep project)
   `(docstring-checker.core/check-docstrings ~(vec source-paths) ~docstring-checker)
   '(require 'docstring-checker.core)))
