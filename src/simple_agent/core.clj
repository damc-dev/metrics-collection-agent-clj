(ns simple-agent.core
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [clojure.core.async :as async]
            [hara.io.scheduler :as scheduler]
            [me.raynes.conch :refer [programs with-programs let-programs] :as sh]))

(def in-chan (async/chan))

(defn start-metrics-publisher []
  (async/thread
    (while true
      (log/info "Publishing Metric: " (async/<!! in-chan)))))

(defn metric-string [output]
  (let [key (str/trim (first output)) value (str/trim (last output))]
  (let [metric (str/join "" ["key: " key " value: " value])]
    (log/info "Collected Metric: " metric)
    metric
  )))

(defn parse-output [line]
  (metric-string (str/split line #":")))

(defn collect-metrics []
  (log/info "Collecting metric")
  (programs fsutil)
  (doseq [metric (map parse-output (fsutil "volume" "diskfree" "C:" {:seq true}))] (async/>!! in-chan metric)))

(def collection-task
  {:handler collect-metrics
   :schedule "/5 * * * * * *"
   })

(def sch1 (scheduler/scheduler {:collection-task collection-task}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "Hello, World!")
  (start-metrics-publisher)
  (scheduler/start! sch1)
)
