(defproject good-with-patterns "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :plugins [[docstring-checker "LATEST"]]
  :docstring-checker {:include [#"^good-with-patterns"]
                      :exclude [#"test"]})
