package ProjectRaceGame.ui;
import ProjectRaceGame.model.Car;
import ProjectRaceGame.model.Track;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class GamePanel extends JPanel { // Class Không Gian Game
    private BufferedImage trackImage;  // Biến để lưu ảnh đường đua
    private int trackScrollY = 0;
    private Track track;  // Biến để tham chiếu đến Track
    private Car car;      // Biến để tham chiếu đến Car
    private boolean gameOver;  // Biến để theo dõi trạng thái game

    // Cập nhật constructor để nhận Track, Car và gameOver từ RaceGame
    public GamePanel(Track track, Car car, boolean gameOver) {
        this.track = track;  // Lưu đối tượng track vào biến của GamePanel
        this.car = car;      // Lưu đối tượng car vào biến của GamePanel
        this.gameOver = gameOver;  // Lưu trạng thái game vào biến gameOver
        setPreferredSize(new Dimension(700, 800));
        try {
            // Đảm bảo đường dẫn đúng đến file race.png của bạn
            File imageFile = new File("src/ProjectRaceGame/ui/race.jpg");
            if (imageFile.exists()) {
                trackImage = ImageIO.read(imageFile);
                System.out.println("Đã tải thành công ảnh đường đua!");
            } else {
                System.out.println("Không tìm thấy file ảnh đường đua tại: " + imageFile.getAbsolutePath());

                // Thử tìm trong thư mục gốc của dự án
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền mặc định màu đen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Vẽ ảnh đường đua với hiệu ứng cuộn
        if (trackImage != null) {
            // Cập nhật vị trí y để tạo hiệu ứng cuộn
            trackScrollY = (trackScrollY + 2) % trackImage.getHeight();

            // Vẽ ảnh đường đua hai lần để tạo hiệu ứng cuộn liên tục
            g.drawImage(trackImage, 0, trackScrollY - trackImage.getHeight(), getWidth(), trackImage.getHeight(), null);
            g.drawImage(trackImage, 0, trackScrollY, getWidth(), trackImage.getHeight(), null);
        } else {
            // Nếu không tìm thấy ảnh, vẽ đường đua mặc định
            drawDefaultTrack(g);
        }

        // Vẽ các đối tượng game lên màn hình
        track.draw(g);  // Vẽ chướng ngại vật
        car.draw(g);  // Vẽ chiếc xe

        // Hiển thị thông báo Game Over nếu game kết thúc
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
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        repaint();
    }

    public void updateComponents(Track track, Car car, boolean gameOver) {
        this.track = track;
        this.car = car;
        this.gameOver = gameOver;
        repaint();
    }

    // Phương thức vẽ đường đua mặc định trong trường hợp không tìm thấy ảnh
    private void drawDefaultTrack(Graphics g) {
        // Vẽ nền xám cho đường đua
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Vẽ vạch kẻ đường trắng ở giữa
        g.setColor(Color.WHITE);
        int centerX = getWidth() / 2;
        for (int y = -50 + trackScrollY % 100; y < getHeight(); y += 100) {
            g.fillRect(centerX - 5, y, 10, 50);
        }

        // Vẽ vỉa hè hai bên
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 50, getHeight());
        g.fillRect(getWidth() - 50, 0, 50, getHeight());
    }
}
