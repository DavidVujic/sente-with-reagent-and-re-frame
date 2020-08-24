(ns example.backend.socket
  (:require [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer (get-sch-adapter)]
            [clojure.core.async :as async :refer (<! go-loop)]))

(let [chsk-server (sente/make-channel-socket-server! (get-sch-adapter) {:packer :edn :csrf-token-fn nil})
      {:keys [ch-recv send-fn connected-uids ajax-post-fn ajax-get-or-ws-handshake-fn]} chsk-server]
  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv)
  (def chsk-send!                    send-fn)
  (def connected-uids                connected-uids))

(defn start-example-broadcaster!
  "As an example of server>user async pushes, setup a loop to broadcast an
  event to all connected users every 10 seconds"
  []
  (let [broadcast!
        (fn [i]
          (let [uids (:any @connected-uids)]
            (doseq [uid uids]
              (println (str "user:" uid))
              (chsk-send! uid
                          [:some/broadcast
                           {:what-is-this "An async broadcast pushed from server"
                            :how-often    "Every 10 seconds"
                            :to-whom      uid
                            :i            i}]))))]

     (go-loop [i 0]
      (<! (async/timeout 10000))
      (broadcast! i)
      (recur (inc i)))))
