(ns docstring-checker.core
  (:require [clojure.java.classpath :as classpath]
            [clojure.pprint :as pprint]
            (clojure.tools.namespace [find :as ns-find]
                                     [file :as ns-file])))

(defn- should-check-namespace? [{:keys [include exclude]} nsname]
  (and nsname
       (some (fn [inclusion-pattern]
               (re-find inclusion-pattern nsname))
             include)
       (every? (fn [exclusion-pattern]
                 (not (re-find exclusion-pattern nsname)))
               exclude)))

(defn- load-file-or-dir [^java.io.File file-or-dir]
  (if (.isDirectory file-or-dir)
    (doseq [file (.listFiles file-or-dir)]
      (load-file-or-dir file))
    (when (ns-file/clojure-file? file-or-dir)
      (load-file (.getCanonicalPath file-or-dir)))))

(defn- things-that-need-dox [options]
  ;; Load all the files or dirs on the classpath
  (doseq [dir (classpath/classpath-directories)]
    (load-file-or-dir dir))
  ;; then iterate over all the loaded namespaces
  (sort (for [ns          (ns-find/find-namespaces (classpath/classpath))
              :let        [nm (try (str (ns-name ns))
                                   (catch Throwable _))]
              :when       (should-check-namespace? options nm)
              [symb varr] (ns-publics ns)
              :when       (not (:doc (meta varr)))]
          (symbol (str (ns-name ns) "/" symb)))))

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
