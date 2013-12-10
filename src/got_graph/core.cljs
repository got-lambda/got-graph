(ns got-graph.core)

(def canvas (js/document.getElementById "canvas"))
(def ctx    (.getContext canvas "2d"))
(def ^:constant font-size 12)

(defn text-width [text]
  (+ 45 (* (count text) font-size 0.45)))

(defn draw-ball [x y text]
  (let [width (text-width text)
        height 50
        radius 15]
    (aset ctx "fillStyle" "#432123")
    (.beginPath ctx)

    (.moveTo           ctx x (+ y radius))
    (.lineTo           ctx x (- (+ y height) radius))
    (.quadraticCurveTo ctx x (+ y height) (+ x radius) (+ y height))
    (.lineTo           ctx (- (+ x width) radius) (+ y height))
    (.quadraticCurveTo ctx (+ x width) (+ y height) (+ x width) (- (+ y height) radius))
    (.lineTo           ctx (+ x width) (+ y radius))
    (.quadraticCurveTo ctx (+ x width) y (- (+ x width) radius) y)
    (.lineTo           ctx (+ x radius) y)
    (.quadraticCurveTo ctx x  y x (+ y radius))
    (.stroke ctx)

    (aset ctx "font" "bold 12px sans-serif")
    (.fillText ctx text (+ x 20) (+ y 30))))

(defn analyse-row [indent row]
  (first (reduce
          (fn [[res i] col]
            [(assoc res col {:x (+ 30 indent)
                             :y (+ 40 (* 80 i))
                             :width (text-width col)})
             (inc i)])
          [{} 0]
          row)))

(defn analyse-rows [rows]
  (second (reduce
           (fn [[indent cords] row]
             [(+ indent 20 (reduce max (map text-width row)))
              (merge cords (analyse-row indent row))])
           [15 {}]
           rows)))

(defn draw-line [x1 y1 width target-cords]
  (doseq [{x2 :x y2 :y} target-cords]
    (.beginPath ctx)
    (.moveTo ctx (+ width x1) (+ 25 y1))
    (.lineTo ctx x2 (+ 25 y2))
    (.stroke ctx)
    (.beginPath ctx)
    (.arc ctx x2 (+ 25 y2) 5 0 (* js/Math.PI 2) true)
    (.closePath ctx)
    (.fill ctx)))

(defn draw-cords-data [cords mappings]
  (doseq [[name {:keys [x y width]}] cords]
    (draw-ball x y name)
    (draw-line x y width (map cords (mappings name)))))

(def example-data
  [["node1"]
   ["node2" "Hello world longer" "node4"]
   ["node5" "node6" "node7" "node8"]])

(def example-mappings
  {"node1" ["node2" "Hello world longer" "node4"]
   "node2" ["node5"]
   "Hello world longer" ["node6"]
   "node4" ["node7" "node8"]})

(draw-cords-data (analyse-rows example-data) example-mappings)
