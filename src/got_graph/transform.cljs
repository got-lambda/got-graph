(ns got-graph.transform)

(def ex1 {:data 'main
          :children [{:data 'foo}
                     {:data 'bar
                      :children [{:data 'baz} {:data 'baz2}]}]})

(defn traverse [node depth]
  (let [data [depth (str (:data node))]]
    (if-let [children (:children node)]
      (conj (mapcat #(traverse % (inc depth)) children) data)
      [data])))

(defn graph->layers [graph]
  (->> (traverse graph 0)
       (group-by first)
       vals
       (map #(map second %))))

(graph->layers ex1)
