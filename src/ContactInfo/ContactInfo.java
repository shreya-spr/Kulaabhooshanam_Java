package ContactInfo;
import javax.swing.*;
import java.awt.*;

public class ContactInfo extends JFrame {
    public ContactInfo() {
        setTitle("Kulaabhooshanam");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the content panel with BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(900, 900));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Background image panel
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                Image image = Toolkit.getDefaultToolkit().getImage("images/children2.jpg");
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(780, 600));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome to Kulaabhooshanam!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        backgroundPanel.add(welcomePanel, BorderLayout.CENTER);

        // Add the background panel to the content panel
        contentPanel.add(backgroundPanel, BorderLayout.NORTH);

        // Cards panel
        JPanel cardPanel = new JPanel(new GridLayout(0, 2, 20, 0));
        JPanel card1 = createCardPanel("Contact Us at:", "");
        JPanel card2 = createCardPanel("Website owned and managed by: ", "Central Adoption Resource Agency \n Ministry of Women and Child Development \n Government of India");
        cardPanel.add(card1);
        cardPanel.add(card2);
        cardPanel.setOpaque(false);
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cardPanel.setPreferredSize(new Dimension(780, 100));


        // Green Stats Panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBackground(new Color(0, 128, 0, 191));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel statsLabel = new JLabel("21,000 children are now part of permanent families!");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statsLabel.setForeground(Color.WHITE);
        statsPanel.add(statsLabel);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 20, 10, 20),
        BorderFactory.createBevelBorder(10)));
        backgroundPanel.add(statsPanel, BorderLayout.SOUTH);

        // Create a JScrollPane and add the content panel to it
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the frame
        add(scrollPane);
        
   }
    // Helper method to create a card panel with a title and content
    private JPanel createCardPanel(String title, String content) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel contentLabel = new JLabel("<html>" + content + "</html>");
        contentLabel.setVerticalAlignment(SwingConstants.TOP);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(contentLabel, BorderLayout.CENTER);
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(300, 300)); // Adjust width and height as needed
        return cardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create and display the home page
                new ContactInfo().setVisible(true);
            }
        });
    }
}
