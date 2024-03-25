package ApplicationForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ApplicationForm extends JFrame {
    private JTextField tfAadhar = new JTextField();
    private JTextField tfAgeOfChild = new JTextField();
    private JTextField tfSexofChild = new JTextField();
    private JButton btnApply = new JButton("Apply Now");
    private JButton btnCancel = new JButton("Cancel");
    private JPanel applyPanel = new JPanel();
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;

    public ApplicationForm(JFrame parent) {
        // super(parent);
        setTitle("Apply for adoption here");
        setContentPane(applyPanel);
        setMinimumSize(new Dimension(500, 600));
        // setModal(true);
        setLocationRelativeTo(parent);  // centers it to the middle of parent
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        System.out.println("Application constructor called");


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel aadharLabel = new JLabel("Aadhar number of parent");
        panel.add(aadharLabel);
        panel.add(tfAadhar);

        JLabel sexLabel = new JLabel("Desired sex of child");
        panel.add(sexLabel);
        panel.add(tfSexofChild);

        JLabel ageLabel = new JLabel("Desired age of child");
        panel.add(ageLabel);
        panel.add(tfAgeOfChild);


        JLabel lblGenDisorder = new JLabel("Genetic Disorder:");
        panel.add(lblGenDisorder);
    
        // gbc.gridx = 1;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yesRadioButton = new JRadioButton("Yes");
        noRadioButton = new JRadioButton("No");
        ButtonGroup group = new ButtonGroup();
        group.add(yesRadioButton);
        group.add(noRadioButton);
        radioPanel.add(yesRadioButton);
        radioPanel.add(noRadioButton);
        panel.add(radioPanel);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnApply = new JButton("Apply");
        btnCancel = new JButton("Cancel");
        buttonPanel.add(btnApply);
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel);
        add(panel);


        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyForAdoption();
            }
        });


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.out.println("Application cancelled");
            }
        });

        setVisible(true);
    }

    private void applyForAdoption() {
        String aadhar = tfAadhar.getText();
        String sex = tfSexofChild.getText();
        int age = parseInteger(tfAgeOfChild.getText());
        String genDisorder = "";

        // Check which radio button is selected
        if (yesRadioButton.isSelected()) {
            genDisorder = yesRadioButton.getText();
            System.out.println("Radio yes = " + genDisorder);
        } else if (noRadioButton.isSelected()) {
            genDisorder = noRadioButton.getText();
            System.out.println("Radio no = " + genDisorder);
        }


        // check for empty fields
        if (aadhar.isEmpty() || sex.isEmpty() || genDisorder.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (age <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Please a valid number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if already applied
        if (isAlreadyApplied(aadhar)) {
            JOptionPane.showMessageDialog(this,
                    "You have already applied for adoption.",
                    "Already Applied",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }



        addToAdoptionDB(aadhar, sex, age, genDisorder);
    }

    private boolean isAlreadyApplied(String aadhar) {
        final String url = "jdbc:mysql://localhost:3306/kulaabhooshanam_java";
        final String user = "root";
        final String password = "Harry@123";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM application WHERE p_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, aadhar);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If any rows are returned, it means the user has already applied
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error occurred while checking application status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return true; // Consider error as already applied to prevent unintended application
        }
    }

    private int parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // Handle the case where input is not a valid integer
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return 0;
        }
    }

    private void addToAdoptionDB(String aadhar, String sex, int age, String genDisorder) {
        final String DB_URL = "jdbc:mysql://localhost:3306/kulaabhooshanam_java";
        final String USERNAME = "root";
        final String PASSWORD = "Harry@123";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // connection done !
            System.out.println("JDBC connection done");

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO application(p_id, sex, child_age, g_disorder)"
                            + "VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, aadhar);
            preparedStatement.setString(2, sex);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, genDisorder);


            // Insert row into table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                System.out.println("has been added!");
                JOptionPane.showMessageDialog(this,
                        "Application successful!");
            }

            stmt.close();
            conn.close();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     ApplicationForm appForm = new ApplicationForm(null);

    // }
}
