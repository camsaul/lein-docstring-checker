(ns leiningen.docstring-checker
  (:require [leiningen.core.eval :as lein]
            [leiningen.compile :as lein-compile]
            [bultitude.core :as b]))

(defn- docstring-checker-version [{:keys [plugins]}]
  (some (fn [[plugin-name version]]
          (when (= plugin-name 'docstring-checker/docstring-checker)
            version))
        plugins))

(defn get-packages [paths]
  (map #(re-pattern (str "^" (name %)))
       (b/namespaces-on-classpath :classpath
                                  (map clojure.java.io/file paths))))

(defn docstring-checker
  "Linter that checks all public vars in a Leiningen project have docstrings."
  [project]
  (println "Checking for docstrings...")
  (lein/eval-in-project
   (update-in project [:dependencies] conj ['docstring-checker (docstring-checker-version project)])
   `(do
      (require 'docstring-checker.core)
      (if ~(:docstring-checker project)
        (docstring-checker.core/check-docstrings ~(:docstring-checker project))
        (docstring-checker.core/check-docstrings {:include (vector ~@(get-packages (:source-paths project)))
                                                  :exclude []})))))
