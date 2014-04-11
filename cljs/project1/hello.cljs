(ns project1.hello)

(let [el (.createElement js/document "a")
      txt (.createTextNode js/document "Hello world!")]
 (.appendChild el txt)
 (.setAttribute el "href" "#clojure")
 (.addEventListener el "click"
		    (fn [] (js/alert (str "Clicked: " (.-outerHTML el)))))
 (.appendChild (.getElementById js/document "holder") el))
