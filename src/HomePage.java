import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JDialog {
    private JButton loginBtn;
    private JButton registerBtn;
    private JPanel homePanel;
    private JButton btnApply;

    // To store the logged-in user
    private User loggedInUser;

    public HomePage(JFrame parent) {
        // add constructor stuff here first
        super(parent);
        setTitle("Home page of user side");
        setContentPane(homePanel);
        setMinimumSize(new Dimension(650, 450));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        System.out.println("home page constructor called");

        loggedInUser = null;


        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loggedInUser != null) {
                    // Logout user
                    loggedInUser = null;
                    updateButtonState();
                } else {
                    // Open login form
                    LoginForm loginForm = new LoginForm(null, HomePage.this);
                    loginForm.setVisible(true);
                }
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

        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("apply button clicked!");
                if (loggedInUser != null) {
                    System.out.println("user is logged in, you can apply!");
                    ApplicationForm appForm = new ApplicationForm(null);

                } else {
                    System.out.println("user is not logged in, login first");
                    // Open login form
                    LoginForm loginForm = new LoginForm(null, HomePage.this);
                    loginForm.setVisible(true);
                }


            }
        });

        setVisible(true);
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
        HomePage home = new HomePage(null);
    }
}
