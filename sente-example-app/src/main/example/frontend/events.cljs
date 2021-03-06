(ns example.frontend.events
  (:require [re-frame.core :as rf]
            [example.frontend.client :as client]))

(rf/reg-event-db
 :app/init
 (fn [_ _]
   {:example {}}))

(rf/reg-event-db
 :app/connected
 (fn [db [_ value]]
   (assoc-in db [:example :connected] value)))

(rf/reg-event-db
 :app/increase
 (fn [db _]
   (update-in db [:example :push] inc)))

(rf/reg-event-fx
 :app/connect
 (fn [_ _]
   (client/start!)
   {:dispatch [:app/connected true]}))
