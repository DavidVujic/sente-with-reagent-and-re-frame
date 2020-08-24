(ns example.frontend.client
  (:require [reagent.core :as r]
            [taoensso.sente :as sente]
            [taoensso.encore :as encore :refer-macros (have have?)]
            [example.frontend.handlers :as handlers]))

(def router_ (atom nil))

(def ch-chsk (r/atom nil))
(def chsk-send! (r/atom nil))
(def chsk-state (r/atom nil))

(def config {:type     :auto
             :packer   :edn
             :protocol :http
             :host     "localhost"
             :port     5000})

(defn create-client! []
  (let [{:keys [ch-recv send-fn state]} (sente/make-channel-socket-client! "/chsk" nil config)]
    (reset! ch-chsk ch-recv)
    (reset! chsk-send! send-fn)
    (reset! chsk-state state)))

(defn stop-router! []
  (when-let [stop-f @router_] (stop-f)))

(defn start-router! []
  (stop-router!)
  (reset! router_ (sente/start-client-chsk-router! @ch-chsk handlers/event-msg-handler)))

(defn start! []
  (create-client!)
  (start-router!))
