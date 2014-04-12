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
  	   [compojure.core :as compojure]
	   [hiccup 
  		[core :as hiccup]
		[page :as page]
		[util]]
	   [project1.route :as route]
	   [project1.blog :as blog]
           [clojure.string]))

(defn layout [contents]
 (page/html5
   [:body {:style "background-color: #363636; color: #fff;"} 
    [:h1 "Clojure webapps example"]
    [:p "This content comes from layout function"]
    contents]))
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
 {:body (str "test1, route args: " (:route-params request))})

(defn test2-handler [request]
 {:status 301 :headers {"Location" "http://github.com/ring-clojure"}})

(defn cookie-handler [request]
 {:body (layout [:div [:p "Cookies:"]
		[:pre (hiccup.util/escape-html (:cookies request))]])})

(defn session-handler [request]
 {:body (layout [:div [:p "Session:"]
		[:pre (hiccup.util/escape-html (:session request))]])})

(defn logout-handler [request]
 {:body "Logged out."
  :session nil})

(defn form-handler [login request]
 {:status 200
  :headers {"Content-type" "text/html"}
  :cookies {:username login}
  :session {:username  login
	    :cnt (inc (or (:cnt (:session request)) 0))}
  :body (layout
    	 [:div
  	   [:p "Params:"]
  	[:pre (hiccup.util/escape-html (:params request))]
 	[:p "Query string params:"]
	[:pre (hiccup.util/escape-html (:query-params request))]
	[:p "Form params:"]
	[:pre (hiccup.util/escape-html (:form-params request))]
	[:p "Multipart params:"]
	[:pre (hiccup.util/escape-html (:multipart-params request))]
	[:p "Local path:"]
	[:b (when-let [f (get-in request [:params :file :tempfile])]
  		(hiccup.util/escape-html (.getAbsolutePath f)))]])})

(compojure/defroutes route-handler 
  (compojure/context "/entries" [] 
	blog/blog-handler)
  (compojure/GET "/test1" [:as request] (test1-handler request))
  (compojure/GET "/test1/:id" [id :as request] (test1-handler request))
  (compojure/GET "/test2" [id :as request] (test2-handler request))
  (compojure/GET "/test3" [id :as request] (handlers/handler3 request))
  (compojure/ANY "/form" [login :as request] (form-handler login request))
  (compojure/GET "/cookies" [:as request] (cookie-handler request))
  (compojure/GET "/session" [:as request] (session-handler request))
  (compojure/GET "/logout" [:as request] (logout-handler request))
   )

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
