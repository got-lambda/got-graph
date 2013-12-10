(ns got-graph.transform)

(def ex1 {:data 'main
          :children [{:data 'foo}
                     {:data 'bar
                      :children [{:data 'baz} {:data 'baz2}]}]})

(defn traverse-layers [node depth]
  (let [data [depth (str (:data node))]]
    (if-let [children (:children node)]
      (conj (mapcat #(traverse-layers % (inc depth)) children) data)
      [data])))

(defn graph->layers [graph]
  (->> (traverse-layers graph 0)
       (group-by first)
       vals
       (map #(map second %))))

(defn traverse-connections [node]
  (let [data (str (:data node))
        children (:children node)
        result {data (map #(str (:data %)) children)}]
    (if (seq children)
      (conj (mapcat traverse-connections children) result)
      [result])))

(defn graph->connections [graph]
  (->> (traverse-connections graph)
       (apply merge)
       (remove #(empty? (second %)))
       (into {})))
