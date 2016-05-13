(ns simple-agent.core
  (:gen-class)
  (:require [schejulure.core :refer [schedule]]
            [clojure.tools.logging :as log]
            [clojure.core.async :as async]))

(def in-chan (async/chan))

(defn start-metrics-publisher []
  (async/thread
    (while true
      (log/info (async/<!! in-chan)))))

(defn collect-metrics []
  (log/info "Collecting metric")
  (async/>!! in-chan "Metric1"))

(def schedule-collection
  (schedule {:min (range 0 10 5)} collect-metrics ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "Hello, World!")
  (start-metrics-publisher))
