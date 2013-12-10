(defproject got-graph "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2030"]]

  :plugins [[lein-cljsbuild "1.0.0-alpha2"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "got-graph"
              :source-paths ["src"]
              :compiler {
                :output-to "got_graph.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
