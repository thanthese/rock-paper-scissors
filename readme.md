**Challenge**: Is it possible to write a bot that can consistently beat random at
rock-paper-scissors?

Obviously, with pure random the answer is no -- and worse, *any* strategy up
against random becomes a random strategy itself.

But.  Computers are not random, and over millions of throws maybe a bias will
emerge that can be abused.

Here's a simple implementation I was playing with.

**Initial conclusion**: Computer random is random enough.

**Sample output**:

    Left Wins!  Spread: 89.   Runs: 10000.   Totals: left: 3378, right: 3289, tie: 3333
    Left Wins!  Spread: 160.   Runs: 10000.   Totals: left: 3388, right: 3228, tie: 3384
    Left Wins!  Spread: 25.   Runs: 10000.   Totals: left: 3359, right: 3334, tie: 3307
    Left Wins!  Spread: 13.   Runs: 10000.   Totals: left: 3347, right: 3334, tie: 3319
    Left Wins!  Spread: 47.   Runs: 10000.   Totals: left: 3339, right: 3292, tie: 3369
    Right Wins!  Spread: -43.   Runs: 10000.   Totals: left: 3300, right: 3343, tie: 3357
    Left Wins!  Spread: 75.   Runs: 10000.   Totals: left: 3387, right: 3312, tie: 3301
    Left Wins!  Spread: 110.   Runs: 10000.   Totals: left: 3389, right: 3279, tie: 3332
    Right Wins!  Spread: -39.   Runs: 10000.   Totals: left: 3339, right: 3378, tie: 3283
    Right Wins!  Spread: -73.   Runs: 10000.   Totals: left: 3294, right: 3367, tie: 3339
    "Elapsed time: 45301.682063 msecs"

**Additional notes**:

- This program is grossly inefficient in that the histogram-strategy
  calculates the frequencies each time.

- I tried making the histogram-strategy use a sliding-window of the history
  -- values 10, 100, and 1000 -- to no effect.

- random-strategy tends to favor one throw by 0.1% each round.

- To run program: `java -server -jar ~/clojure-1.2.0/clojure.jar i-rock.clj`

