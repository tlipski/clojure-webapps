(defproject project1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler project1.core/route-handler
     	 :init project1.core/on-init
         :port 4001
  	 :destroy project1.core/on-destroy}
  :dependencies [[org.clojure/clojure "1.5.1"]
   	         [ring "1.2.0"]])
