(ns leiningen.docstring-checker
  (:require [leiningen.core.eval :as lein]))

(defn- docstring-checker-version [{:keys [plugins]}]
  (some (fn [[plugin-name version]]
          (when (= plugin-name 'docstring-checker/docstring-checker)
            version))
        plugins))

(defn docstring-checker
  "Linter that checks all public vars in a Leiningen project have docstrings."
  [project]
  (println "Checking for docstrings...")
  (lein/eval-in-project
   (update-in project [:dependencies] conj ['docstring-checker (docstring-checker-version project)])
   `(do
      (require 'docstring-checker.core)
      (docstring-checker.core/check-docstrings ~(:docstring-checker project)))))
