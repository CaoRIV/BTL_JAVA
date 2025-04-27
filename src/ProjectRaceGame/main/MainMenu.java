package ProjectRaceGame.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenu extends JFrame {
    private Image backgroundImage;
    private JButton startButton;

    public MainMenu() {
        // Set up the frame
        setTitle("Race Game - Main Menu");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("src/ProjectRaceGame/ui/menu_background.jpg"));
        } catch (IOException e) {
            System.out.println("Không thể tải ảnh nền menu: " + e.getMessage());
            // Use a solid color background if image can't be loaded
            backgroundImage = null;
        }

        // Set up layout
        setLayout(new BorderLayout());

        // Create a custom panel for the menu
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw background
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback to gradient background
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 50),
                            0, getHeight(), new Color(60, 60, 100));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }

                // Draw game title
                g.setFont(new Font("Arial", Font.BOLD, 72));
                g.setColor(Color.WHITE);
                String title = "RACING GAME";
                FontMetrics metrics = g.getFontMetrics();
                int titleX = (getWidth() - metrics.stringWidth(title)) / 2;
                g.drawString(title, titleX, 200);

                // Draw subtitle
                g.setFont(new Font("Arial", Font.ITALIC, 24));
                String subtitle = "Dodge the obstacles and survive!";
                metrics = g.getFontMetrics();
                int subtitleX = (getWidth() - metrics.stringWidth(subtitle)) / 2;
                g.drawString(subtitle, subtitleX, 250);
            }
        };
        menuPanel.setLayout(null); // Using null layout for absolute positioning

        // Create Start button
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(new Color(165, 42, 42));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createRaisedBevelBorder());

        // Set button size and position (centered)
        int buttonWidth = 200;
        int buttonHeight = 60;
        int buttonX = (900 - buttonWidth) / 2;
        int buttonY = 400;
        startButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Add action listener to the button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Add button to panel
        menuPanel.add(startButton);

        // Add panel to frame
        add(menuPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        // Close the menu
        dispose();

        // Start the game
        SwingUtilities.invokeLater(() -> {
            RaceGame game = new RaceGame();
            game.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}