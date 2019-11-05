(ns good.core)

(defn foo
  "I'm ok, because I have a docstring."
  [x]
  (println x "Hello, World!"))

(defn create-a-proxy
  "A docstring here"
  []
  ;; This generates a proxy class which is a public var but should be ignored
  (proxy [java.lang.Object] []
    (toString [] "I'm an object")))
