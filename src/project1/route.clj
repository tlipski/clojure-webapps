(ns project1.route)

(defn route-matches [verb path-spec request]
 (when (= (:request-method request) verb)
  (let [path-regex (.replaceAll path-spec "/:([^/]+)" "/([^/]+)")
   	path-matches (re-matches (re-pattern path-regex) (:uri request))]
	(println "Path regex for" path-spec "is" path-regex
		 "match result:" path-matches)
	(when path-matches
	  (let [route-arg-names (re-seq #"/:([^/]+)" path-spec)
		route-arg-names-and-vals (map conj route-arg-names (rest path-matches))
		route-arg-map (->> route-arg-names-and-vals
				  (map rest)
				  (map (fn [[x y]] [(keyword x) y]))
				  (into {}))]
		(println "Matched request, arg names are" route-arg-names
			 ", matched to values" route-arg-names-and-vals
			 ", arg map" route-arg-map)
		(assoc request
		  :route-params route-arg-map))))))

(defn routing [& routes]
 (fn [request]
  (some #(% request) routes)))

(defn with-route-matches [verb path-spec fun]
 (fn [request]
  (when-let [request (route-matches verb path-spec request)]
   (fun request))))
