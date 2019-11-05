(defproject docstring-checker "1.1.0"
  :description "Linter that checks that public vars in your project all have docstrings."
  :url         "https://github.com/camsaul/lein-docstring-checker"

  :license
  {:name "3-Clause BSD"
   :url  "https://github.com/camsaul/lein-docstring-checker/blob/master/LICENSE.txt"}

  :eval-in-leiningen true

  :dependencies
  [[org.clojure/tools.namespace "0.2.11"]]

  :deploy-repositories
  [["clojars"
    {:url           "https://clojars.org/repo"
     :username      :env/clojars_username
     :password      :env/clojars_password
     :sign-releases false}]])
