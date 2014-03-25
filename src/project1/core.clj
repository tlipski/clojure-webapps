(ns project1.core
 (:require [project1.handlers :as handlers]
           [ring.middleware.resource :as resource]
     	   [ring.middleware.file-info :as file-info]
 	   [ring.middleware.params]
	   [ring.middleware.keyword-params]
   	   [ring.middleware.multipart-params]
	   [ring.middleware.cookies]
	   [ring.middleware.session]
	   [ring.middleware.session.memory]
  	   [project1.html :as html]
           [clojure.string]))

(defn layout [contents]
 (html/emit
  [:html
   [:body 
    [:h1 "Clojure webapps example"]
    [:p "This content comes from layout function"]
    contents]]))
(defn case-middleware [handler request]
 (let [request (update-in request [:uri] clojure.string/lower-case)
       response (handler request)]
  (if (string? (:body response))
   (update-in response [:body] clojure.string/capitalize)
   response)))

(defn wrap-case-middleware [handler]
  (fn [request] (case-middleware handler request)))

(defn exception-middleware-fn [handler request]
 (try (handler request)
  (catch Throwable e 
   {:status 500 :body (apply str (interpose "\n" (.getStackTrace e)))})))

(defn wrap-exception-middleware [handler]
 (fn [request]
  (exception-middleware-fn handler request)))

(defn not-found-middleware [handler]
 (fn [request]
  (or (handler request)
   {:status 404 :body (str "404 Not Found (with middleware!):" (:uri request))})))

(defn simple-log-middleware [handler]
 (fn [{:keys [uri] :as request}]
  (println "Request path:" uri)
  (handler request)))

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

(defn cookie-handler [request]
 {:body (layout [:div [:p "Cookies:"]
		[:pre (:cookies request)]])})

(defn session-handler [request]
 {:body (layout [:div [:p "Session:"]
		[:pre (:session request)]])})

(defn logout-handler [request]
 {:body "Logged out."
  :session nil})

(defn form-handler [request]
 {:status 200
  :headers {"Content-type" "text/html"}
  :cookies {:username (:login (:params request))}
  :session {:username (:login (:params request))
	    :cnt (inc (or (:cnt (:session request)) 0))}
  :body (layout
    	 [:div
  	   [:p "Params:"]
  	[:pre (:params request)]
 	[:p "Query string params:"]
	[:pre (:query-params request)]
	[:p "Form params:"]
	[:pre (:form-params request)]
	[:p "Multipart params:"]
	[:pre (:multipart-params request)]
	[:p "Local path:"]
	[:b (when-let [f (get-in request [:params :file :tempfile])]
  		(.getAbsolutePath f))]])})

(defn route-handler [request]
 (condp = (:uri request)
  "/test1" (test1-handler request)
  "/test2" (test2-handler request)
  "/test3" (handlers/handler3 request)
  "/form"  (form-handler request)
  "/cookies" (cookie-handler request)
  "/session" (session-handler request)
  "/logout" (logout-handler request)
  nil))

(defn wrapping-handler [request]
 (try
  (if-let [resp (route-handler request)]
    resp
    {:status 404 :body (str "Not found: " (:uri request))})
  (catch Throwable e
   {:status 500 :body (apply str (interpose "\n" (.getStackTrace e)))})))

(def full-handler 
 (-> route-handler
  not-found-middleware
  (resource/wrap-resource "public")
  file-info/wrap-file-info
  wrap-case-middleware
  wrap-exception-middleware
  ring.middleware.keyword-params/wrap-keyword-params
  ring.middleware.params/wrap-params
  ring.middleware.multipart-params/wrap-multipart-params
  (ring.middleware.session/wrap-session
   {:cookie-name "ring-session"
    :root "/" 
    :cookie-attrs {:max-age 600
 		   :secure false}
    :store (ring.middleware.session.memory/memory-store)})
  ring.middleware.cookies/wrap-cookies
  simple-log-middleware))
