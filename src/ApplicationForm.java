import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ApplicationForm extends JDialog {
    private JTextField tfAadhar;
    private JTextField tfAgeOfChild;
    private JTextField tfSexofChild;
    private JButton btnApply;
    private JButton btnCancel;
    private JPanel applyPanel;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;

    public ApplicationForm(JFrame parent) {
        super(parent);
        setTitle("Apply for adoption here");
        setContentPane(applyPanel);
        setMinimumSize(new Dimension(500, 600));
        setModal(true);
        setLocationRelativeTo(parent);  // centers it to the middle of parent
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        System.out.println("Application constructor called");


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
        final String DB_URL = "jdbc:mysql://localhost:3306/kulaabhooshanam_java";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
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
        final String PASSWORD = "";

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

    public static void main(String[] args) {
        ApplicationForm appForm = new ApplicationForm(null);

    }
}
