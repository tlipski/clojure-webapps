(ns project1.core)

(defn example-handler [request]
 {:headers {"Location" "http://github.com/ring-clojure/ring"
	    "Set-cookie" "test=1"}
  :status 301}
 )

(defn on-init []
 (println "Initializing sample webapp!"))

(defn on-destroy []
 (println "Destroying sample webapp!"))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
