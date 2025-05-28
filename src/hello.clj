(ns hello
  (:gen-class)
  (:require [compojure.core :refer [defroutes GET]]
            [clojure.pprint :refer [pprint]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]))

(defroutes app
  (GET "/hello" request
    (println "query-string:" request)
    (let [name (get-in request [:params :name])
          _ (println "type" (type (get-in request [:query-string])))]
      (str "Hello " (or name "World"))))
  (route/not-found "Not Found"))

(def api (-> app handler/api))

(defn -main [& args]
  (println "Starting web server on port 3000...")
  (jetty/run-jetty #'api {:port 3000 :join? false})
  (println "Server started at http://localhost:3000")
  (when args
    (println "Args:" args)))

