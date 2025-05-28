(ns hello.core
  (:gen-class)
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]))

(defroutes app
  (GET "/hello" request
    (let [name (get-in request [:params :name])]
      (str "Hello " (or name "World"))))
  (route/not-found "Not Found"))

(def api (-> app handler/api))

(defn -main [& args]
  (println "Starting web server on port 3000...")
  (jetty/run-jetty #'api {:port 3000 :join? false})
  (println "Server started at http://localhost:3000")
  (when args
    (println "Args:" args)))

