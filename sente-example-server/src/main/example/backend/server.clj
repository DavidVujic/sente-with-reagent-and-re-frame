(ns example.backend.server
  (:require [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.cors :refer [wrap-cors]]
            [org.httpkit.server :as http-kit]
            [example.backend.router :as router]))

(def main-ring-handler
  (-> router/ring-routes
      (wrap-defaults site-defaults)
      (wrap-cors :access-control-allow-origin [#".*"]))) ;; TODO: don't allow any origin

(defonce web-server_ (atom nil))

(defn stop! []
  (when-let [stop-fn @web-server_] (stop-fn)))

(defn start! [port]
  (stop!)
  (let [ring-handler (var main-ring-handler)
        [port stop-fn]
        (let [stop-fn (http-kit/run-server ring-handler {:port port})]
          [(:local-port (meta stop-fn)) (fn [] (stop-fn :timeout 100))])]
    (println (str "Web server is running at port " port))
    (reset! web-server_ stop-fn)))
