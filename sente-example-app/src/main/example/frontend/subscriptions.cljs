(ns example.frontend.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :app/push-count
 (fn [db _]
   (get-in db [:example :push])))

(rf/reg-sub
 :app/connected
 (fn [db _]
   (true? (get-in db [:example :connected]))))
