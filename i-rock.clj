(defn moderate [player-a player-b run-count]
  (loop [runs-left run-count
         history []]
    (if (= 0 runs-left)
      history
      (recur (dec runs-left)
             (conj history [(player-a history)
                            (player-b history)])))))

(defn rock-strategy [history] :rock)
(defn paper-strategy [history] :paper)
(defn random-strategy [history] (first (shuffle '(:rock :paper :scissors))))

(defn winner [[left right]]
  (cond (and (= left :paper   ) (= right :paper   )) :tie
        (and (= left :paper   ) (= right :scissors)) :right
        (and (= left :paper   ) (= right :rock    )) :left
        (and (= left :scissors) (= right :paper   )) :left
        (and (= left :scissors) (= right :scissors)) :tie
        (and (= left :scissors) (= right :rock    )) :right
        (and (= left :rock    ) (= right :paper   )) :right
        (and (= left :rock    ) (= right :scissors)) :left
        (and (= left :rock    ) (= right :rock    )) :tie))

(defn summarize [history]
  (let [{left :left right :right :as all}
        (frequencies (map winner history))]
    (cond (< left right) (str "Left lose: " (- left right))
          (> left right) (str "Right lose: " (- left right))
          (= left right) (str "Everybody lose: " (- left right)))))

(time (summarize (moderate random-strategy rock-strategy 1000000)))
