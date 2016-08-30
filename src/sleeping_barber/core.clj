(ns sleeping-barber.core
  (:require [clojure.core.async
             :as async
             :refer [>! <! >!! <!! go chan close! thread alts!! timeout]])
  (:gen-class))



(def opts {:number-of-chairs 4
           :haircut-duration 1000
           :max-customer-arrival-period 500})


(defn run [customer-queue opts]
  (let [clients-channel (chan)
        seats-channel (chan (:number-of-chairs opts))
        do-haircut (fn [customer]
                     (println "The barber is cutting hair for customer"
                              customer)
                     (<!! (timeout (:haircut-duration opts)))
                     (println "Done cutting hair for customer"
                              customer))
        serviced-customers (atom [])]

    ;; Customers coming to the barbershop
    (go
      (doseq [customer customer-queue]
        (let [arrival-interval (rand-int (:max-customer-arrival-period opts))]
          ;; wait some random interval before getting new customer
          (<!! (timeout arrival-interval))
          (println "New customer arrives: " customer)
          (when-not (async/offer! clients-channel customer)
            (println "Barber is busy, customer" customer
                     "proceeds to waiting room")
            (when-not (async/offer! seats-channel customer)
              (println "No chairs for customer" customer)))))
      (close! clients-channel)
      (close! seats-channel))

    ;; Barber's process
    (<!! (thread
           (loop [[new-customer] (alts!! [seats-channel clients-channel]
                                         :priority true)]
             (when new-customer
               (do (swap! serviced-customers conj new-customer)
                   (do-haircut new-customer)
                   (recur (alts!! [seats-channel clients-channel]
                                  :priority true)))))))
    @serviced-customers))


(defn -main
  [& args]
  (println "Opening the barbershop")
  (let [customer-queue (range 10)
        ;; customer-queue (iterate inc 1)
        serviced-customers (run customer-queue opts)]
    (println "No more customers left. Serviced customers:\n"
             serviced-customers)))
