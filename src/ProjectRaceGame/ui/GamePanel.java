package ProjectRaceGame.ui;
import ProjectRaceGame.model.Car;
import ProjectRaceGame.model.Track;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
/* GamePanel: the main drawing canvas for the racing game.
 * It handles rendering the scrolling track, obstacles, the player car,
 * and the Game Over overlay .*/
public class GamePanel extends JPanel {
    private BufferedImage trackImage;
    private int trackScrollY = 0;
    private Track track;
    private Car car;
    private boolean gameOver;

    //Constructs a new GamePanel.
    public GamePanel(Track track, Car car, boolean gameOver) {
        this.track = track;
        this.car = car;
        this.gameOver = gameOver;
        setPreferredSize(new Dimension(700, 800));
        try {
            File imageFile = new File("src/ProjectRaceGame/ui/race.jpg");
            if (imageFile.exists()) {
                trackImage = ImageIO.read(imageFile);
                System.out.println("Đã tải thành công ảnh đường đua!");
            } else {
                System.out.println("Không tìm thấy file ảnh đường đua tại: " + imageFile.getAbsolutePath());

                imageFile = new File("race.png");
                if (imageFile.exists()) {
                    trackImage = ImageIO.read(imageFile);
                    System.out.println("Đã tải thành công ảnh đường đua từ thư mục gốc!");
                } else {
                    System.out.println("Không tìm thấy file ảnh đường đua ở thư mục gốc.");
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tải ảnh đường đua: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //paintComponent: renders the entire game frame.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (trackImage != null) {
            trackScrollY = (trackScrollY + 2) % trackImage.getHeight();

            g.drawImage(trackImage, 0, trackScrollY - trackImage.getHeight(), getWidth(), trackImage.getHeight(), null);
            g.drawImage(trackImage, 0, trackScrollY, getWidth(), trackImage.getHeight(), null);
        }
        track.draw(g);
        car.draw(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 58));
            String message = "GAME OVER";
            FontMetrics metrics = g.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            int y = getHeight() / 2;
            g.drawString(message, x, y);
        }
    }

    //setGameOver: updates the gameOver flag and repaints the panel to show or hide the Game Over overlay.
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        repaint();
    }

    //updateComponents: refreshes the panel’s references to the Track, Car, and gameOver state, then triggers a repaint.
    public void updateComponents(Track track, Car car, boolean gameOver) {
        this.track = track;
        this.car = car;
        this.gameOver = gameOver;
        repaint();
    }
}
