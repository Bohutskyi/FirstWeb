package test;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame implements ListSelectionListener, ActionListener {

    private JList list;

    public Test() {
        list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultListModel dlm = new DefaultListModel();
        list.setModel(dlm);

        list.addListSelectionListener(this);

        JButton add = new JButton("Add");
        JButton del = new JButton("Del");

        add.addActionListener(this);
        del.addActionListener(this);

        add.setName("add");
        del.setName("del");

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        p.add(add);
        p.add(del);

        getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);

        getContentPane().add(p, BorderLayout.SOUTH);

        setBounds(100, 100, 200, 200);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test test = new Test();
            test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            test.setVisible(true);
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting())
            System.out.println("Index: " + list.getSelectedIndex());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel dlm = (DefaultListModel) list.getModel();

        JButton sender = (JButton) e.getSource();

        if (sender.getName().equals("add")) {
            dlm.addElement(String.valueOf(dlm.getSize()));
        }
        if (sender.getName().equals("del") && list.getSelectedIndex() >= 0) {
            dlm.remove(list.getSelectedIndex());
        }
    }
}
