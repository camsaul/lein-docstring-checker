(ns docstring-checker.core
  (:require [clojure
             [pprint :as pprint]
             [string :as str]]
            [clojure.java.io :as io]
            [clojure.tools.namespace.find :as ns-find]))

(defn- should-check-namespace?
  "Should we check the namespace with `nsname` for docstrings based on the `:include`/`:exclude` patterns?"
  [{:keys [include exclude]} nsname]
  (let [matches-include-rules? (if-not (seq include)
                                 true
                                 (some (fn [inclusion-pattern]
                                         (re-find inclusion-pattern nsname))
                                       include))
        matches-exclude-rules? (every? (fn [exclusion-pattern]
                                         (not (re-find exclusion-pattern nsname)))
                                       exclude)]
    (and nsname matches-include-rules? matches-exclude-rules?)))

(defn- proxy?
  "When using a Clojure `proxy` it creates a public var that uses the Java class naming conventions (i.e. `_` instead
  of `-`) that is public and will cause the `things-that-need-dox` to be tripped with no way to attach documentation
  to it. This function examines `symb` to see if it is from a generate proxy."
  [ns-symb symb]
  (let [ns-str   (str/replace (str ns-symb) \- \_)
        symb-str (str symb)]
    (.startsWith symb-str (str ns-str ".proxy$"))))

(defn- check-namespace
  "Check a namespace for public vars without docstrings. Returns a sequence of symbols that are missing docstrings."
  [ns-symb]
  (for [[symb varr] (do (require ns-symb)
                        (ns-publics ns-symb))
        :when       (and (not (:doc (meta varr)))
                         (not (proxy? ns-symb symb)))]
    (symbol (str (ns-name ns-symb) "/" symb))))

(defn- things-that-need-dox
  "Return a sorted sequence of public vars that need documentation in namespaces that should be checked."
  [source-paths options]
  ;; iterate over all the available namespaces and check the ones that match the patterns
  (sort (for [ns-symb (ns-find/find-namespaces (map io/file source-paths))
              :when   (should-check-namespace? options (str ns-symb))
              symb    (check-namespace ns-symb)]
          symb)))

(defn check-docstrings
  "Check that public vars have docstrings or fail."
  [source-paths {:keys [include exclude], :as options}]
  (try
    (when (seq include)
      (println "Including namespaces that match these patterns:" include))
    (when (seq exclude)
      (println "Excluding namespaces that match these patterns:" exclude))
    (when-let [things-that-need-dox (seq (things-that-need-dox source-paths options))]
      (println (format "Every public var might as well have a docstring! Go write some for the following (or make them ^:private):\n%s"
                       (with-out-str (pprint/pprint things-that-need-dox))))
      (System/exit -1))
    (println "Everything is documented. Well done.")
    (System/exit 0)
    (catch Throwable e
      (println "Error checking docstrings." e)
      (System/exit -2))))
