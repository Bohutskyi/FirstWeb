package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 200, WIDTH = 300;
    private JButton buttonOk = new JButton("Ok"),
            buttonCancel = new JButton("Cancel");
    private JTextField groupName, curator, specialty;
//    private JSpinner curator;

    CreateGroupDialog() {
        setTitle(MessageResource.getMessageResource().getString("id20"));
        setResizable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel(MessageResource.getMessageResource().getString("id21"));
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

        label = new JLabel(MessageResource.getMessageResource().getString("id22"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        curator = new JTextField();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(curator, constraints);
        getContentPane().add(curator);

        label = new JLabel(MessageResource.getMessageResource().getString("id23"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        specialty = new JTextField();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(specialty, constraints);
        getContentPane().add(specialty);

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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) dimension.getWidth() - WIDTH) / 2, ((int) dimension.getHeight() - HEIGHT) / 2,
                WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getName().equals("Ok")) {
//            ManagementSystem.getInstance().
        } else if (source.getName().equals("Cancel")) {

        }
        setVisible(false);
    }
}
