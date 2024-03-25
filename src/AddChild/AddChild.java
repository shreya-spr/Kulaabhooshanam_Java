package AddChild;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddChild extends JDialog {
    private JTextField ageField, dobField, nameField, geneticDisorderField;
    private JComboBox<String> sexComboBox;
    private JButton submitButton;
    private Connection connection;

    public AddChild() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        setTitle("Child Information Form");
        setSize(450, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        panel.add(ageLabel);
        panel.add(ageField);

        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        dobField = new JTextField();
        panel.add(dobLabel);
        panel.add(dobField);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel sexLabel = new JLabel("Sex:");
        String[] sexOptions = {"Male", "Female", "Other"};
        sexComboBox = new JComboBox<>(sexOptions);
        panel.add(sexLabel);
        panel.add(sexComboBox);

        JLabel geneticDisorderLabel = new JLabel("Genetic Disorder:");
        geneticDisorderField = new JTextField();
        panel.add(geneticDisorderLabel);
        panel.add(geneticDisorderField);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(new Color(4, 170, 109));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitChildInfo();
            }
        });
        panel.add(submitButton);

        add(panel);
    }

    private void connectToDatabase() {
        try {
            // Replace below with your MySQL connection details
            String url = "jdbc:mysql://localhost:3306/kulaabhooshanam_java";
            String user = "root";
            String password = "Harry@123";

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void submitChildInfo() {
        try {
            String name = nameField.getText();
            String dob = dobField.getText();
            String sex = (String) sexComboBox.getSelectedItem();
            String geneticDisorder = geneticDisorderField.getText();
            if (geneticDisorder == null || geneticDisorder.trim().isEmpty()) {
                geneticDisorder = "None";
            }            
            String adoptionStatus = "inhouse";
            Timestamp dateAdmitted = new Timestamp(System.currentTimeMillis());
            int agencyId = 100;
            int age = Integer.parseInt(ageField.getText());
    
            // Check if the child already exists
            if (childExists(name, dob)) {
                JOptionPane.showMessageDialog(this, "Child already exists.");
                return; // Exit the method
            }
    
            // Prepare the SQL INSERT statement
            String sql = "INSERT INTO children (c_name, dob, sex, date_admitted, adoption_status, genetic_disorder, agency_id, age) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
    
            // Set parameter values for the INSERT statement
            statement.setString(1, name);
            statement.setString(2, dob);
            statement.setString(3, sex);
            statement.setTimestamp(4, dateAdmitted);
            statement.setString(5, adoptionStatus);
            statement.setString(6, geneticDisorder);
            statement.setInt(7, agencyId);
            statement.setInt(8, age);
    
            // Execute the INSERT statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Child information added successfully!");
                // Clear input fields after successful insertion
                ageField.setText("");
                nameField.setText("");
                dobField.setText("");
                geneticDisorderField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding child information.");
        }
    }
    
    private boolean childExists(String name, String dob) throws SQLException {
        // Prepare the SQL SELECT statement to check if the child exists
        String sql = "SELECT * FROM children WHERE c_name = ? AND dob = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, dob);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next(); // Returns true if a record exists
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddChild().setVisible(true);
            }
        });
    }
}
