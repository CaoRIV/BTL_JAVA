package ProjectRaceGame.model;

import java.awt.*;

public class Car { // class xe để chơi
        private int x, y;  // Vị trí của xe
        private int width, height;
        private long lastTime;  // Lưu thời gian trước khi cập nhật điểm

public Car(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 50;
    this.height = 80;
    this.lastTime = System.currentTimeMillis();  // Khởi tạo thời gian ban đầu
}

public void moveLeft() {
    // Giới hạn xe trong phạm vi đường đua (điều chỉnh nếu cần theo ảnh đường đua của bạn)
    if (x > 75) {
        x -= 10;  // Di chuyển sang trái
    }
}

public void moveRight() {
    // Giới hạn xe trong phạm vi đường đua (điều chỉnh nếu cần theo ảnh đường đua của bạn)
    if (x + width < 700 - 75) {
        x += 10;  // Di chuyển sang phải
    }
}

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

// Kiểm tra va chạm với chướng ngại vật
public boolean checkCollision(Obstacle obstacle) {
    return x < obstacle.getX() + obstacle.getWidth() &&
            x + width > obstacle.getX() &&
            y < obstacle.getY() + obstacle.getHeight() &&
            y + height > obstacle.getY();
}

// Getter và Setter cho lastTime (thời gian)
public long getLastTime() {
    return lastTime;
}

public void setLastTime(long lastTime) {
    this.lastTime = lastTime;
}

// Getter cho y (để tính điểm)
public int getY() {
    return y;
  }
}
