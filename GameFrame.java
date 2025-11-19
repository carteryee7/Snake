// Carter Yee, Aryan Patel, and Aditya Savarkar
// Arcade Snake Game

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        GamePanel panel = new GamePanel();
        panel.requestFocus();
        add(panel);
        setTitle("Snake");
        setPreferredSize(new Dimension(750, 750));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setResizable(false);
    }
}
