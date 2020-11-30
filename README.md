Goal

The Goal is to implement a game with two independent units – the players –
communicating with each other using an API.

Description:
When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose
between adding one of { 1, 0, 1} to get to a number that is divisible by 3. Divide it by three. The
resulting whole number is then sent back to the original sender.
The same rules are applied until one player reaches the number 1(after the division).
For each "move", a sufficient output should get generated (mandatory: the added, and
the resulting number). 
Both players should be able to play automatically without user input. The
type of input (manual, automatic) should be optionally adjustable by the player.

Solution:
Application covers all required features. 
This game is implemented in Java and dependencies are Mockito, Junit, and log4j. 
Requirements are java, maven, and tcp. 
Game commands look like this:
ADD_PLAYER: add a player
ADD_AUTOMATIC_PLAYER - add automatic player
START_GAME – Game must have two players to start (human or computer players)
PLAY_GAME – numbers allowed to play -1, 0 or 1 
GAME_STATUS – get current game state 
LEAVE_GAME - leave the game

Note: adding two automatic players is also possible.

Implementation:
The main game is also divided in modules: the base main game module which holds the general game and player information and the game moves package which processes input and output. It has its own actions(domain), play and win logic, validators, it's a separate replaceable module. 
The game architecture is very flexible. Converting this game to another one all you need to do is replace the independent classes that cover actions, winning logic and game logic.

Business domains defined: player information, input number, output number, game move input (input after each move), game move result (result after each move).
Defined and implemented actions are: start new round with input number, check input number is within -1, 0 or 1, create output number with in range input -1, 0, 1 to make result divisible by three. Divide by three game logic, winning logic when output is one, adding real human players, adding automatic players, restarting game, playing with automatic player, letting two automatic players play together.
Functional programming approach was used to implement all the features. The goal was to use more reusable codebase and immutable objects. Application design follows Domain-Driven-Design (DDD) with makes it easier to work with business people.

Game is implemented in three parts: application controller, main game, and server. To connect controller to server streams are used. Messages are also broadcasted to all connected users. TCP/IP is used for network transfer protocol, and plain strings are used for message protocol.  

The application works like this:
ADD_PLAYER: playerA
Added player playerA to game.
ADD_AUTOMATIC_PLAYER
Added Automatic player to game.
START_GAME
Game started with number 61. playerA is next in turn.
0 
invalid instruction. Available instructions are: ADD_PLAYER: player, ADD_AUTOMATIC_PLAYER, START_GAME, PLAY_GAME: value, GAME_STATUS, LEAVE_GAME.
PLAY_GAME:-1
playerA player played -1. Result is: output 20.
Automatic player played number 1. Result is: output 7.
PLAY_GAME: 5
ERROR: invalid number, you can only choose [-1, 0, 1]
PLAY_GAME: ABC 
ERROR: Input was a string: "ABC"
ERROR: Cannot play with incorrect types.
PLAY_GAME:-1
playerA played number -1. Result is: output 2.
Automatic player played number 0. Result is: output 1, wins true.
GAME_STATUS
playerA is next in turn. Last Round result: Result is: output 1, wins true.
LEAVE_GAME
Left game.

