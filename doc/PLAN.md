# Breakout Plan
### Billy McCune


[//]: # (#### Examples)

[//]: # ()
[//]: # (You need to put blank lines to write some text)

[//]: # ()
[//]: # (in separate paragraphs.)

[//]: # ()
[//]: # ()
[//]: # (Emphasis, aka italics, with *asterisks* or _underscores_.)

[//]: # ()
[//]: # (Strong emphasis, aka bold, with **asterisks** or __underscores__.)

[//]: # ()
[//]: # (Combined emphasis with **asterisks and _underscores_**.)

[//]: # ()
[//]: # ()
[//]: # (You can also make lists:)

[//]: # (* Bullets are made with asterisks)

[//]: # (1. You can order things with numbers.)

[//]: # ()
[//]: # ()
[//]: # (You can put links in like this: [Duke CompSci]&#40;https://www.cs.duke.edu&#41;)



## Interesting Breakout Variants

 * Idea #1: Super Breakout

 * Idea #2: Jet Ball


## Paddle Ideas

 * Idea #1: Positional Bounces

 * Idea #2: Catch


## Block Ideas

 * Idea #1: power up - block when hit drops a power up

 * Idea #2: multi-hit - block has lots of health - more that one 

 * Idea #3: exploding block - block explodes destroying other blocks


## Power-up Ideas

 * Idea #1: Speed Up - ball speeds up

 * Idea #2: Extra Balls - more balls spawn

 * Idea #3: Big Ball with increased damage - ball grows in size and does more damage not bouncing off blocks until all its damage is delt (If ball does 2 damage on hit and there are two one health blocks in its way it will go through the first block and bounce off of the second)


## Cheat Key Ideas

 * Idea #1: Cheat Key L: player gains an extra life

 * Idea #2: Cheat Key R: When the player presses the R key, the ball and paddle should be reset to their starting positions.

 * Idea #3: Cheat Key P: When the player presses P it adds plus one damage to the paddle

 * Idea #4: Cheat Key 1-9: When the player presses any key 1-9, the current level should be cleared and the game should load the level corresponding to that key (or the highest one that exists).


## Level Descriptions

 * Idea #1: **Fat Man**: massive exploding block in the center with a lot of health, but when destroyed explodes all the other blocks in the level
* My rough picture is the following: (the 5's are the exploding block(s))

0 1 1 1 1 0

1 1 5 5 1 1 

1 1 5 5 1 1

1 1 1 1 1 1

1 1 1 1 1 1

0 1 1 1 1 0

0 0 0 0 0 0

0 0 0 0 0 0

1 5 1 5 1 5

0 0 0 0 0 0



 * Idea #2: Level ??? *Fast Ball*: Every block has a down buffed speed increase power up making the ball slowly speed up as the player progresses until it seems unreasonable.
 * The numbers here represent health of the blocks

9 9 9 9 9 9

1 1 1 1 1 1

7 6 4 4 6 7

1 1 1 1 1 1

1 1 1 1 1 1

3 6 9 9 6 3

0 0 0 0 0 0

0 0 0 0 0 0

1 5 1 5 1 5

0 0 0 0 0 0


## Class Ideas

 * Idea #1: Ball 
 * Methods: setSpeed() -  changes the speed of the bal for the speed up powerup

 * Idea #2: Paddle
 * Methods: ChangeColorToRandom() - changes the color of the paddle to a differnet random color from a list of colors

 * Idea #3: Block
 * Methods: setType() - changes the type of block whether exploding or not

 * Idea #4: Power-Up
 * Methods: setDuration() - sets the duration of how long the power up will last for power ups with a duration (Big Ball and potentially speed up)

