package frame;

import structures.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.List;

public class MoveGroupDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 150, WIDTH = 400;
    private JSpinner spYear;
    private JComboBox groupList;
//    private JButton buttonOk = new JButton("Ok");
//    private JButton buttonCancel = new JButton("Cancel");
    private boolean result = false;

    MoveGroupDialog(int year, List groups) {
        setTitle(MessageResource.getMessageResource().getString("id24"));
        this.setResizable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5,5, 5);

        JLabel label = new JLabel(MessageResource.getMessageResource().getString("id25"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        groupList = new JComboBox(new Vector(groups));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(groupList, constraints);
        getContentPane().add(groupList);

        label = new JLabel(MessageResource.getMessageResource().getString("id26"));
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        spYear = new JSpinner(new SpinnerNumberModel(year + 1, 1900, 2100, 1));
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(spYear, constraints);
        getContentPane().add(spYear);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        JButton buttonOk = new JButton("Ok");
        buttonOk.setName("OK");
        buttonOk.addActionListener(this);
        gridBagLayout.setConstraints(buttonOk, constraints);
        getContentPane().add(buttonOk);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setName("Cancel");
        buttonCancel.addActionListener(this);
        gridBagLayout.setConstraints(buttonCancel, constraints);
        getContentPane().add(buttonCancel);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - MoveGroupDialog.WIDTH) / 2, ((int) d.getHeight() - MoveGroupDialog.HEIGHT) / 2,
                MoveGroupDialog.WIDTH, MoveGroupDialog.HEIGHT);
    }

    int getYear() {
        return ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
    }

    Group getGroup() {
        if (groupList.getModel().getSize() > 0) {
            return (Group) groupList.getSelectedItem();
        }
        return null;
    }

    boolean getResult() {
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }

}
