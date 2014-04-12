(ns project1.blog
 (:require [clojure.data.json :as json]
	   [project1.route :as route]
	   [monger.core :as mg]
	   [monger.collection :as mc]
	   [monger.json]
	   [cheshire.core]
	   [liberator.core :as liberator]
	   [compojure.core :as compojure]
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



(defn json-body [request]
 (walk/keywordize-keys 
  (json/read-str (slurp (:body request)))))


(defn get-id [request]
 (-> request :route-params :id))

(defn get-handler [request]
 (get-blog-entries))

(defn post-handler [request]
 (add-blog-entry (json-body request)))

(defn get-entry-handler [request]
  (get-blog-entry (get-id request)))

(defn put-handler [request]
 (update-blog-entry (get-id request) (json-body request)))

(defn delete-handler [request]
 (delete-blog-entry (get-id request)))

(liberator/defresource blog-list-resource []
 :allowed-methods [:get :post]
 :available-media-types ["application/json"]
 :available-charsets ["utf-8"]
 :post! (fn [ctx]
   	   (add-blog-entry (json-body (:request ctx))))
 :handle-ok (fn [_] (get-blog-entries)))

(liberator/defresource blog-entry-resource [id]
 :allowed-methods [:get :put :delete]
 :available-media-types ["application/json"]
 :available-charsets ["utf-8"]
 :exists? (fn [_] (get-blog-entry id))
 :put! (fn [ctx] (update-blog-entry id (json-body (:request ctx))))
 :can-put-to-missing? false
 :delete! (fn [_] (delete-blog-entry id))
 :handle-ok (fn [_] (get-blog-entry id)))

(compojure/defroutes blog-handler
 (compojure/ANY "/:id" [id] (blog-entry-resource id))
 (compojure/ANY "/" [] (blog-list-resource)))

 
