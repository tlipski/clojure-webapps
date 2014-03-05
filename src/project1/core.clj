(ns project1.core
 (:require [project1.handlers :as handlers]))

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

(defn test1-handler [request]
 (throw (RuntimeException. "error!"))
 {:body "test1"})

(defn test2-handler [request]
 {:status 301 :headers {"Location" "http://github.com/ring-clojure"}})

(defn route-handler [request]
 (condp = (:uri request)
  "/test1" (test1-handler request)
  "/test2" (test2-handler request)
  "/test3" (handlers/handler3 request)
  nil))

(defn wrapping-handler [request]
 (try
  (if-let [resp (route-handler request)]
    resp
    {:status 404 :body (str "Not found: " (:uri request))})
  (catch Throwable e
   {:status 500 :body (apply str (interpose "\n" (.getStackTrace e)))})))
