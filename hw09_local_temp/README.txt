=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: cindysu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays -- I used two 2D arrays for minesweeper. The board array represents the minesweeper game board that
  would be displayed if the entire board is uncovered (i.e. mines and number squares), and the boardIsCovered array
  represents the status of the squares on the board (covered, uncovered, flagged, or unflagged). This allows the board
  to be initialized by first assigning mines to random squares, then counting the number of surrounding mines each
  square that is not a mine has. The boardIsCovered array is updated when the user leftclicks on a square to uncover
  it, or rightclicks on a square to flag or unflag it. I used 2D int arrays because the game board is two-dimensional
  and integers could be easily updated and checked for equality.

  2. File I/O -- File I/O is used to store the game state so that the user can save their game and resume it without
  inturruption. This is done by reading the 2D arrays that contain the integers within the game board, and writing it
  into a text file. This file is then read when the user wants to load the game, and updated accordingly to represent
  the minesweeper board.

  3. JUnit Testable Component -- I wrote JUnit testable test cases to ensure that all the methods of the game work as
  intended. For instance, I checked whether squares were revealed properly according to the coordinates the user
  clicked on, if the neighbors of a square were uncovered correctly during the recursive method, and whether the user
  could flag and unflag a square that is not uncovered. I also tested edge cases such as when a coordinate the user
  clicks on is out of bounds, and checked that nothing on the board could be clicked/flagged/unflagged after the game
  ends.

  4. Recursion -- I implemented a recursive function that checks surrounding squares for blank squares and uncovers
  them when the user clicks on a blank square (square that does not have any surrounding mines). This recursive call
  continues to uncover squares until a surrounding square has neighboring mines or has no neighboring square (is an
  edge square and has no more neighbors to uncover). Recursion is used because it is much more efficient than using
  loops to determine the blank squares that could be connected to a blank square the user clicks on.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Two of the main classes in my game are MineSweeper and GameBoard. The MineSweeper class
  implements the functionality of the game (initializes the game board, recurse when the
  user clicks on a blank square, checks if the game has been won/lost), and the
  GameBoard class uses Java Swing and GUI to display and update the minesweeper board
  that is presented to the user.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  One area in which I had to change some of my code was in implementing encapsulation
  for the 2D arrays, since they were only to be accessed by the MineSweeper class.
  Also, thinking about the logic behind implementing the recursive method that uncovers
  all the surrounding blank squares when the user clicked on a blank square was pretty
  challenging because it required calls to many methods in MineSweeper.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I separated functionality by avoiding repeating code and instead creating methods
  that could be called whenever the same piece of code was needed. This makes it so that
  the code is both shorter and able to be modified easily (only need to change code in
  one method for the change to be implemented everywhere). Private state is encapsulated
  by all the local variables in MineSweeper being private. Get methods are used to access
  these variables in GameBoard. If I were given the chance to refactor, I would create a
  method in paintComponent that allows the minesweeper board's size to be adjusted
  efficiently.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
