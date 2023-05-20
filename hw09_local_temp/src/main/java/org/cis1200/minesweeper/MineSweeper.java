package org.cis1200.minesweeper;
import java.util.Random;

public class MineSweeper {

    private int[][] board;
    private int[][] boardIsCovered;
    private boolean gameOver;
    private int boardWidth = 8;
    private int boardHeight = 8;
    private int numMines = 8;

    public MineSweeper() {
        reset();
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int[][] getBoardIsCovered() {
        return this.boardIsCovered;
    }

    public void setGameOver(boolean b) {
        gameOver = b;
    }

    public boolean playTurn(int c, int r, String mouseButton) {
        if (gameOver) {
            return false;
        }

        int state = boardIsCovered[r][c];
        board[r][c] = getSquare(r, c);

        if (mouseButton == "Left") {
            if (state == 0 || state == 1) {
                revealSquare(r, c);
            } else if (state == 2) {
                flagSquare(r, c);
            }
        } else if (mouseButton == "Right") {
            if (state == 0) {
                flagSquare(r, c);
            } else if (state == 1) {
                revealSquare(r, c);
            } else if (state == 2) {
                boardIsCovered[r][c] = 0;
            }
        }
        return true;
    }

    public void checkZero(int i, int j) {
        if (board[i][j] == 0 && !gameOver) {
            revealNeighbors(i, j);

            if (squareExists(i - 1, j - 1) &&
                    board[i - 1][j - 1] == 0 && !isRevealed(i - 1, j - 1)) {
                revealSquare(i - 1, j - 1);
                revealNeighbors(i - 1, j - 1);
                checkZero(i - 1, j - 1);
            }
            if (squareExists(i, j - 1) && board[i][j - 1] == 0 && !isRevealed(i, j - 1)) {
                revealSquare(i, j - 1);
                revealNeighbors(i, j - 1);
                checkZero(i, j - 1);
            }
            if (squareExists(i + 1, j - 1) &&
                    board[i + 1][j - 1] == 0 && !isRevealed(i + 1, j - 1)) {
                revealSquare(i + 1, j - 1);
                revealNeighbors(i + 1, j - 1);
                checkZero(i + 1, j - 1);
            }
            if (squareExists(i - 1, j) &&
                    board[i - 1][j] == 0 && !isRevealed(i - 1, j)) {
                revealSquare(i - 1, j);
                revealNeighbors(i - 1, j);
                checkZero(i - 1, j);
            }
            if (squareExists(i + 1, j) &&
                    board[i + 1][j] == 0 && !isRevealed(i + 1, j)) {
                revealSquare(i + 1, j);
                revealNeighbors(i + 1, j);
                checkZero(i + 1, j);
            }
            if (squareExists(i - 1, j + 1) &&
                    board[i - 1][j + 1] == 0 && !isRevealed(i - 1, j + 1)) {
                revealSquare(i - 1, j + 1);
                revealNeighbors(i - 1, j + 1);
                checkZero(i - 1, j + 1);
            }
            if (squareExists(i, j + 1) &&
                    board[i][j + 1] == 0 && !isRevealed(i, j + 1)) {
                revealSquare(i, j + 1);
                revealNeighbors(i, j + 1);
                checkZero(i, j + 1);
            }
            if (squareExists(i + 1, j + 1) &&
                    board[i + 1][j + 1] == 0 && !isRevealed(i + 1, j + 1)) {
                revealSquare(i + 1, j + 1);
                revealNeighbors(i + 1, j + 1);
                checkZero(i + 1, j + 1);
            }
        }
    }

    public void flagSquare(int r, int c) {
        if (!gameOver) {
            boardIsCovered[r][c] = 2;
        }
    }

    public void unflagSquare(int r, int c) {
        if (!gameOver) {
            boardIsCovered[r][c] = 0;
        }
    }

    public boolean isFlagged(int r, int c) {
        return boardIsCovered[r][c] == 2;
    }

    public void revealSquare(int r, int c) {
        if (!gameOver) {
            boardIsCovered[r][c] = 1;
        }
    }

    public boolean isRevealed(int r, int c) {
        return boardIsCovered[r][c] == 1;
    }

    public void revealNeighbors(int i, int j) {
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (squareExists(x, y) && !isRevealed(x, y)) {
                    if (board[x][y] != 0) {
                        revealSquare(x, y);
                    }
                }
            }
        }
    }

    public int checkWinner() {
        int count = 0;
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (isRevealed(i, j) && board[i][j] == 10) {
                    gameOver = true;
                    return 1;
                }

                if (isRevealed(i, j) && board[i][j] != 10) {
                    count++;
                }

                if (count == boardWidth * boardHeight - numMines) {
                    gameOver = true;
                    return 2;
                }
            }
        }
        return 0;
    }

    public void reset() {
        board = new int[boardWidth][boardHeight];
        boardIsCovered = new int[boardWidth][boardHeight];

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                boardIsCovered[i][j] = 0;
            }
        }

        setMines();
        setNonMines();
        gameOver = false;
    }

    public void setMines() {
        int mines = 0;
        Random rand = new Random();

        while (mines < numMines) {
            int x = rand.nextInt(0, boardWidth);
            int y = rand.nextInt(0, boardHeight);
            if (board[x][y] != 10) {
                board[x][y] = 10;
                mines++;
            }
        }
    }

    public void setNonMines() {
        int surroundingMines = 0;
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                // int curr = board[i][j];
                if (board[i][j] != 10) {
                    if (squareExists(i - 1, j - 1) && board[i - 1][j - 1] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i, j - 1) && board[i][j - 1] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i + 1, j - 1) && board[i + 1][j - 1] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i - 1, j) && board[i - 1][j] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i + 1, j) && board[i + 1][j] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i - 1, j + 1) && board[i - 1][j + 1] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i, j + 1) && board[i][j + 1] == 10) {
                        surroundingMines++;
                    }
                    if (squareExists(i + 1, j + 1) && board[i + 1][j + 1] == 10) {
                        surroundingMines++;
                    }
                    board[i][j] = surroundingMines;
                }
                surroundingMines = 0;
            }
        }
    }

    public boolean squareExists(int x, int y) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            return false;
        }
        return true;
    }

    public int getSquare(int x, int y) {
        return board[x][y];
    }
}
