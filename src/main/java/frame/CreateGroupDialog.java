package frame;

import structures.ManagementSystem;
import structures.Speciality;
import structures.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CreateGroupDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 200, WIDTH = 500;
    private JButton buttonOk = new JButton(MessageResource.getString("id49")),
            buttonCancel = new JButton(MessageResource.getString("id50"));
    private JTextField groupName;
    private JLabel message;
    private JComboBox curators, specialities;

    CreateGroupDialog() throws Exception {
        setTitle(MessageResource.getString("id20"));
        setResizable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel(MessageResource.getString("id21"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        groupName = new JTextField();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(groupName, constraints);
        getContentPane().add(groupName);

        label = new JLabel(MessageResource.getString("id22"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        curators = new JComboBox(new Vector(ManagementSystem.getInstance().getTeachers()));
        curators.setPreferredSize(new Dimension(300, curators.getHeight()));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(curators, constraints);
        getContentPane().add(curators);

        label = new JLabel(MessageResource.getString("id23"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        specialities = new JComboBox(new Vector(ManagementSystem.getInstance().getSpecialities()));
        specialities.setPreferredSize(new Dimension(150, specialities.getHeight()));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(specialities, constraints);
        getContentPane().add(specialities);

        message = new JLabel();
        message.setFont(new Font("Arial", Font.BOLD, 14));
        message.setForeground(Color.RED);
        message.setText("Some text need to add id");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        gridBagLayout.setConstraints(message, constraints);
        getContentPane().add(message);

        groupName.getDocument().addDocumentListener(new TextFieldDocumentListener(groupName, message, buttonOk));

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        buttonOk.setName("Ok");
        buttonOk.addActionListener(this);
        gridBagLayout.setConstraints(buttonOk, constraints);
        getContentPane().add(buttonOk);

        buttonCancel.setName("Cancel");
        buttonCancel.addActionListener(this);
        gridBagLayout.setConstraints(buttonCancel, constraints);
        getContentPane().add(buttonCancel);

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) dimension.getWidth() - WIDTH) / 2, ((int) dimension.getHeight() - HEIGHT) / 2,
                WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getName().equals("Ok")) {
            if (JOptionPane.showConfirmDialog(this, MessageResource.getString("id51") + groupName.getText() + "?\n" + MessageResource.getString("id52"),
                    MessageResource.getString("id20"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ManagementSystem.getInstance().createGroup(groupName.getText(),
                            (((Teacher) curators.getSelectedItem()).getTeacherID()),
                            ((Speciality) specialities.getSelectedItem()).getSpecialityID());
                    setVisible(false);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } else if (source.getName().equals("Cancel")) {
            setVisible(false);
        }
    }

}
