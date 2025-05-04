package ProjectRaceGame.ui;
import javax.swing.*;
import java.awt.*;
// ScorePanel Class
public class ScorePanel extends JPanel {
    private int score;
// Constructor
    public ScorePanel() {
        this.score = 0;
        setPreferredSize(new Dimension(200, 800));
        setBackground(Color.LIGHT_GRAY);
    }
// Method setScore
    public void setScore(int score) {
        this.score = score;
        repaint();
    }
// Method paintComponent
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Score: " + score, 20, 50);
    }
}
