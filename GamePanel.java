// Carter Yee, Aryan Patel, and Aditya Savarkar
// Arcade Snake Game

import java.awt.*;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private ArrayList<Point> snake;
    String direction = "right";
    Boolean fruitEaten = true;
    int randX = (int)(Math.random() * 25) * 20 + 20; // (int)(Math.random() * 10) * 50 + 50; bigger size
    int randY = (int)(Math.random() * 25) * 20 + 20; // (int)(Math.random() * 10) * 50 + 50;
    int prevKeyCode = 39;
    int score;
    Point fruit;
    boolean gameOver;
    boolean start = true;
    Image img;
    BufferedImage myPic;

    Timer gameLoop;

    public GamePanel() {
        setBackground(Color.black);
        addKeyListener(this);
        snake = new ArrayList<Point>();
        setFocusable(true);
        int x = 300;
        for (int i = 0; i < 3; i++) {
            snake.add(new Point(x, 300));
            x -= 20;
        }
        //snake.add(new Point(350, 350));
        fruit = new Point();
        gameLoop = new Timer(100, this);
        gameOver = false;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        addFruit(g);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == prevKeyCode - 2 || e.getKeyCode() == prevKeyCode + 2) {
            direction = direction;
        } else if (e.getKeyCode() == 37) {
            direction = "left";
            prevKeyCode = e.getKeyCode();
        } else if (e.getKeyCode() == 38) {
            direction = "up";
            prevKeyCode = e.getKeyCode();
        } else if (e.getKeyCode() == 39) {
            direction = "right";
            prevKeyCode = e.getKeyCode();
        } else if (e.getKeyCode() == 40) {
            direction = "down";
            prevKeyCode = e.getKeyCode();
        }
        if (start = true) {
            gameLoop.start();
            start = false;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            gameLoop.stop();
        } else {
            move();
            repaint();
        }
    }

    public void draw(Graphics g) {
        // Grid
        /*for (int i = 0; i < 750/20; i++) {
            g.drawLine(i * 20, 0, i * 20, 750);
            g.drawLine(0, i * 20, 750, i * 20);
        }*/

        // Snake Head
        g.setColor(Color.orange);
        g.fill3DRect((int) snake.get(0).getX(), (int) snake.get(0).getY(), 20, 20, true); // 50, 50

        // Snake Body
        g.setColor(Color.green);
        for (int i = 1; i < snake.size(); i++) {
            g.fill3DRect((int) snake.get(i).getX(), (int) snake.get(i).getY(), 20, 20, true); // 50, 50
        }

        // Score
        g.setColor(Color.white);
        g.drawString("Score: " + score, 10, 20);
        if (gameOver) {
            g.drawString("GAME OVER", 350, 350);
        }
    }

    public void move() {
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).setLocation(snake.get(i - 1));
        }

        if (direction == "left") {
            snake.get(0).translate(-20, 0); // -50
        } else if (direction == "right") {
            snake.get(0).translate(20, 0); // 50
        } else if (direction == "up") {
            snake.get(0).translate(0, -20); // -50
        } else if (direction == "down") {
            snake.get(0).translate(0, 20); // 50
        }

        // Snake head collides into body
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).equals(snake.get(i))) {
                gameOver = true;
            }
        }

        // Snake eats fruit
        if (snake.get(0).equals(fruit)) {
            fruitEaten();
        }

        // Snake hits wall
        if (snake.get(0).getX() < 0 || snake.get(0).getX() > 750) {
            gameOver = true;
        }
        if (snake.get(0).getY() < 0 || snake.get(0).getY() > 680) {
            gameOver = true;
        }
    }

    public void fruitEaten() {
        snake.add(new Point((int)fruit.getX(), (int)fruit.getY()));
        score++;
        fruitEaten = false;
    }

    public void addFruit(Graphics g) {
        g.setColor(Color.red);

        if (!fruitEaten) {
            do {
                randX = (int)(Math.random() * 25) * 20 + 20; // (int)(Math.random() * 10) * 50 + 50; bigger size
                randY = (int)(Math.random() * 25) * 20 + 20; // (int)(Math.random() * 10) * 50 + 50;
            } while (checkFruit());

            fruitEaten = true;
        }

        fruit.setLocation(randX, randY);

        try {
            myPic = ImageIO.read(new File("red-apple-with-leaf-it-is-black-background_782990-33.png"));
        } catch(IOException ex) {
            g.fillOval((int)fruit.getX(), (int)fruit.getY(), 20, 20); // 50, 50
        }

        g.drawImage(myPic, (int)fruit.getX(), (int)fruit.getY(), 20, 20, this);
    }

    public boolean checkFruit() {
        for (int i = 0; i < snake.size(); i++) {
            if (snake.get(i).equals(new Point(randX, randY))) {
                return true;
            }
        }

        return false;
    }
}
