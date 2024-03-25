package LoginForm;
import javax.swing.*;

import App.App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import RegistrationForm.RegistrationForm;
import User.User;
public class LoginForm extends JFrame {
    private JTextField tfAadhar = new JTextField();
    private JButton btnLogin = new JButton("Login");
    private JButton btnCancel = new JButton("Cancel");
    private JPasswordField pfPassword = new JPasswordField();
    private JPanel loginPanel = new JPanel();

    public LoginForm(JFrame parent, App homePage) {
        // super(parent);
        setTitle("Login to you account here");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 430));
        // setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        System.out.println("Login form constructor is called");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel aadharLabel = new JLabel("Aadhar Number");
        panel.add(aadharLabel);
        panel.add(tfAadhar);

        JLabel pswdLabel = new JLabel("Password");
        panel.add(pswdLabel);
        panel.add(pfPassword);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aadhar = tfAadhar.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(aadhar, password);

                if (user != null) {
                    dispose();
                    // Pass the logged-in user back to HomePage
                    homePage.setUser(user);
                } else {
                    System.out.println("user in action listener: " + user);
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password is invalid",
                            "Try again.",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(btnLogin);
        panel.add(btnCancel);
        add(panel);
        pack();
        setVisible(true);

    }

    // global
    public User user;
    private User getAuthenticatedUser(String aadhar, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/kulaabhooshanam_java";
        final String USERNAME = "root";
        final String PASSWORD = "Harry@123";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // connection done !

            conn.createStatement();
            String sqlSelect = "SELECT * FROM parents WHERE p_id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
                preparedStatement.setString(1, aadhar);
                // preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.name = resultSet.getString("p_name");
                        user.email = resultSet.getString("email_id");
                        user.phno = resultSet.getString("phno");
                    } else {
                        // User not found, display message and redirect to registration page
                        JOptionPane.showMessageDialog(LoginForm.this,
                                "User not registered. Please register first.",
                                "User Not Found",
                                JOptionPane.INFORMATION_MESSAGE);
                        // Open registration form
                        RegistrationForm registrationForm = new RegistrationForm(null);
                        registrationForm.setVisible(true);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException gracefully, e.g., show an error message dialog
            JOptionPane.showMessageDialog(LoginForm.this,
                    "An error occurred while logging in. Please try again later.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return user;
    }

    public User getUser() {
        return user;
    }

    // public static void main(String[] args) {
    //     LoginForm loginForm = new LoginForm(null, null);
    //     User user = loginForm.user;

    //     // if valid user
    //     if (user != null) {
    //         System.out.println("Successful authentication of: " + user.name);
    //         System.out.println("\t Email=" + user.email);
    //         System.out.println("\t Name=" + user.name);
    //         System.out.println("\t Phone=" + user.phno);
    //     } else {
    //         System.out.println("Authentication not done !");
    //     }
    // }
}


