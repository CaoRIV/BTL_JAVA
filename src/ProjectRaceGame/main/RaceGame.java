package ProjectRaceGame.main;
import ProjectRaceGame.model.Car;
import ProjectRaceGame.model.Track;
import ProjectRaceGame.model.Obstacle;
import ProjectRaceGame.ui.GamePanel;
import ProjectRaceGame.ui.ScorePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
// Main class: manages the game window and overall game flow
public class RaceGame extends JFrame {
    private Car car;
    private Track track;
    private Timer timer;
    private boolean gameOver;
    private int score;
    private JButton restartButton;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;

    // Constants defining window and track dimensions
    private static final int GAME_WIDTH = 900;
    private static final int GAME_HEIGHT = 800;
    private static final int TRACK_WIDTH = 700;
    private static final int ROAD_LEFT = (GAME_WIDTH - TRACK_WIDTH) / 2;
    private static final int ROAD_WIDTH = 500;
    private static final int SIDEWALK_WIDTH = 100;

    //Constructor: initializes the main window, game objects, UI panels,
    //keyboard listener, and starts the game loop timer.
    public RaceGame() {
        setTitle("RACEGAME_QT02");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon image = new ImageIcon("src/ProjectRaceGame/ui/racelogo.jpeg");
        setIconImage(image.getImage());


        int carX = GAME_WIDTH / 2 - 25;
        car = new Car(carX, 650);
        track = new Track(GAME_HEIGHT);;
        gameOver = false;
        score = 0;

        setLayout(new BorderLayout());

        scorePanel = new ScorePanel();
        add(scorePanel, BorderLayout.WEST);


        gamePanel = new GamePanel(track, car, gameOver);
        add(gamePanel, BorderLayout.CENTER);


        restartButton = new JButton("Start Again");
        restartButton.addActionListener(e -> restartGame());  // Xử lý khi nhấn nút
        restartButton.setVisible(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);


        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    car.moveLeft();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    car.moveRight();
                }
                repaint();
            }
        });
        gamePanel.setFocusable(true);

        timer = new Timer(10, e -> updateGame());
        timer.start();
    }

    //updateGame(): called by the Timer on each tick.
    public void updateGame() {
        if (gameOver) return;

        track.moveObstacles();

        long currentTime = System.currentTimeMillis();
        if (currentTime - car.getLastTime() >= 1000) {
            score += 1;
            car.setLastTime(currentTime);
            scorePanel.setScore(score);
        }

        for (Obstacle obs : track.getObstacles()) {
            if (car.checkCollision(obs)) {
                gameOver = true;
                gamePanel.setGameOver(true);
                timer.stop();
                restartButton.setVisible(true);
                repaint();
                return;
            }
        }

        repaint();
    }

    // restartGame(): resets the game state to allow a new play session.
    public void restartGame() {
        int carX = GAME_WIDTH / 2 - 25;
        car = new Car(carX, 650);
        track = new Track(GAME_HEIGHT);
        track.generateObstacles();
        score = 0;
        scorePanel.setScore(score);
        gameOver = false;
        restartButton.setVisible(false);
        gamePanel.updateComponents(track, car, gameOver);


        // Revalidate và repaint để đảm bảo UI được cập nhật đúng
        revalidate();
        timer.start();
        gamePanel.requestFocusInWindow();
        repaint();
    }

    // main(): the application entry point.
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            RaceGame game = new RaceGame();
            game.setVisible(true);
        });
    }
}