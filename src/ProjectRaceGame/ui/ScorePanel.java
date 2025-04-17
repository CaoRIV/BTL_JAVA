package ProjectRaceGame.ui;
import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {// Class Điểm
    private int score;

    public ScorePanel() {
        this.score = 0;
        setPreferredSize(new Dimension(200, 800));
        setBackground(Color.LIGHT_GRAY);
    }

    public void setScore(int score) {
        this.score = score;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Score: " + score, 20, 50);
    }
}
