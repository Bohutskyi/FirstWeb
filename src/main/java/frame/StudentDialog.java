package frame;

import structures.Group;
import structures.ManagementSystem;
import structures.Student;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.Month;
import java.util.*;
import java.util.List;

public class StudentDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 200, WIDTH = 550;
    private static final int L_X = 10, L_W = 100, C_W = 150;
    private StudentsFrame owner;
    private boolean result;
    private int studentId = 0;
    private JTextField firstName = new JTextField(),
            surName = new JTextField(),
            lastName = new JTextField();
    private JSpinner dateOfBirth = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private ButtonGroup sex = new ButtonGroup();
    private JSpinner year = new JSpinner(new SpinnerNumberModel(2006, 1996, 2020, 1));
    private JComboBox groupList;
    private JComboBox years, months, days;

    public StudentDialog(List<Group> groups, boolean newStudent, StudentsFrame owner) {
        // После вставки студента без закрытия окна нам потребуется перегрузка списка
        // А для этого нам надо иметь доступ к этому методу в главной форме
        this.owner = owner;
        this.setResizable(false);

        if (newStudent) {
            setTitle("Додавання нових студентів");
        } else {
            setTitle("Редагування даних про студента");
        }
        getContentPane().setLayout(new FlowLayout());

        groupList = new JComboBox(new Vector<Group>(groups));

        JRadioButton m = new JRadioButton("Чол");
        JRadioButton w = new JRadioButton("Жін");
        m.setActionCommand("Ч");
        w.setActionCommand("Ж");
        sex.add(m);
        sex.add(w);

        getContentPane().setLayout(null);

        // Прізвище
        JLabel l = new JLabel("Прізвище:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        surName.setBounds(L_X + L_W + 10 + 62, 10, C_W, 20);
        getContentPane().add(surName);

        // Ім'я
        l = new JLabel("Ім'я:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        firstName.setBounds(L_X + L_W + 10 + 62, 30, C_W, 20);
        getContentPane().add(firstName);

        // По батькові
        l = new JLabel("По батькові:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        lastName.setBounds(L_X + L_W + 10 + 62, 50, C_W, 20);
        getContentPane().add(lastName);

        // Стать
        l = new JLabel("Стать:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        m.setBounds(L_X + L_W + 10 + 65, 70, C_W / 2, 20);
        getContentPane().add(m);
        // Сделаем по умолчанию женщину - из уважения
        w.setBounds(L_X + L_W + 10 + 65 + C_W / 2, 70, C_W / 2, 20);
        w.setSelected(true);
        getContentPane().add(w);


        // Дата народження
        l = new JLabel("Дата народження:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        years = new JComboBox<>();
        for (int i = 1996; i < 2021; ++i) {
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

        months.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                days.removeAllItems();
                Calendar temp = new GregorianCalendar(years.getSelectedIndex(), months.getSelectedIndex(), 1);
                for (int i = 1; i <= temp.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
                    days.addItem(i);
                }
            }
        });

        years.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (months.getSelectedIndex() == 1) {
                    days.removeAllItems();
                    Calendar temp = new GregorianCalendar(years.getSelectedIndex(), months.getSelectedIndex(), 1);
                    for (int i = 1; i <= temp.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
                        days.addItem(i);
                    }
                }
            }
        });


        // Група
        l = new JLabel("Група:", JLabel.RIGHT);
        l.setBounds(L_X, 115, L_W, 25);
        getContentPane().add(l);
        groupList.setBounds(L_X + L_W + 10, 115, C_W + 125, 25);
        getContentPane().add(groupList);

        // Рік навчання
        l = new JLabel("Рік навчання:", JLabel.RIGHT);
        l.setBounds(L_X, 145, L_W, 20);
        getContentPane().add(l);
        year.setBounds(L_X + L_W + 10 + 62, 145, C_W, 20);
        getContentPane().add(year);

        JButton buttonOk = new JButton("Ok");
        buttonOk.setName("Ok");
        buttonOk.addActionListener(this);
        buttonOk.setBounds(L_X + L_W + C_W + 10 + 150, 10, 100, 25);
        getContentPane().add(buttonOk);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setName("Cancel");
        buttonCancel.addActionListener(this);
        buttonCancel.setBounds(L_X + L_W + C_W + 10 + 150, 40, 100, 25);
        getContentPane().add(buttonCancel);

        if (newStudent) {
            JButton buttonAddNew = new JButton("Add new");
            buttonAddNew.setName("Add new");
            buttonAddNew.addActionListener(this);
            buttonAddNew.setBounds(L_X + L_W + C_W + 10 + 150, 70, 100, 25);
            getContentPane().add(buttonAddNew);
        }

        firstName.getDocument().addDocumentListener(new TextFieldDocumentListener(firstName));
        surName.getDocument().addDocumentListener(new TextFieldDocumentListener(surName));
        lastName.getDocument().addDocumentListener(new TextFieldDocumentListener(lastName));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - StudentDialog.WIDTH) / 2, ((int) d.getHeight() - StudentDialog.HEIGHT) / 2,
                StudentDialog.WIDTH, StudentDialog.HEIGHT);
    }

    public void setStudent(Student student) {
        studentId = student.getStudentId();
        firstName.setText(student.getFirstName());
        surName.setText(student.getSurName());
        lastName.setText(student.getLastName());
        dateOfBirth.getModel().setValue(student.getDateOfBirth());
        for (Enumeration e = sex.getElements(); e.hasMoreElements(); ) {
            AbstractButton abstractButton = (AbstractButton) e.nextElement();
            abstractButton.setSelected(abstractButton.getActionCommand().equals(new String("" + student.getSex())));
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

    public Student getStudent() {
        if (!canGetStudent()) {
            return null;
        }
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName.getText());
        student.setSurName(surName.getText());
        student.setLastName(lastName.getText());

        Calendar temp = new GregorianCalendar((int) years.getSelectedItem() , months.getSelectedIndex(), (int) days.getSelectedItem());
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
        // Добавляем студента, но не закрываем окно
        // Здесь мы не будем вызывать в отдельном потоке сохранение.
        // Оно не занимаем много времени и лишние действия здесь не оправданы
        if (source.getName().equals("Add new")) {
            try {
//                ManagementSystem.getInstance().insertStudent(getStudent());
                Student newStudent = getStudent();
                if (newStudent == null) {
                    return;
                }
                ManagementSystem.getInstance().insertStudent(newStudent);
                owner.reloadStudents();
                firstName.setText("");
                surName.setText("");
                lastName.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            result = true;
            return;
        }
        if (source.getName().equals("Ok")) {
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

    private class TextFieldDocumentListener implements DocumentListener {
        private JTextField textField;
        private Border temp;

        public TextFieldDocumentListener(JTextField textField) {
            this.textField = textField;
            temp = textField.getBorder();
            if (textField.getText().equals("")) {
                textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            textField.setBorder(temp);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (textField.getText().equals("")) {
                textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }
}
