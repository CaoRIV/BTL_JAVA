package ProjectRaceGame.model;
import ProjectRaceGame.main.RaceGame;
import ProjectRaceGame.model.Obstacle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;


public class Track { //Class làn đường đi
    private ArrayList<Obstacle> obstacles;
    private int speed;
    private int gameHeight;  // Lưu GAME_HEIGHT

    public Track(int gameHeight) {
        obstacles = new ArrayList<>();
        generateObstacles();
        speed = 3;
        this.gameHeight = gameHeight;  // Gán GAME_HEIGHT vào Track
    }
    public Obstacle createSafeObstacle() {
        Random rand = new Random();
        int lanes = 6; // Số làn đường
        int laneWidth = 100; // Chiều rộng của mỗi làn đường
        int startX = 75 ; // Vị trí bắt đầu của làn đường đầu tiên
        int attempts = 0;
        Obstacle newObstacle;
        do {
            // Chọn làn đường ngẫu nhiên
            int lane = rand.nextInt(lanes);
            int x = startX + lane * laneWidth;
            // Tạo chướng ngại vật ở vị trí cao hơn màn hình (âm)
            int y = -100 - rand.nextInt(200); // Random trong khoảng từ -300 đến -100
            newObstacle = new Obstacle(x, y);
            // Kiểm tra xem có va chạm với chướng ngại vật nào đã tồn tại không
            boolean collisionFound = false;
            for (Obstacle existingObs : obstacles) {
                // Thêm khoảng cách an toàn (ví dụ 100px)
                if (newObstacle.intersects(existingObs) ||
                        Math.abs(newObstacle.getY() - existingObs.getY()) < 100) {
                    collisionFound = true;
                    break;
                }
            }
            // Nếu không có va chạm, thoát khỏi vòng lặp
            if (!collisionFound) {
                break;
            }

            attempts++;
        } while (attempts < 10); // Giới hạn số lần thử để tránh vòng lặp vô hạn

        return newObstacle;
    }
    public void generateObstacles() {
        obstacles.clear(); // Xóa tất cả chướng ngại vật cũ
        // Tạo một số lượng chướng ngại vật ban đầu
        int initialObstacles = 5; // Hoặc số lượng khác tùy ý
        for (int i = 0; i < initialObstacles; i++) {
            obstacles.add(createSafeObstacle());
        }
    }

    // Thêm phương thức để thêm chướng ngại vật mới khi cần
    public void addNewObstacle() {
        obstacles.add(createSafeObstacle());
    }
    // Di chuyển các chướng ngại vật xuống dưới
    public void moveObstacles() {
        // Di chuyển tất cả chướng ngại vật xuống
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle obs = obstacles.get(i);
            obs.moveDown(5); // Tốc độ di chuyển
            // Nếu chướng ngại vật đã ra khỏi màn hình
            if (obs.getY() > gameHeight) {
                obstacles.remove(i); // Xóa chướng ngại vật
                addNewObstacle(); // Thêm chướng ngại vật mới
            }
        }
    }
    public void draw(Graphics g) {
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}