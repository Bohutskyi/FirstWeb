package frame;

import structures.Group;
import structures.ManagementSystem;
import structures.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Month;
import java.util.*;
import java.util.List;

public class StudentDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 200, WIDTH = 550;
    private static final int L_X = 10, L_W = 100, C_W = 150;
    private StudentsFrame owner;
    private boolean result, newStudent;
    private int studentId = 0;
    private JTextField firstName = new JTextField(),
            surName = new JTextField(),
            lastName = new JTextField();
    private ButtonGroup sex = new ButtonGroup();
    private JSpinner year = new JSpinner(new SpinnerNumberModel(2006, 1996, 2020, 1));
    private JComboBox groupList;
    private JComboBox years, months, days;
    private JLabel message; //WIP

    public StudentDialog(List<Group> groups, boolean newStudent, StudentsFrame owner) {
        this.owner = owner;
        this.setResizable(false);

        this.newStudent = newStudent;
        if (newStudent) {
            setTitle(MessageResource.getString("id27"));
        } else {
            setTitle(MessageResource.getString("id28"));
        }
        getContentPane().setLayout(new FlowLayout());

        groupList = new JComboBox(new Vector(groups));

        JRadioButton m = new JRadioButton(MessageResource.getString("id29"));
        JRadioButton w = new JRadioButton(MessageResource.getString("id31"));
        m.setActionCommand(MessageResource.getString("id32"));
        w.setActionCommand(MessageResource.getString("id33"));
        sex.add(m);
        sex.add(w);

        getContentPane().setLayout(null);

        JLabel label = new JLabel(MessageResource.getString("id34"), JLabel.RIGHT);
        label.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(label);
        surName.setBounds(L_X + L_W + 10 + 62, 10, C_W, 20);
        getContentPane().add(surName);

        label = new JLabel(MessageResource.getString("id35"), JLabel.RIGHT);
        label.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(label);
        firstName.setBounds(L_X + L_W + 10 + 62, 30, C_W, 20);
        getContentPane().add(firstName);

        label = new JLabel(MessageResource.getString("id36"), JLabel.RIGHT);
        label.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(label);
        lastName.setBounds(L_X + L_W + 10 + 62, 50, C_W, 20);
        getContentPane().add(lastName);

        label = new JLabel(MessageResource.getString("id37"), JLabel.RIGHT);
        label.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(label);
        m.setBounds(L_X + L_W + 10 + 65, 70, C_W / 2, 20);
        getContentPane().add(m);
        w.setBounds(L_X + L_W + 10 + 65 + C_W / 2, 70, C_W / 2, 20);
        w.setSelected(true);
        getContentPane().add(w);

        label = new JLabel(MessageResource.getString("id38"), JLabel.RIGHT);
        label.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(label);
        years = new JComboBox<>();
        for (int i = 1980; i < 2000; ++i) {
            years.addItem(i);
        }
        years.setSelectedItem(2017);
        years.setBounds(L_X + L_W + 10, 90, C_W - 65, 25);
        getContentPane().add(years);

        months = new JComboBox<>();
        for (int i = 1; i <= 12; ++i) {
            months.addItem(Month.of(i));
        }
        months.setBounds(L_X + L_W + 100, 90, C_W - 40, 25);
        getContentPane().add(months);

        days = new JComboBox<>();
        Calendar temp = new GregorianCalendar(years.getSelectedIndex(), months.getSelectedIndex(), 1);
        for (int i = 1; i <= temp.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
            days.addItem(i);
        }
        days.setBounds(L_X + L_W + 10 + 205, 90, 70, 25);
        getContentPane().add(days);

        months.addActionListener((e) -> {
            days.removeAllItems();
            Calendar tempCalendar = new GregorianCalendar(years.getSelectedIndex(), months.getSelectedIndex(), 1);
            for (int i = 1; i <= tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
                days.addItem(i);
            }
        });

        years.addActionListener((e) -> {
            if (months.getSelectedIndex() == 1) {
                days.removeAllItems();
                Calendar tempCalendar = new GregorianCalendar(years.getSelectedIndex(), months.getSelectedIndex(), 1);
                for (int i = 1; i <= tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
                    days.addItem(i);
                }
            }
        });

        label = new JLabel(MessageResource.getString("id40"), JLabel.RIGHT);
        label.setBounds(L_X, 115, L_W, 25);
        getContentPane().add(label);
        groupList.setBounds(L_X + L_W + 10, 115, C_W + 125, 25);
        getContentPane().add(groupList);

        label = new JLabel(MessageResource.getString("id41"), JLabel.RIGHT);
        label.setBounds(L_X, 145, L_W, 20);
        getContentPane().add(label);
        year.setBounds(L_X + L_W + 10 + 62, 145, C_W, 20);
        getContentPane().add(year);

        JButton buttonOk = new JButton(MessageResource.getString("id49"));
        if (newStudent) {
            buttonOk.setText(MessageResource.getString("id64"));
        }
        buttonOk.setName("Ok");
        buttonOk.addActionListener(this);
        buttonOk.setBounds(L_X + L_W + C_W + 10 + 150, 10, 100, 25);
        getContentPane().add(buttonOk);

        if (!newStudent) {
            JButton buttonCancel = new JButton(MessageResource.getString("id50"));
            buttonCancel.setName("Cancel");
            buttonCancel.addActionListener(this);
            buttonCancel.setBounds(L_X + L_W + C_W + 10 + 150, 40, 100, 25);
            getContentPane().add(buttonCancel);
        }

        if (newStudent) {
            JButton buttonAddNew = new JButton(MessageResource.getString("id63"));
            buttonAddNew.setName("Add new");
            buttonAddNew.addActionListener(this);
            buttonAddNew.setBounds(L_X + L_W + C_W + 10 + 150, 40, 100, 25);
            getContentPane().add(buttonAddNew);
        }

        firstName.getDocument().addDocumentListener(new TextFieldDocumentListener(firstName, null, null));
        surName.getDocument().addDocumentListener(new TextFieldDocumentListener(surName, null, null));
        lastName.getDocument().addDocumentListener(new TextFieldDocumentListener(lastName, null, null));

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - StudentDialog.WIDTH) / 2, ((int) d.getHeight() - StudentDialog.HEIGHT) / 2,
                StudentDialog.WIDTH, StudentDialog.HEIGHT);
    }

    void setStudent(Student student) {
        studentId = student.getStudentId();
        firstName.setText(student.getFirstName());
        surName.setText(student.getSurName());
        lastName.setText(student.getLastName());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(student.getDateOfBirth());
        years.setSelectedItem(calendar.get(Calendar.YEAR));
        months.setSelectedItem(calendar.get(Calendar.MONTH));
        days.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));

        for (Enumeration e = sex.getElements(); e.hasMoreElements(); ) {
            AbstractButton abstractButton = (AbstractButton) e.nextElement();
            abstractButton.setSelected(abstractButton.getActionCommand().equals("" + student.getSex()));
        }
        year.getModel().setValue(student.getEducationYear());
        for (int i = 0; i < groupList.getModel().getSize(); ++i) {
            Group group = (Group) groupList.getModel().getElementAt(i);
            if (group.getGroupId() == student.getGroupId()) {
                groupList.setSelectedIndex(i);
                break;
            }
        }
    }

    private Student getStudent() {
        if (!canGetStudent()) {
            return null;
        }
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName.getText());
        student.setSurName(surName.getText());
        student.setLastName(lastName.getText());

        Calendar temp = new GregorianCalendar((int) years.getSelectedItem() , months.getSelectedIndex(), (int) days.getSelectedItem() + 1);
        student.setDateOfBirth(temp.getTime());

        for (Enumeration e = sex.getElements(); e.hasMoreElements();) {
            AbstractButton ab = (AbstractButton) e.nextElement();
            if (ab.isSelected()) {
                student.setSex(ab.getActionCommand().charAt(0));
            }
        }
        int y = ((SpinnerNumberModel) year.getModel()).getNumber().intValue();
        student.setEducationYear(y);
        student.setGroupId(((Group) groupList.getSelectedItem()).getGroupId());
        return student;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getName().equals("Add new")) {
            try {
                Student newStudent = getStudent();
                if (newStudent == null) {
                    return;
                }
                ManagementSystem.getInstance().insertStudent(newStudent);
                owner.reloadStudents();
                firstName.setText("");
                surName.setText("");
                lastName.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            result = true;
            return;
        }
        if (source.getName().equals("Ok")) {
            if (!newStudent) {
                try {
                    Student student = getStudent();
                    if (student == null) {
                        return;
                    }
                    ManagementSystem.getInstance().updateStudent(student);
                    owner.reloadStudents();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
            result = true;
        }
        if (source.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }

    private boolean canGetStudent() {
        if (firstName.getText().equals("")) {
            return false;
        } else if (lastName.getText().equals("")) {
            return false;
        } else if (surName.getText().equals("")) {
            return false;
        }
        return true;
    }

}
