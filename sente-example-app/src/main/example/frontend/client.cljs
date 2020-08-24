(ns example.frontend.client
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [taoensso.sente :as sente]
            [taoensso.encore :as encore :refer-macros (have have?)]))

(def chsk_ (r/atom nil))
(def ch-chsk (r/atom nil))
(def chsk-send! (r/atom nil))
(def chsk-state (r/atom nil))

(def config {:type     :auto
             :packer   :edn
             :protocol :http
             :host     "localhost"
             :port     5000})

(defn create-client! []
  (let [{:keys [chsk ch-recv send-fn state]} (sente/make-channel-socket-client! "/chsk" nil config)]
    (reset! chsk_ chsk)
    (reset! ch-chsk ch-recv)
    (reset! chsk-send! send-fn)
    (reset! chsk-state state)))

(defmulti -event-msg-handler :id)

(defn event-msg-handler
  [{:as ev-msg :keys [id ?data event]}]
  (-event-msg-handler ev-msg))

(defmethod -event-msg-handler :default
  [{:keys [event]}]
  (.log js/console "Unhandled event: %s" event))

(defmethod -event-msg-handler :chsk/handshake
  [{:keys [?data]}]
  (.log js/console "Handshake" (clj->js ?data)))

(defmethod -event-msg-handler :chsk/recv
  [{:keys [?data]}]
  (rf/dispatch [:increase])
  (.log js/console "Push event from server: %s" (.stringify js/JSON (clj->js ?data))))

(defonce router_ (atom nil))

(defn  stop-router! []
  (when-let [stop-f @router_] (stop-f)))

(defn start-router! []
  (stop-router!)
  (reset! router_ (sente/start-client-chsk-router! @ch-chsk event-msg-handler)))

(defn start! []
  (create-client!)
  (start-router!))
