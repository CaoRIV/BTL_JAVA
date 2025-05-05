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
    this.lastTime = System.currentTimeMillis();
}
//Moves the car 10 pixels to the left, if within track boundaries.
public void moveLeft() {
    if (x > 75) {
        x -= 10;
    }
}
//Moves the car 10 pixels to the right, if within track boundaries.
public void moveRight() {
    if (x + width < 700 - 75) {
        x += 10;
    }
}
//Renders the car on the provided Graphics context.
public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(x, y, width, height);

    g.setColor(Color.YELLOW);
    g.fillRect(x + 5, y - 5 , 10,5 );
    g.fillRect(x + 35, y - 5  , 10, 5);
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y + 10, 40, 10);
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y + 25, 40, 25);
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y + 55, 40, 10);

    g.setColor(Color.BLACK);
    g.fillRect(x - 5, y + 5,5, 15);
    g.fillRect(x + 50, y + 5, 5, 15);

    g.setColor(Color.BLACK);
    g.fillRect(x - 5 , y + 65, 5, 15);
    g.fillRect(x + 50, y + 65, 5, 15);
    g.setColor(Color.YELLOW);
    g.fillRect(x + 5, y + height, 10,4 );
    g.fillRect(x + 35, y + height , 10, 4);
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
