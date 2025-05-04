package ProjectRaceGame.model;

import java.awt.*;

//Car: represents the player-controlled vehicle.
//Manages position, movement boundaries, rendering, and collision detection.
public class Car {
        private int x, y;
        private int width, height;
        private long lastTime;
//Constructs a new Car at the specified position.
public Car(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 50;
    this.height = 80;
    this.lastTime = System.currentTimeMillis();  // Khởi tạo thời gian ban đầu
}
//Moves the car 10 pixels to the left, if within track boundaries.
public void moveLeft() {
    // Giới hạn xe trong phạm vi đường đua (điều chỉnh nếu cần theo ảnh đường đua của bạn)
    if (x > 75) {
        x -= 10;  // Di chuyển sang trái
    }
}
//Moves the car 10 pixels to the right, if within track boundaries.
public void moveRight() {
    // Giới hạn xe trong phạm vi đường đua (điều chỉnh nếu cần theo ảnh đường đua của bạn)
    if (x + width < 700 - 75) {
        x += 10;  // Di chuyển sang phải
    }
}
//Renders the car on the provided Graphics context.
public void draw(Graphics g) {
    // Vẽ thân xe
    g.setColor(Color.RED);
    g.fillRect(x, y, width, height);
    // Vẽ chi tiết xe
    g.setColor(Color.YELLOW);
    g.fillRect(x + 5, y - 5 , 10,5 ); // Đèn sau trái
    g.fillRect(x + 35, y - 5  , 10, 5); // Đèn sau phải
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y + 10, 40, 10); // Kính chắn gió
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y + 25, 40, 25);// Thân xe
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y + 55, 40, 10);// Kính sau
    //Bánh trước
    g.setColor(Color.BLACK);
    g.fillRect(x - 5, y + 5,5, 15);
    g.fillRect(x + 50, y + 5, 5, 15);
    //Bánh sau
    g.setColor(Color.BLACK);
    g.fillRect(x - 5 , y + 65, 5, 15); // Bánh sau trái
    g.fillRect(x + 50, y + 65, 5, 15); // Bánh sau phải
    g.setColor(Color.YELLOW);
    g.fillRect(x + 5, y + height, 10,4 ); // Đèn sau trái
    g.fillRect(x + 35, y + height , 10, 4); // Đèn sau phải
}

//Checks if this car collides with a given obstacle using AABB.
public boolean checkCollision(Obstacle obstacle) {
    return x < obstacle.getX() + obstacle.getWidth() &&
            x + width > obstacle.getX() &&
            y < obstacle.getY() + obstacle.getHeight() &&
            y + height > obstacle.getY();
}

// Returns the timestamp of the last score update.
public long getLastTime() {
    return lastTime;
}

//Sets the timestamp for the last score update.
public void setLastTime(long lastTime) {
    this.lastTime = lastTime;
}

//Returns the current y-coordinate of the car, used for scoring logic.
public int getY() {
    return y;
  }
}
