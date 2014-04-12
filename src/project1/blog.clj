(ns project1.blog
 (:require [clojure.data.json :as json]
	   [project1.route :as route]
	   [monger.core :as mg]
	   [monger.collection :as mc]
	   [monger.json]
	   [cheshire.core]
  	   [clojure.walk :as walk]))

(mg/connect!)
(mg/set-db! (mg/get-db "clojure_blog"))

(defn get-blog-entries []
 (mc/find-maps "entries"))

(defn add-blog-entry [entry]
 (let [entry (assoc entry :_id (org.bson.types.ObjectId.))]
  (mc/insert "entries" entry)
  entry)
  )

(defn get-blog-entry [id]
 (mc/find-one-as-map "entries" {:_id (org.bson.types.ObjectId. id)}))

(defn update-blog-entry [id entry]
 (let [old-entry (get-blog-entry id)
       new-entry (merge old-entry entry)]
  (mc/update-by-id "entries" (:_id old-entry) new-entry)
  new-entry))

(defn alter-blog-entry [id entry-values]
 (update-blog-entry id entry-values))

(defn delete-blog-entry [id]
 (when-let [entry (get-blog-entry id)]
  (mc/remove-by-id "entries" (:_id entry))
  {:id id}))


(defn json-response [data]
 (when data
  {:body (cheshire.core/generate-string data)
   :headers {"Content-type" "application/json"}}))

(defn json-body [request]
 (walk/keywordize-keys 
  (json/read-str (slurp (:body request)))))

(defn json-error-handler [handler]
 (fn [request]
  (try
   (handler request)
   (catch Throwable throwable 
     (assoc (json-response {:message (.getMessage throwable)
  			    :stacktrace (map str (.getStackTrace throwable))})
	:status 500)))))

(defn get-id [request]
 (-> request :route-params :id))

(defn get-handler [request]
 (json-response (get-blog-entries)))

(defn post-handler [request]
 (json-response (add-blog-entry (json-body request))))

(defn get-entry-handler [request]
  (json-response (get-blog-entry (get-id request))))

(defn put-handler [request]
 (json-response (update-blog-entry (get-id request) (json-body request))))

(defn delete-handler [request]
 (json-response (delete-blog-entry (get-id request))))

(def blog-handler 
 (->
  (route/routing
   (route/with-route-matches :get "/" get-handler)
   (route/with-route-matches :post "/" post-handler)
   (route/with-route-matches :get "/:id" get-entry-handler)
   (route/with-route-matches :put "/:id" put-handler)
   (route/with-route-matches :delete "/:id" delete-handler))
  json-error-handler
))
 
