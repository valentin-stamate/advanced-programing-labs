# Lab 7 : TSP Game

![Program Screenshot](https://i.postimg.cc/TPNSTxwm/Capture.png)

## How it works?
> Tokens
> * They are located in the upper side of the canvas
> * Tokens are represented as a pair (i, j) with 0 <= i, j < n
> * The number of tokens is 5 * nrOfPlayers
> * Every token can be picked only once and after is picked the color becomes gray
> * The tokens can be considered edges of a complete graph

> Players
> * Are located in the bottom side of the canvas
> * The number of players is between 20 and 50
> * Every player wants to pick a token but only one can do so
> * The colour of the current player that is picking a token becomes gray
> * The score is the pair sum of the largest connected component
> * At the end of the game the color of the winner is black

> Timer
> * When the time is exceeded the game stops
 
> Features
> * Sometimes, it crashes randomly
