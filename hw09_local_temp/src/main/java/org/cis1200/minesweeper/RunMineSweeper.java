package org.cis1200.minesweeper;
import javax.swing.*;
import java.awt.*;

public class RunMineSweeper implements Runnable {
    public void run() {
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("MineSweeper");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);

        Font font = new Font("Arial", Font.PLAIN, 30);
        final JLabel status = new JLabel("Find the mines!");
        status.setFont(font);
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Instructions button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> board.instructionsWindow());
        control_panel.add(instructions);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // SaveGame button
        final JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(e -> board.saveGame());
        control_panel.add(saveGame);

        // LoadGame button
        final JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(e -> board.loadGame());
        control_panel.add(loadGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}