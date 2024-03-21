package App;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ApplicationForm.ApplicationForm;
import LoginForm.LoginForm;
import RegistrationForm.RegistrationForm;
import User.User;
import AddChild.AddChild;

public class App extends JFrame {

    private User loggedInUser;
    private JButton loginBtn = new JButton("Login");
    private JButton registerBtn = new JButton("Register");

    public App() {
        setTitle("Kulaabhooshanam");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the content panel with BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(900, 800));
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
        JPanel card1 = createCardPanel("Who we help?", "Many babies are left at public spaces, and our volunteers bring us babies found abandoned. Many of the infants who survive are very fragile and/or premature and require hospitalization and intensive care. Our adoption process finds loving families for those unwanted and we’re with them every step of the way ensuring a safe and smooth transition to their new home.");
        JPanel card2 = createCardPanel("How can you help?", "Help us to ensure no baby is left abandoned and unloved. Volunteer your time and skills to support our programs and events. Your involvement can help create positive experiences for children and families. Help us raise awareness about the importance of adoption. Share our stories, events, and resources with your friends and community.");
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

        // Apply Now button
        JButton applyNowButton = new JButton("Apply Now");
        applyNowButton.setFont(new Font("Arial", Font.BOLD, 18));
        applyNowButton.setBackground(new Color(4, 170, 109));
        applyNowButton.setForeground(Color.WHITE);
        applyNowButton.setBorderPainted(false);
        applyNowButton.setFocusPainted(false);
        // applyNowButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         // Handle Apply Now button click
        //         // For now, let's just print a message

        //         if (loggedInUser != null) {
        //             System.out.println("User is logged in, you can apply");
        //             new ApplicationForm(null).setVisible(true);
        //         }
        //         else {
        //             System.out.println("User is not logged in, login first");
        //             LoginForm loginForm = new LoginForm(null, App.this);
        //             loginForm.setVisible(true);
        //         }
        //         System.out.println("Apply Now button clicked");

        //     }
        // });
        JPanel applyNowPanel = new JPanel();
        applyNowPanel.setOpaque(false);
        applyNowPanel.add(applyNowButton);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loggedInUser != null) {
                    // Logout user
                    loggedInUser = null;
                    updateButtonState();
                } else {
                    // Open login form
                    LoginForm loginForm = new LoginForm(null, App.this);
                    loginForm.setVisible(true);
                }
            }
        });

        // Give Up Child button
        JButton giveUpChildButton = new JButton("Want to give up a child for adoption?");
        giveUpChildButton.setFont(new Font("Arial", Font.PLAIN, 16));
        giveUpChildButton.setForeground(Color.BLUE);
        giveUpChildButton.setBorderPainted(false);
        giveUpChildButton.setFocusPainted(false);
        giveUpChildButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle Give Up Child button click
                // For now, let's just print a message
                System.out.println("Give Up Child button clicked");
                new AddChild().setVisible(true);
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registerform = new RegistrationForm(null);
                registerform.setVisible(true);
            }
        });
        updateButtonState();

        applyNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("apply button clicked!");
                if (loggedInUser != null) {
                    System.out.println("user is logged in, you can apply!");
                    new ApplicationForm(null);

                } else {
                    System.out.println("user is not logged in, login first");
                    // Open login form
                    LoginForm loginForm = new LoginForm(null, App.this);
                    loginForm.setVisible(true);
                }


            }
        });

        // Profile Button
        JButton profileButton = new JButton("Profile");

        JPanel giveUpChildPanel = new JPanel();
        giveUpChildPanel.setOpaque(false);
        giveUpChildPanel.add(giveUpChildButton);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(loginBtn);
        buttonsPanel.add(profileButton);
        buttonsPanel.add(registerBtn);
        buttonsPanel.add(applyNowPanel);
        buttonsPanel.add(giveUpChildPanel);

        contentPanel.add(buttonsPanel, BorderLayout.SOUTH);

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

    public void setUser(User user) {
        loggedInUser = user;
        updateButtonState();
    }

    private void updateButtonState() {
        if (loggedInUser != null) {
            loginBtn.setText("Logout");
        } else {
            loginBtn.setText("Login");
        }
    }

    public void closeHomePage() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create and display the home page
                App app = new App();
                app.setVisible(true);
            }
        });
    }
}
