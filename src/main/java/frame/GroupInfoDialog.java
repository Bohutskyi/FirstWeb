package frame;

import structures.Group;
import structures.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupInfoDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 200, WIDTH = 500;

    GroupInfoDialog(Group group) {
        this.setTitle(MessageResource.getString("id53"));
        this.setResizable(false);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        add(Box.createVerticalGlue());

        JLabel label = new JLabel(MessageResource.getString("id21") + group.getNameGroup());
        this.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());

        label = new JLabel(MessageResource.getString("id23") + group.getSpeciality());
        add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

        try {
            label = new JLabel(MessageResource.getString("id22") + ManagementSystem.getInstance().getFullTeacherName(group.getCuratorID()));
            add(label);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        add(Box.createVerticalGlue());

        JButton button = new JButton(MessageResource.getString("id49"));
        button.setName("Ok");
        button.addActionListener(this);
        add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) dimension.getWidth() - WIDTH) / 2, ((int) dimension.getHeight() - HEIGHT) / 2,
                WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getName().equals("Ok")) {
            setVisible(false);
        }
    }
}
