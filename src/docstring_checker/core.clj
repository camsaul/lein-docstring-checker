(ns docstring-checker.core
  (:require [clojure.java.classpath :as classpath]
            [clojure.pprint :as pprint]
            (clojure.tools.namespace [find :as ns-find]
                                     [file :as ns-file])))

(defn- should-check-namespace?
  "Should we check the namespace with `nsname` for docstrings based on the `:include`/`:exclude` patterns?"
  [{:keys [include exclude]} nsname]
  (and nsname
       (some (fn [inclusion-pattern]
               (re-find inclusion-pattern nsname))
             include)
       (every? (fn [exclusion-pattern]
                 (not (re-find exclusion-pattern nsname)))
               exclude)))

(defn- things-that-need-dox
  "Return a sorted sequence of public vars that need documentation in namespaces that should be checked."
  [options]
  ;; iterate over all the available namespaces and check the ones that match the patterns
  (sort (for [ns-symb     (ns-find/find-namespaces (classpath/classpath))
              :when       (should-check-namespace? options (str ns-symb))
              [symb varr] (do (require ns-symb)
                              (ns-publics ns-symb))
              :when       (not (:doc (meta varr)))]
          (symbol (str (ns-name ns-symb) "/" symb)))))

(defn check-docstrings
  "Check that public vars have docstrings or fail."
  [options]
  (when-not options
    (println "Please specify which namespaces to :include and :exclude in :docstring-checker in your project.clj.")
    (System/exit -1))
  (println "Including namespaces that match these patterns:" (:include options))
  (println "Excluding namespaces that match these patterns:" (:exclude options))
  (when-let [things-that-need-dox (seq (things-that-need-dox options))]
    (println (format "Every public var might as well have a docstring! Go write some for the following (or make them ^:private):\n%s"
                     (with-out-str (pprint/pprint things-that-need-dox))))
    (System/exit -1))
  (println "Everything is documented. Well done.")
  (System/exit 0))
