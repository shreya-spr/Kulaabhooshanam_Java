package Role;

import javax.swing.*;
import java.awt.*;

public class Role extends JDialog {
    private JComboBox<String> roleComboBox;
    private JPanel rolePanel = new JPanel();
    private RoleChangeListener roleChangeListener;  // Observer pattern apparently

    public Role(RoleChangeListener listener) {
        setTitle("Choose role here");
        setContentPane(rolePanel);
        setMinimumSize(new Dimension(450, 430));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.roleChangeListener = listener; // Set the listener

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        // dropdown for role
        JLabel roleLabel = new JLabel("Choose Role:");
        String[] roleOptions = {"Agency", "Parent"};
        roleComboBox = new JComboBox<>(roleOptions);
        panel.add(roleLabel);
        panel.add(roleComboBox);

        add(panel);

        chooseRole();
    }

    private void chooseRole() {
        roleComboBox.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            if (roleChangeListener != null) {
                roleChangeListener.onRoleChanged(role); // Notify listener about role change
            }
        });
    }

    // Interface for role change listener
    public interface RoleChangeListener {
        void onRoleChanged(String role);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Role(null).setVisible(true);
            }
        });
    }
}
