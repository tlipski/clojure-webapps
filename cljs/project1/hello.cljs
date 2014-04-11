(ns project1.hello)

(defn show-entries [entries]
  (let [ul-el (.getElementById js/document "entries")]
    (aset ul-el "innerHTML" ""); clean the document
    (doseq [entry entries]
     (let [li-el (.createElement js/document "li")]
       (.appendChild li-el (.createTextNode js/document (get (js->clj entry) "title")))
       (.appendChild ul-el li-el)))))

(defn load-entries []
 (let [req (js/XMLHttpRequest.)]
  (aset req "onreadystatechange"
	(fn []
		(when (= (.-readyState req) 4)
			(show-entries (JSON/parse (.-responseText req))))))
  (.open req "GET" "/entries" true)
  (.send req "")))

(defn ^:export add-entry []
 (let [title (.-value (.getElementById js/document "title"))
       req (js/XMLHttpRequest.)]
  (aset req "onreadystatechange" 
	(fn [] (when (= (.-readyState req) 4) (load-entries))))
  (.open req "POST" "/entries" true)
  (.setRequestHeader req "Content-type" "application/json")
  (.send req (.stringify js/JSON (clj->js {:title title})))))

(load-entries)
