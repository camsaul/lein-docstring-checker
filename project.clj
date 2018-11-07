(defproject docstring-checker "1.0.3-SNAPSHOT"
  :description "Linter that checks that public vars in your project all have docstrings."
  :url "https://github.com/camsaul/lein-docstring-checker"
  :license {:name "3-Clause BSD"
            :url "https://github.com/camsaul/lein-docstring-checker/blob/master/LICENSE.txt"}
  :eval-in-leiningen true
  :dependencies [[org.clojure/java.classpath "0.3.0"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :deploy-repositories [["clojars" {:sign-releases false}]])
