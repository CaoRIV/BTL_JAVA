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

//Track: manages obstacle generation, movement, and rendering within the game area.
public class Track {
    private ArrayList<Obstacle> obstacles;
    private int speed;
    private int gameHeight;

    //Constructs a Track object.
    public Track(int gameHeight) {
        obstacles = new ArrayList<>();
        generateObstacles();
        speed = 3;
        this.gameHeight = gameHeight;
    }

    //Creates a new obstacle positioned safely above the visible area in a random lane, ensuring no collision with existing obstacles.
    public Obstacle createSafeObstacle() {
        Random rand = new Random();
        int lanes = 6;
        int laneWidth = 100;
        int startX = 75 ;
        int attempts = 0;
        Obstacle newObstacle;
        do {
            int lane = rand.nextInt(lanes);
            int x = startX + lane * laneWidth;
            int y = -100 - rand.nextInt(200);
            newObstacle = new Obstacle(x, y);
            boolean collisionFound = false;
            for (Obstacle existingObs : obstacles) {
                if (newObstacle.intersects(existingObs) ||
                        Math.abs(newObstacle.getY() - existingObs.getY()) < 100) {
                    collisionFound = true;
                    break;
                }
            }
            if (!collisionFound) {
                break;
            }

            attempts++;
        } while (attempts < 10);

        return newObstacle;
    }

    //Clears existing obstacles and generates an initial set.
    public void generateObstacles() {
        obstacles.clear();
        int initialObstacles = 5;
        for (int i = 0; i < initialObstacles; i++) {
            obstacles.add(createSafeObstacle());
        }
    }

    //Adds a single new obstacle to the track using safe placement logic.
    public void addNewObstacle() {
        obstacles.add(createSafeObstacle());
    }

    //Moves all obstacles downward by a fixed amount each tick.
    public void moveObstacles() {
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle obs = obstacles.get(i);
            obs.moveDown(5);
            if (obs.getY() > gameHeight) {
                obstacles.remove(i);
                addNewObstacle();
            }
        }
    }

    //Renders all obstacles onto the provided Graphics context.
    public void draw(Graphics g) {
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }
    }

    //Returns the current list of active obstacles for collision detection.
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}