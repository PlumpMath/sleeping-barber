(defproject sleeping-barber "0.1.0-SNAPSHOT"
  :description "Sleeping Barber Problem"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.385"]]
  :main ^:skip-aot sleeping-barber.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
