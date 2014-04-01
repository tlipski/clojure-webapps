(ns project1.db
  (:require [clojure.java.jdbc :as jdbc]))

(def postgresql-db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/clojure_blog"
   :user "clojure_blog"
   :password "clojure"})
