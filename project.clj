(defproject simple-agent "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.8.0"],
    [org.clojure/tools.logging "0.3.1"],
    [ch.qos.logback/logback-classic "1.1.3"],
    [schejulure "1.0.1"],
    [org.clojure/core.async "0.2.374"],
    [im.chit/hara.io.scheduler "2.3.6"],
    [me.raynes/conch "0.8.0"]
  ]
  :main ^:skip-aot simple-agent.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
