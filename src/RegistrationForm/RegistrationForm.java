package RegistrationForm;

import javax.swing.*;

import LoginForm.LoginForm;
import User.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class RegistrationForm  extends JFrame {
    private JTextField tfAadhar = new JTextField();
    private JTextField tfName = new JTextField();
    private JTextField tfEmail = new JTextField();
    private JTextField tfAdoptedKids = new JTextField();
    private JTextField tfBioKids = new JTextField();
    private JTextField tfPhno = new JTextField();
    private JTextField tfSex = new JTextField();
    private JTextField tfIncome = new JTextField();
    private JTextField tfBank = new JTextField();
    private JTextField tfMaritalstatus = new JTextField();
    private JTextField tfAge = new JTextField();
    private JTextField tfSpouseName = new JTextField();
    private JTextField tfSpouseAge = new JTextField();
    private JTextField tfAddress = new JTextField();
    private JTextField tfFinStatus = new JTextField();
    private JTextField tfSpouseAadhar = new JTextField();
    private JTextField tfCaste = new JTextField();
    private JButton btnRegister = new JButton("Register");
    private JButton btnCancel = new JButton("Cancel");
    private JPasswordField pfPassword = new JPasswordField();
    private JPanel panel = new JPanel();

    public RegistrationForm(JFrame parent) {
        // super(parent);
        setTitle("Create a new account here");
        setContentPane(panel);
        setMinimumSize(new Dimension(450, 600));
        // setModal(true);
        setLocationRelativeTo(parent);  // centers it to the middle of parent
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel.setLayout(new GridLayout(18, 1, 5, 5));

        JLabel aadharLabel = new JLabel("Aadhar Number");
        panel.add(aadharLabel);
        panel.add(tfAadhar);
 
        JLabel passwordLabel = new JLabel("Password");
        panel.add(passwordLabel);
        panel.add(pfPassword);

        JLabel nameLabel = new JLabel("Enter your name");
        panel.add(nameLabel);
        panel.add(tfName);

        JLabel emailLabel = new JLabel("Enter your email");
        panel.add(emailLabel);
        panel.add(tfEmail);

        JLabel adoptedLabel = new JLabel("Number of adopted children");
        panel.add(adoptedLabel);
        panel.add(tfAdoptedKids);

        JLabel biokidsLabel = new JLabel("Number of biological children");
        panel.add(biokidsLabel);
        panel.add(tfBioKids);

        JLabel phnoLabel = new JLabel("Phone number");
        panel.add(phnoLabel);
        panel.add(tfPhno);

        JLabel bankLabel = new JLabel("Enter your bank details");
        panel.add(bankLabel);
        panel.add(tfBank);

        JLabel sexLabel = new JLabel("Parent sex");
        panel.add(sexLabel);
        panel.add(tfSex);

        JLabel incomeLabel = new JLabel("Income");
        panel.add(incomeLabel);
        panel.add(tfIncome);

        JLabel maritalstatusLabel = new JLabel("Marital status");
        panel.add(maritalstatusLabel);
        panel.add(tfMaritalstatus);

        JLabel spnameLabel = new JLabel("Spouse name");
        panel.add(spnameLabel);
        panel.add(tfSpouseName);

        JLabel spageLabel = new JLabel("Spouse age");
        panel.add(spageLabel);
        panel.add(tfSpouseAge);

        JLabel spaadLabel = new JLabel("Spouse Aadhar number");
        panel.add(spaadLabel);
        panel.add(tfSpouseAadhar);

        JLabel addressLabel = new JLabel("Address");
        panel.add(addressLabel);
        panel.add(tfAddress);

        JLabel incomestatLabel = new JLabel("Income status");
        panel.add(incomestatLabel);
        panel.add(tfFinStatus);

        JLabel casteLabel = new JLabel("caste");
        panel.add(casteLabel);
        panel.add(tfCaste);

        // when you click on the Register button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);

        panel.add(btnRegister);
        panel.add(btnCancel);
    }



    private void registerUser() {
        String name = tfName.getText();
        String aadhar = tfAadhar.getText();
        String email = tfEmail.getText();
        int numAdoptedKids = parseInteger(tfAdoptedKids.getText());  // int
        int numBioKids = parseInteger(tfBioKids.getText());  // int
        String phno = tfPhno.getText();
        String sex = tfSex.getText();
        int income = parseInteger(tfIncome.getText());   // int
        String bankInfo = tfBank.getText();
        String maritalStatus = tfMaritalstatus.getText();
        String financialStatus = tfFinStatus.getText();
        int age = parseInteger(tfAge.getText());   // int
        String spouseName = tfSpouseName.getText();
        int spouseAge = parseInteger(tfSpouseAge.getText());   // int
        String spouseAadhar = tfSpouseAadhar.getText();
        String address = tfAddress.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String caste = tfCaste.getText();

        if (name.isEmpty() || email.isEmpty() || aadhar.isEmpty() ||
                phno.isEmpty() || address.isEmpty() || sex.isEmpty() || password.isEmpty() ||
                maritalStatus.isEmpty() || bankInfo.isEmpty() || financialStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(aadhar, name, email, phno, address, password, numAdoptedKids, numBioKids, income, age, spouseName,
                spouseAge, spouseAadhar, sex, bankInfo, maritalStatus, financialStatus, caste);

        // already in the database
        if (user != null) {
            dispose();
            new LoginForm(null, null);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again.",
                    JOptionPane.ERROR_MESSAGE);
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
    // global
    public User user;



    private User addUserToDatabase(String aadhar, String name, String email, String phno, String address, String password, int numAdoptedKids, int numBioKids, int income, int age, String spouseName, int spouseAge, String spouseAadhar, String sex, String bankInfo, String maritalStatus, String financialStatus, String caste) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/kulaabhooshanam_java";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // connection done !

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO parents(p_id, p_name, email_id, pswd, n_bio_kids, n_adopted_kids, appln_status, phno, sex, annual_income, bank_details, marital_status, age, spouse_age, spouse_name, address, financial_status, caste, spouse_aadhar)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, aadhar);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, numBioKids);
            preparedStatement.setInt(6, numAdoptedKids);
            preparedStatement.setString(7, "null");
            preparedStatement.setString(8, phno);
            preparedStatement.setString(9, sex);
            preparedStatement.setInt(10, income);
            preparedStatement.setString(11, bankInfo);
            preparedStatement.setString(12, maritalStatus);
            preparedStatement.setInt(13, age);
            preparedStatement.setInt(14, spouseAge);
            preparedStatement.setString(15, spouseName);
            preparedStatement.setString(16, address);
            preparedStatement.setString(17, financialStatus);
            preparedStatement.setString(18, caste);
            preparedStatement.setString(19, spouseAadhar);





            // Insert row into table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.aadhar = aadhar;
                user.email = email;
                user.password = password;
                user.numBioKids = numBioKids;
                user.numAdoptedKids = numAdoptedKids;
                user.applicationStatus = "null";
                user.phno = phno;
                user.sex = sex;
                user.income = income;
                user.bankInfo = bankInfo;
                user.maritalStatus = maritalStatus;
                user.age = age;
                user.spouseAge = spouseAge;
                user.spouseName = spouseName;
                user.address = address;
                user.financialStatus = financialStatus;
                user.caste = caste;
                user.spouseAadhar = spouseAadhar;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
//     public static void main(String[] args) {
//         RegistrationForm myForm = new RegistrationForm(null);
//         User user = myForm.user;
//         if (user != null) {
//             System.out.println("Successfully registered! " + user.name);
//         } else {
//             System.out.println("Registration not done !");
//         }
//     }
// }