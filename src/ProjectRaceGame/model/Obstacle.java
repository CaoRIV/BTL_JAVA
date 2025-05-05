package ProjectRaceGame.model;
import java.awt.*;
import java.util.Random;

//Obstacle: represents an oncoming obstacle vehicle.
//Handles its position, appearance, movement, and collision bounds.
public class Obstacle {
    private int x, y;
    private int width, height;
    private Color color;

    //Constructs a new Obstacle at the specified coordinates.
    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 70;

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

    //Determines whether this obstacle intersects another obstacle.
    public boolean intersects(Obstacle other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }

    //Moves the obstacle downward by a specified speed (pixels per tick).
    public void moveDown(int speed) {
        y += speed;
    }

    //Renders the obstacle onto the provided Graphics context.
    public void draw(Graphics g) {
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

    //Returns the current x-coordinate of the obstacle.
    public int getX() {
        return x;
    }
    //Returns the current y-coordinate of the obstacle.
    public int getY() {
        return y;
    }
    //Returns the width of the obstacle.
    public int getWidth() {
        return width;
    }
    //Returns the height of the obstacle.
    public int getHeight() {
        return height;
    }
}