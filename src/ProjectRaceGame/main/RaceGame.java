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

public class RaceGame extends JFrame { //Class chính
    private Car car;
    private Track track;
    private Timer timer;
    private boolean gameOver;
    private int score;
    private JButton restartButton;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;

    // Định nghĩa kích thước và vị trí đường đua
    private static final int GAME_WIDTH = 900;
    private static final int GAME_HEIGHT = 800;
    private static final int TRACK_WIDTH = 700; // Chiều rộng của đường đua theo yêu cầu
    private static final int ROAD_LEFT = (GAME_WIDTH - TRACK_WIDTH) / 2;
    private static final int ROAD_WIDTH = 500; // Phần xám của đường (nhỏ hơn TRACK_WIDTH)
    private static final int SIDEWALK_WIDTH = 100; // Chiều rộng vỉa hè mỗi bên

    public RaceGame() {
        setTitle("RACEGAME_QT02");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon image = new ImageIcon("src/ProjectRaceGame/ui/racelogo.jpeg");
        setIconImage(image.getImage());

        // Khởi tạo các đối tượng game
        // Đặt xe ở giữa đường đua
        int carX = GAME_WIDTH / 2 - 25; // 50 là chiều rộng của xe, đặt ở giữa màn hình
        car = new Car(carX, 650);
        track = new Track(GAME_HEIGHT);;
        gameOver = false;
        score = 0;

        // Tạo layout
        setLayout(new BorderLayout());

        // Tạo và thêm ScorePanel vào JFrame
        scorePanel = new ScorePanel();
        add(scorePanel, BorderLayout.WEST);  // Thêm ScorePanel vào bên trái

        // Tạo và thêm GamePanel vào JFrame
        gamePanel = new GamePanel(track, car, gameOver);
        add(gamePanel, BorderLayout.CENTER);  // Thêm GamePanel vào trung tâm

        // Tạo nút "Start Again" khi game over
        restartButton = new JButton("Start Again");
        restartButton.addActionListener(e -> restartGame());  // Xử lý khi nhấn nút
        restartButton.setVisible(false);  // Ẩn nút khi game chưa kết thúc
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);  // Thêm nút vào phía dưới

        // Thêm xử lý phím cho GamePanel
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;  // Không xử lý phím khi game đã kết thúc

                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    car.moveLeft();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    car.moveRight();
                }
                repaint();  // Cập nhật lại màn hình
            }
        });
        gamePanel.setFocusable(true);  // Đảm bảo gamePanel có thể nhận sự kiện bàn phím

        // Tạo một Timer để cập nhật vị trí chướng ngại vật và vẽ lại màn hình
        timer = new Timer(10, e -> updateGame());  // Cập nhật mỗi 10ms
        timer.start();
    }

    public void updateGame() {
        if (gameOver) return;  // Nếu game kết thúc, không xử lý thêm gì

        track.moveObstacles();  // Di chuyển chướng ngại vật

        // Cập nhật điểm số (dựa trên thời gian)
        long currentTime = System.currentTimeMillis();
        if (currentTime - car.getLastTime() >= 1000) {  // Cập nhật mỗi 1 giây
            score += 1;  // Cộng thêm 1 điểm mỗi giây
            car.setLastTime(currentTime);  // Cập nhật thời gian
            scorePanel.setScore(score);  // Cập nhật điểm vào ScorePanel
        }

        // Kiểm tra va chạm
        for (Obstacle obs : track.getObstacles()) {
            if (car.checkCollision(obs)) {
                gameOver = true;  // Nếu va chạm, game over
                gamePanel.setGameOver(true);
                timer.stop();  // Dừng Timer
                restartButton.setVisible(true);  // Hiển thị nút "Start Again"
                repaint();  // Cập nhật màn hình để hiển thị Game Over
                return;
            }
        }

        repaint();  // Cập nhật lại màn hình
    }

    // Reset game khi nhấn nút "Start Again"
    public void restartGame() {
        // Đặt xe ở giữa đường đua
        int carX = GAME_WIDTH / 2 - 25;
        car = new Car(carX, 650);
        track = new Track(GAME_HEIGHT);
        track.generateObstacles();
        score = 0;
        scorePanel.setScore(score);
        gameOver = false;
        restartButton.setVisible(false);
        // Cập nhật GamePanel thay vì tạo mới
        gamePanel.updateComponents(track, car, gameOver);

        // Không cần thêm KeyListener mới

        // Revalidate và repaint để đảm bảo UI được cập nhật đúng
        revalidate();
        timer.start();
        gamePanel.requestFocusInWindow();
        repaint();
    }
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            RaceGame game = new RaceGame();
            game.setVisible(true);
        });
    }
}