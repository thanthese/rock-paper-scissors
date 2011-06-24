;;
; Stephen Mann
; June 2011
;
; Challenge: is it possible to write a bot that can consistently beat random at
; rock-paper-scissors?
;
; Obviously, with pure random the answer is no -- and worse, *any* strategy up
; against random becomes a random strategy itself.
;
; But.  Computers are not random, and over millions of throws maybe a bias will
; emerge that can be abused.
;
; Here's a simple implementation I was playing with.  Initial conclusion:
; computer random is random enough.
;
; Sample output:
;
;   Left Wins!  Spread: 89.   Runs: 10000.   Totals: left: 3378, right: 3289, tie: 3333
;   Left Wins!  Spread: 160.   Runs: 10000.   Totals: left: 3388, right: 3228, tie: 3384
;   Left Wins!  Spread: 25.   Runs: 10000.   Totals: left: 3359, right: 3334, tie: 3307
;   Left Wins!  Spread: 13.   Runs: 10000.   Totals: left: 3347, right: 3334, tie: 3319
;   Left Wins!  Spread: 47.   Runs: 10000.   Totals: left: 3339, right: 3292, tie: 3369
;   Right Wins!  Spread: -43.   Runs: 10000.   Totals: left: 3300, right: 3343, tie: 3357
;   Left Wins!  Spread: 75.   Runs: 10000.   Totals: left: 3387, right: 3312, tie: 3301
;   Left Wins!  Spread: 110.   Runs: 10000.   Totals: left: 3389, right: 3279, tie: 3332
;   Right Wins!  Spread: -39.   Runs: 10000.   Totals: left: 3339, right: 3378, tie: 3283
;   Right Wins!  Spread: -73.   Runs: 10000.   Totals: left: 3294, right: 3367, tie: 3339
;   "Elapsed time: 45301.682063 msecs"
;
; Additional notes:
;
; - This program is grossly inefficient in that the histogram-strategy
;   calculates the frequencies each time.
;
; - I tried making the histogram-strategy use a sliding-window of the history
;   -- values 10, 100, and 1000 -- to no effect.
;
; - random-strategy tends to favor one throw by 0.1% each round.
;
; - To run program: java -server -jar ~/clojure-1.2.0/clojure.jar i-rock.clj
;

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
  (let [{:keys [left right tie] :or {left 0 right 0 tie 0}}
        (frequencies (map winner history))]
    (str (cond (< left right) "Right Wins!  "
               (= left right) "Tie Game.  "
               (> left right) "Left Wins!  ")
         "Spread: " (- left right) ".   "
         "Runs: " (count history) ".   "
         "Totals: left: " left ", right: " right ", tie: " tie)))

(defn rock-strategy [history] :rock)
(defn paper-strategy [history] :paper)
(defn random-strategy [history] (first (shuffle '(:rock :paper :scissors))))
(defn histogram-strategy [history]
  ; assume that we're the right strategy
  (let [{r :rock p :paper s :scissors :or {r 0 p 0 s 0}}
        (frequencies (map #(get % 0) history))]
    (cond (and (>= s p) (>= s r)) :rock
          (and (>= p s) (>= p r)) :scissors
          (and (>= r p) (>= r s)) :paper)))

(time (dotimes [_ 10]
        (println (summarize (moderate random-strategy
                                      histogram-strategy
                                      10000)))))
