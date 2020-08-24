(ns example.frontend.app
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [example.frontend.events]
            [example.frontend.subscriptions]
            [example.frontend.client]))

(defn connect-button []
  [:input {:type     :button
           :value    "Connect"
           :disabled @(rf/subscribe [:connected])
           :on-click #(rf/dispatch [:connect])}])

(defn page []
  (let [push-count (or @(rf/subscribe [:push-count]) 0)]
    [:div
     [:p (str "Push count: " push-count)] 
     [connect-button]]))

(defn init []
  (rf/dispatch-sync [:init])
  (rdom/render [page] (js/document.getElementById "root")))
