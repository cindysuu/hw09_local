package org.cis1200.minesweeper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private MineSweeper ms;
    private JLabel status;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;

    public GameBoard(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        ms = new MineSweeper();
        status = statusInit;

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
                    if (ms.getBoard()[p.y / 100][p.x / 100] == 0) {
                        ms.checkZero(p.y / 100, p.x / 100);
                    }
                    System.out.println("Left click detected" + (e.getPoint()));
                    ms.playTurn(p.x / 100, p.y / 100, "Left");
                }

                if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                    System.out.println("Right click detected" + (e.getPoint()));
                    ms.playTurn(p.x / 100, p.y / 100, "Right");
                }

                System.out.println("Point clicked = " + p.x / 100 + " " + p.y / 100);

                updateStatus();
                repaint();
            }
        });
    }

    public void reset() {
        ms.reset();
        status.setText("Find the mines!");
        repaint();
        requestFocusInWindow();
    }


    public void instructionsWindow() {
        JFrame frame = new JFrame("InstructionsWindow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLocation(500, 500);

        JLabel instructionsLabel =
                new JLabel("<html>These are the rules for playing minesweeper: " +
                        "<br/> <br/> Uncover all the squares without digging up a mine! " +
                        "Each square indicates the number of surrounding mines, and if a " +
                        "square is blank that means there are zero adjacent mines. " +
                        "Squares can be flagged to indicate a mine, but you do not " +
                        "need to flag all the mines to win the game. " +
                        "Right click to uncover a square, and left click to flag or " +
                        "unflag a square. </html>");

        instructionsLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        frame.getContentPane().add(instructionsLabel, BorderLayout.PAGE_START);
        frame.setVisible(true);
    }

    public void saveGame() {
        File boardFile = new File("BoardGameStatus");
        File isCoveredFile = new File("IsCoveredGameStatus");
        String boardPath = boardFile.getAbsolutePath();
        String isCoveredPath = isCoveredFile.getAbsolutePath();
        String boardStr = "";
        String isCoveredStr = "";

        for (int x = 0; x < ms.getBoard().length; x++) {
            for (int y = 0; y < ms.getBoard()[x].length; y++) {
                boardStr += ms.getBoard()[x][y] + ", ";
                isCoveredStr += ms.getBoardIsCovered()[x][y] + ", ";
            }
        }

        System.out.println("Game saved!");

        try {
            Files.writeString(Path.of(boardPath), boardStr, StandardCharsets.UTF_8);
            Files.writeString(Path.of(isCoveredPath), isCoveredStr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.print("Invalid Path");
        }
    }

    public void loadGame() {
        File boardFile = new File("BoardGameStatus");
        File isCoveredFile = new File("IsCoveredGameStatus");
        Scanner s = null;
        Scanner s2 = null;

        try {
            s = new Scanner(boardFile);
            s2 = new Scanner(isCoveredFile);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] str = line.split("[,]", 0);

                int iter = 0;
                while (iter < 64) {
                    for (int x = 0; x < ms.getBoard().length; x++) {
                        for (int y = 0; y < ms.getBoard()[x].length; y++) {
                            ms.getBoard()[x][y] = Integer.parseInt(str[iter].trim());
                            iter++;
                        }
                    }
                }
            }

            while (s2.hasNextLine()) {
                String line2 = s2.nextLine();
                String[] str2 = line2.split("[,]", 0);

                int iter2 = 0;
                while (iter2 < 64) {
                    for (int x = 0; x < ms.getBoard().length; x++) {
                        for (int y = 0; y < ms.getBoard()[x].length; y++) {
                            ms.getBoardIsCovered()[x][y] = Integer.parseInt(str2[iter2].trim());
                            iter2++;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                if (s2 != null) {
                    s2.close();
                }
            } catch (Exception e) {
                System.out.println("Exception 2: " + e.getMessage());
            }
        }

        updateStatus();
        repaint();
    }


    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        Font font = new Font("Arial", Font.PLAIN, 30);
        status.setFont(font);

        status.setText("Find the mines!");
        int winner = ms.checkWinner();

        if (winner == 1) {
            status.setText("You lost!");
        } else if (winner == 2) {
            status.setText("You win!");
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color lightGreen = new Color(167, 224, 146);
        g.setColor(lightGreen);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        Color darkGreen = new Color(98, 158, 99);
        g.setColor(darkGreen);
        for (int i = 100; i < BOARD_WIDTH; i += 100) {
            g.drawLine(i, 0, i, BOARD_HEIGHT);
        }

        for (int i = 100; i < BOARD_HEIGHT; i += 100) {
            g.drawLine(0, i, BOARD_WIDTH, i);
        }

        Font font = new Font("Arial Bold", Font.PLAIN, 35);
        g.setFont(font);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int state = ms.getSquare(i, j);
                String stateStr = Integer.toString(state);

                int isCoveredState = ms.getBoardIsCovered()[i][j];

                if (isCoveredState == 1) {
                    if (state == 0) {
                        fillAndDrawLine(g, i, j);
                        g.drawString(" ", 40 + 100 * j, 60 + 100 * i);

                    } else if (state == 1) {
                        fillAndDrawLine(g, i, j);
                        Color blue = new Color(115, 172, 230);
                        displayNumber(g, stateStr, blue, i, j);

                    } else if (state == 2) {
                        fillAndDrawLine(g, i, j);
                        Color pink = new Color(214, 139, 208);
                        displayNumber(g, stateStr, pink, i, j);

                    } else if (state == 3) {
                        fillAndDrawLine(g, i, j);
                        Color yellow = new Color(209, 174, 0);
                        displayNumber(g, stateStr, yellow, i, j);

                    } else if (state == 4) {
                        fillAndDrawLine(g, i, j);
                        Color purple = new Color(126, 98, 196);
                        displayNumber(g, stateStr, purple, i, j);

                    } else if (state == 10) {
                        fillAndDrawLine(g, i, j);
                        Color red = new Color(219, 109, 72);
                        g.setColor(red);
                        g.drawOval(35 + 100 * j, 35 + 100 * i, 35, 35);
                        g.fillOval(35 + 100 * j, 35 + 100 * i, 35, 35);
                    }
                } else if (isCoveredState == 2) {
                    g.setColor(darkGreen);
                    for (int x = 100; x < BOARD_WIDTH; x += 100) {
                        g.drawLine(x, 0, x, BOARD_HEIGHT);
                    }

                    for (int x = 100; x < BOARD_HEIGHT; x += 100) {
                        g.drawLine(0, x, BOARD_WIDTH, x);
                    }

                    Color orange = new Color(209, 120, 25);
                    g.setColor(orange);
                    g.drawString("F", 40 + 100 * j, 60 + 100 * i);
                }
            }
        }
    }

    public void displayNumber(Graphics g, String s, Color c, int i, int j) {
        g.setColor(c);
        g.drawString(s, 40 + 100 * j, 60 + 100 * i);
    }

    public void fillAndDrawLine(Graphics g, int i, int j) {
        Color tan = new Color(222, 197, 151);
        g.setColor(tan);
        g.fillRect(j * 100, i * 100, 100, 100);

        Color darkGreen = new Color(98, 158, 99);
        g.setColor(darkGreen);
        for (int x = 100; x < BOARD_WIDTH; x += 100) {
            g.drawLine(x, 0, x, BOARD_HEIGHT);
        }

        for (int x = 100; x < BOARD_HEIGHT; x += 100) {
            g.drawLine(0, x, BOARD_WIDTH, x);
        }
    }



        /**
         * Returns the size of the game board.
         */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
