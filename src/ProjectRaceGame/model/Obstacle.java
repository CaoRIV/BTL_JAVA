package ProjectRaceGame.model;
import java.awt.*;
import java.util.Random;


public class Obstacle { //Class Chướng ngạt vật
    private int x, y;
    private int width, height;
    private Color color;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 70;

        // Màu ngẫu nhiên cho xe chướng ngại vật
        Random rand = new Random();
        int colorChoice = rand.nextInt(5);
        switch(colorChoice) {
            case 0: this.color = Color.BLUE; break;
            case 1: this.color = Color.GREEN; break;
            case 2: this.color = Color.ORANGE; break;
            case 3: this.color = Color.MAGENTA; break;
            default: this.color = Color.CYAN; break;
        }
    }
    public boolean intersects(Obstacle other) {
        // Kiểm tra xem hai hình chữ nhật có giao nhau không
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }

    public void moveDown(int speed) {
        y += speed;  // Di chuyển chướng ngại vật xuống dưới
    }

    public void draw(Graphics g) {
        // Vẽ thân xe chướng ngại vật
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Vẽ chi tiết xe
        g.setColor(Color.BLACK);
        g.fillRect(x + 5, y + 55, 40, 10); // Kính chắn gió
        g.fillRect(x + 5, y + 5, 40, 40); // Thân xe
        //Bánh trước
        g.setColor(Color.BLACK);
        g.fillRect(x - 5, y + 5,5, 15);
        g.fillRect(x + 50, y + 5, 5, 15);
        //Bánh sau
        g.setColor(Color.BLACK);
        g.fillRect(x - 5 , y + 55, 5, 15);
        g.fillRect(x + 50, y + 55, 5, 15);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}