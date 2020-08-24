(ns example.backend.main
  (:require [example.backend.router :as router]
            [example.backend.server :as server]
            [example.backend.socket :as socket]))

(defn start! []
  (router/start!)
  (server/start! 5000)
  (socket/start-example-broadcaster!))

(defn stop! []
  (router/stop!)
  (server/stop!))

(defn -main []
  (start!))
