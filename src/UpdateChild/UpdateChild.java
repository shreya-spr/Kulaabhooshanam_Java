package UpdateChild;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateChild extends JDialog {
    private JTextField ageField, dobField, nameField, geneticDisorderField, idField, agencyIDField;
    private JComboBox<String> sexComboBox, AdoptionComboBox;
    private JButton submitButton;
    private Connection connection;

    public UpdateChild() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        setTitle("Child Information Form");
        setSize(450, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 1, 5, 5));

        JLabel idLabel = new JLabel("Child ID:");
        idField = new JTextField();
        panel.add(idLabel);
        panel.add(idField);

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

        JLabel agencyIDLabel = new JLabel("Agency ID");
        agencyIDField = new JTextField();
        panel.add(agencyIDLabel);
        panel.add(agencyIDField);

        JLabel adoptionLabel = new JLabel("Sex:");
        String[] AdoptionStatus = {"Inhouse", "Adopted"};
        AdoptionComboBox = new JComboBox<>(AdoptionStatus);
        panel.add(adoptionLabel);
        panel.add(AdoptionComboBox);

        submitButton = new JButton("Submit");
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
            String url = "jdbc:mysql://localhost:3306/kulaabhooshanam";
            String user = "root";
            String password = "w1o2rk";

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
            String adoptionStatus = (String) AdoptionComboBox.getSelectedItem();
            Timestamp dateAdmitted = new Timestamp(System.currentTimeMillis());
            int agencyId = Integer.parseInt(agencyIDField.getText());
            int age = Integer.parseInt(ageField.getText());
            int id = Integer.parseInt(idField.getText());
    
            // Check if the child already exists
            if (!childExists(id)) {
                JOptionPane.showMessageDialog(this, "Child does not exist.");
                return; // Exit the method
            }
    
            // Prepare the SQL INSERT statement
            String sql = "UPDATE children SET c_name = ?, dob = ?, sex = ?, date_admitted = ?, adoption_status = ?, genetic_disorder = ?, agency_id = ?, age = ? WHERE child_id = ?";
            ;
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
            statement.setInt(9, id);
    
            // Execute the INSERT statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Child information added successfully!");
                // Clear input fields after successful insertion
                nameField.setText("");
                dobField.setText("");
                geneticDisorderField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding child information.");
        }
    }
    
    private boolean childExists(int id) throws SQLException {
        // Prepare the SQL SELECT statement to check if the child exists
        String sql = "SELECT * FROM children WHERE c_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next(); // Returns true if a record exists
    }
}