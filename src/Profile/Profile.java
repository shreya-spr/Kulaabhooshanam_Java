package Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Profile extends JDialog {
    private JTextField tfAadhar = new JTextField();
    private JTextArea userDetailsArea = new JTextArea(10, 40);
    private JTextArea childDetailsArea = new JTextArea(10, 40);

    public Profile(JFrame parent) {
        setTitle("View your details here");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel aadharLabel = new JLabel("Aadhar Number:");
        JButton viewDetailsButton = new JButton("View Details");

        inputPanel.add(aadharLabel);
        inputPanel.add(tfAadhar);
        inputPanel.add(viewDetailsButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        JScrollPane userDetailsScrollPane = new JScrollPane(userDetailsArea);
        JScrollPane childDetailsScrollPane = new JScrollPane(childDetailsArea);

        userDetailsArea.setEditable(false);
        childDetailsArea.setEditable(false);

        detailsPanel.add(userDetailsScrollPane);
        detailsPanel.add(childDetailsScrollPane);

        add(detailsPanel, BorderLayout.CENTER);

        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aadharNumber = tfAadhar.getText().trim();

                if (!aadharNumber.isEmpty()) {
                    try {
                        // Establish database connection
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kulaabhooshanam_java", "root", "Harry@123");

                        // Retrieve user details based on Aadhar number
                        // Maybe get details from what they applied as well ...
                        String userDetailsQuery = "SELECT * FROM parents p " +
                                                    "JOIN application a ON p.p_id = a.p_id " +
                                                    "WHERE p.p_id = ?";
                        PreparedStatement userDetailsStatement = conn.prepareStatement(userDetailsQuery);
                        userDetailsStatement.setString(1, aadharNumber);
                        ResultSet userDetailsResult = userDetailsStatement.executeQuery();

                        if (userDetailsResult.next()) {
                            String userDetails = "Name: " + userDetailsResult.getString("p_name") + "\n"
                                                + "Email: " + userDetailsResult.getString("email_id") + "\n"
                                                + "Phone: " + userDetailsResult.getString("phno") + "\n"
                                                + "Address: " + userDetailsResult.getString("address") + "\n"
                                                + "Child's age: " + userDetailsResult.getInt("child_age") + "\n"
                                                + "Child genetic disorder: " + userDetailsResult.getString("g_disorder");
                            userDetailsArea.setText(userDetails);

                            // Retrieve child details mapped to the user using stored procedure
                            CallableStatement childDetailsStatement = conn.prepareCall("CALL MapChildToParent(?, ?, ?, ?)");
                            childDetailsStatement.setString(1, userDetailsResult.getString("sex"));
                            childDetailsStatement.setInt(2, userDetailsResult.getInt("age"));
                            childDetailsStatement.setString(3, userDetailsResult.getString("g_disorder"));
                            childDetailsStatement.setString(4, aadharNumber);
                            ResultSet childDetailsResult = childDetailsStatement.executeQuery();

                            if (childDetailsResult.next()) {
                                String childDetails = "Child Name: " + childDetailsResult.getString("c_name") + "\n"
                                                    + "Child DOB: " + childDetailsResult.getDate("dob") + "\n"
                                                    + "Child Gender: " + childDetailsResult.getString("sex");
                                childDetailsArea.setText(childDetails);
                            } else {
                                childDetailsArea.setText("No child mapped to the user.");
                            }
                        } else {
                            userDetailsArea.setText("No user found with provided Aadhar number.");
                            childDetailsArea.setText("");
                        }

                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(Profile.this, "Please enter Aadhar number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Profile(new JFrame());
            }
        });
    }
}
