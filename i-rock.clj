(ns i-rock)

(defn moderate [player-a player-b run-count]
  (reduce (fn [h _] (conj h [(player-a h)
                             (player-b h)]))
          []
          (range run-count)))

(defn winner [entry]
  (cond (= entry [:rock     :paper   ]) :right
        (= entry [:paper    :paper   ]) :tie
        (= entry [:scissors :paper   ]) :left
        (= entry [:rock     :scissors]) :left
        (= entry [:paper    :scissors]) :right
        (= entry [:scissors :scissors]) :tie
        (= entry [:rock     :rock    ]) :tie
        (= entry [:paper    :rock    ]) :left
        (= entry [:scissors :rock    ]) :right))

(defn summarize [history]
  (let [{:keys [left right tie] :or {left 0
                                     right 0
                                     tie 0}}
        (frequencies (map winner history))
        winner (cond (< left right) "Right"
                     (= left right) "Tie"
                     (> left right) "Left")]
    (str "Winner: " winner ".   "
         "Spead: " (- left right) ".   "
         "Runs: " (count history) ".   "
         "Totals: left: " left ", right: " right ", tie: " tie)))

(defn rock-strategy [history] :rock)
(defn paper-strategy [history] :paper)
(defn random-strategy [history] (first (shuffle '(:rock :paper :scissors))))

(defn histogram-strategy [history]
  ; assume that we're the right strategy
  (let [results (frequencies (map #(get % 0) history))
        s (:scissors results 0)
        r (:rock results 0)
        p (:paper results 0)]
    (cond (and (>= s p) (>= s r)) :rock
          (and (>= p s) (>= p r)) :scissors
          (and (>= r p) (>= r s)) :paper)))

(time (summarize (moderate random-strategy histogram-strategy 100)))
