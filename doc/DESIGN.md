# Breakout Design
## Billy McCune


## Design Goals
* create a basic break out
* adds some interesting functionality
* maintain methods being relatively short

## High-Level Design
Build a basic breakout with nice colors and a decent theme.
Base most of the look off of the original breakout and Araknoid when possible. 

## Assumptions or Simplifications
Only one ball at a time for extra ball power-up

## Changes from the Plan
I had to change one of my specific cheat keys. 
In my orginal plan I said that the cheat key P would increase Paddle damage 
(the ball would end up doing more damage), but after some thought I decided to 
instead do cheat Key S which restarts the game and a even more secret cheat Key: D 
which adds damage to the ball (and is basic which is why it is extra).
I also didn't implement the changing paddle color method due to time constraints. 

## How to Add New Levels
Adding new levels is simple.
1. Go to the resources folder
2. Go to the levels folder
3. create a next .txt file named "level" + (the next level number up from the highest one)
4. next go into the main class and increase the variable Max_Level by one
5. The levels also have a structure of: number space number
6. They lay 6 numbers across and are 20 lines long
7. the numbers correspond to the block type; "0" being no block 
