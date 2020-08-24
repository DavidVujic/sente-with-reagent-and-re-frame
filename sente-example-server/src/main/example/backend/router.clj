(ns example.backend.router
  (:require [taoensso.sente :as sente]
            [compojure.core :as comp :refer (defroutes GET POST)]
            [compojure.route :as route]
            [example.backend.socket :as socket]
            [example.backend.handlers :as handlers]))

(defroutes ring-routes
  (GET  "/chsk"  ring-req (socket/ring-ajax-get-or-ws-handshake ring-req))
  (POST "/chsk"  ring-req (socket/ring-ajax-post                ring-req))
  (route/not-found "Not found"))

(defonce router_ (atom nil))

(defn stop! []
  (when-let [stop-fn @router_] (stop-fn)))

(defn start! []
  (stop!)
  (reset! router_ (sente/start-server-chsk-router! socket/ch-chsk handlers/event-msg-handler)))
