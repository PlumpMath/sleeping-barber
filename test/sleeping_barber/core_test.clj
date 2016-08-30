(ns sleeping-barber.core-test
  (:require [clojure.test :refer :all]
            [sleeping-barber.core :refer [run]]))

(deftest single-barber-test
  (testing "some common sense assumption"
    ;; when customers arrive in packs only :number-of-chairs+1 customers will be
    ;; served (:haircut-duration >> :max-customer-arrival-period)
    (let [opts {:number-of-chairs (rand-int 10)
                :max-customer-arrival-period 0
                :haircut-duration 1000}
          number-of-customers (inc (:number-of-chairs opts))
          serviced-customers (run (range number-of-customers) opts)]
      (is (= (count serviced-customers) (inc (:number-of-chairs opts)))))
    ;; the opposite case where barber is mostly sleeping should ensure all
    ;; clients are serviced
    (let [opts {:number-of-chairs (rand-int 10)
                :max-customer-arrival-period 100
                :haircut-duration 0}
          number-of-customers (+ (:number-of-chairs opts) 10)
          serviced-customers (run (range number-of-customers) opts)]
      (is (= (count serviced-customers) number-of-customers)))

    ;; if the time it takes to do one haircut is twice of customer arrival time
    ;; then the number of serviced customers should be less or equal to half of
    ;; the customers + number of chairs
    (let [opts {:number-of-chairs (rand-int 10)
                :max-customer-arrival-period 100
                :haircut-duration 200}
          number-of-customers (+ (:number-of-chairs opts) 10)
          serviced-customers (run (range number-of-customers) opts)]
      (is (<= (count serviced-customers) (+ (/ number-of-customers 2)
                                            (:number-of-chairs opts)))))
    ))
