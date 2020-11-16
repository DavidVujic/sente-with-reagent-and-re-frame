(ns example.frontend.app
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [example.frontend.events]
            [example.frontend.subscriptions]
            [example.frontend.client]))

(defn connect-button []
  [:input {:type     :button
           :value    "Connect"
           :disabled @(rf/subscribe [:app/connected])
           :on-click #(rf/dispatch [:app/connect])}])

(defn page []
  (let [push-count (or @(rf/subscribe [:app/push-count]) 0)]
    [:div
     [:p (str "Push count: " push-count)]
     [connect-button]
     [:p "(push data is logged to the console)"]]))

(defn init []
  (rf/dispatch-sync [:app/init])
  (rdom/render [page] (js/document.getElementById "root")))
