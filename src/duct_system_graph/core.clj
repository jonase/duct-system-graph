(ns duct-system-graph.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as s]
            [meta-merge.core :refer [meta-merge]])
  (:gen-class))

(defn read-config [source]
  (edn/read-string {:default (fn [tag literal] literal)}
                   (slurp source)))

(defn read-system [sources]
  (->> sources
       (map read-config)
       (apply meta-merge)))

(defn graph [dependencies]
  (->> dependencies
       (mapcat (fn [[key deps]]
                 (map #(format "\"%s\" -> \"%s\"" (name key) (name %))
                      deps)))
       (s/join ";\n")))

(defn colorize [keys color]
  (->> keys
       (map name)
       (map #(format "\"%s\" [fillcolor=%s; style=filled]" % color))
       (s/join ";\n")))

(defn -main
  [& sources]
  (let [{:keys [components
                endpoints
                dependencies
                config]} (read-system sources)
        deps (graph dependencies)
        component-colors (colorize (keys components) "lawngreen")
        endpoint-colors (colorize (keys endpoints) "skyblue")
        config-colors (colorize (set/difference (set (keys config))
                                                (set/union (set (keys components))
                                                           (set (keys endpoints))))
                                "lightsalmon")]
    (printf "digraph system {\n%s\n}"
            (s/join ";\n" [component-colors endpoint-colors config-colors deps]))
    (flush)))
