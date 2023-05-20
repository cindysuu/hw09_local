package org.cis1200;
import org.cis1200.minesweeper.MineSweeper;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MineSweeperTest {
    private MineSweeper ms;

    @BeforeEach
    public void setUp() {
        ms = new MineSweeper();
    }

    @Test
    public void testRevealSquare() {
        ms.revealSquare(1, 3);

        /** test that square (1, 3) is revealed **/
        { assertTrue(ms.isRevealed(1, 3)); }

        /** test that other squares are not revealed **/
        { assertFalse(ms.isRevealed(1, 1)); }
        { assertFalse(ms.isRevealed(1, 2)); }
        { assertFalse(ms.isRevealed(2, 2)); }
    }

    @Test
    public void testRevealNeighborsCenterSquare() {
        ms.revealNeighbors(3, 3);

        /** test that square (3, 3) and neighbors are revealed **/
        if (ms.getBoard()[3][3] != 0) {
            { assertTrue(ms.isRevealed(3, 3)); }
        }
        if (ms.getBoard()[2][2] != 0) {
            { assertTrue(ms.isRevealed(2, 2)); }
        }
        if (ms.getBoard()[3][2] != 0) {
            { assertTrue(ms.isRevealed(3, 2)); }
        }
        if (ms.getBoard()[4][2] != 0) {
            { assertTrue(ms.isRevealed(4, 2)); }
        }
        if (ms.getBoard()[2][3] != 0) {
            { assertTrue(ms.isRevealed(2, 3)); }
        }
        if (ms.getBoard()[4][3] != 0) {
            { assertTrue(ms.isRevealed(4, 3)); }
        }
        if (ms.getBoard()[2][4] != 0) {
            { assertTrue(ms.isRevealed(2, 4)); }
        }
        if (ms.getBoard()[3][4] != 0) {
            { assertTrue(ms.isRevealed(3, 4)); }
        }
        if (ms.getBoard()[4][4] != 0) {
            { assertTrue(ms.isRevealed(4, 4)); }
        }

        /** test that other squares are not revealed **/
        { assertFalse(ms.isRevealed(2, 1)); }
        { assertFalse(ms.isRevealed(5, 3)); }
        { assertFalse(ms.isRevealed(4, 5)); }
    }

    @Test
    public void testRevealNeighborsEdgeSquare() {
        ms.revealNeighbors(7, 7);

        /** test that square (7, 7) and neighbors are revealed **/
        if (ms.getBoard()[7][7] != 0) {
            { assertTrue(ms.isRevealed(7, 7)); }
        }
        if (ms.getBoard()[6][6] != 0) {
            { assertTrue(ms.isRevealed(6, 6)); }
        }
        if (ms.getBoard()[7][6] != 0) {
            { assertTrue(ms.isRevealed(7, 6)); }
        }
        if (ms.getBoard()[6][7] != 0) {
            { assertTrue(ms.isRevealed(6, 7)); }
        }

        /** test that other squares are not revealed **/
        { assertFalse(ms.isRevealed(5, 5)); }
        { assertFalse(ms.isRevealed(6, 5)); }
        { assertFalse(ms.isRevealed(7, 5)); }
        { assertFalse(ms.isRevealed(5, 6)); }
        { assertFalse(ms.isRevealed(5, 7)); }
    }

    @Test
    public void testFlagSquare() {
        /** test flagging a square **/
        ms.flagSquare(5, 5);
        { assertTrue(ms.isFlagged(5, 5)); }
    }

    @Test
    public void testUnflagSquare() {
        /** test unflagging a square **/
        ms.flagSquare(4, 5);
        { assertTrue(ms.isFlagged(4, 5)); }
        ms.unflagSquare(4, 5);
        { assertFalse(ms.isFlagged(4, 5)); }
    }

    @Test
    public void testFlagAfterEndGame() {
        ms.setGameOver(true);
        ms.flagSquare(1, 1);
        { assertFalse(ms.isFlagged(1, 1)); }
    }

    @Test
    public void testUnflagAfterEndGame() {
        ms.flagSquare(1, 1);
        ms.setGameOver(true);
        ms.unflagSquare(1, 1);
        { assertTrue(ms.isFlagged(1, 1)); }
    }

    @Test
    public void testRevealAfterEndGame() {
        ms.setGameOver(true);
        ms.revealSquare(1, 1);
        { assertFalse(ms.isRevealed(1, 1)); }
    }

    @Test
    public void testRevealNonExistingSquare() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            ms.revealSquare(-1, 1);
        });
    }

    @Test
    public void testFlagNonExistingSquare() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            ms.flagSquare(10, 1);
        });
    }

    @Test
    public void testGetNonExistingSquare() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            ms.getSquare(8, 8);
        });
    }

    @Test
    public void testSquareExists() {
        assertTrue(ms.squareExists(3, 3));
    }

    @Test
    public void testSquaresDoesNotExist() {
        assertFalse(ms.squareExists(-3, -3));
    }

    @Test
    public void testPlayTurnGameOver() {
        ms.setGameOver(true);
        ms.playTurn(2, 5, "Left");
        assertFalse(ms.isRevealed(2, 5));
    }

    @Test
    public void testPlayTurnNonExistingSquareLeft() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            ms.playTurn(-6, 2, "Left");
        });
    }

    @Test
    public void testPlayTurnNonExistingSquareRight() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            ms.playTurn(-6, 2, "Right");
        });
    }
}
