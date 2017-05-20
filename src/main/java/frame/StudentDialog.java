package frame;

import structures.Group;
import structures.ManagementSystem;
import structures.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

public class StudentDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 200, WIDTH = 450;
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

    public StudentDialog(List<Group> groups, boolean newStudent, StudentsFrame owner) {
        // После вставки студента без закрытия окна нам потребуется перегрузка списка
        // А для этого нам надо иметь доступ к этому методу в главной форме
        this.owner = owner;
        // Установить заголовок
        if (newStudent) {
            setTitle("Додавання нових студентів");
        } else {
            setTitle("Редагування даних про студента");
        }
        getContentPane().setLayout(new FlowLayout());

        groupList = new JComboBox(new Vector<Group>(groups));

        JRadioButton m = new JRadioButton("Чол");
        JRadioButton w = new JRadioButton("Жін");
        // Сделаем имя такое же, как требуется в баще данных - М/Ж
        m.setActionCommand("Ч");
        w.setActionCommand("Ж");
        // Добавим радио-кнопки в группу
        sex.add(m);
        sex.add(w);

        // Не будем использовать layout совсем
        getContentPane().setLayout(null);

        // Разместим компоненты по абсолютным координатам
        // Фамилия
        JLabel l = new JLabel("Прізвище:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        surName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(surName);
        // Имя
        l = new JLabel("Ім'я:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        firstName.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(firstName);
        // Отчество
        l = new JLabel("По батькові:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        lastName.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(lastName);
        // Пол
        l = new JLabel("Стать:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        m.setBounds(L_X + L_W + 10, 70, C_W / 2, 20);
        getContentPane().add(m);
        // Сделаем по умолчанию женщину - из уважения
        w.setBounds(L_X + L_W + 10 + C_W / 2, 70, C_W / 2, 20);
        w.setSelected(true);
        getContentPane().add(w);
        // Дата рождения
        l = new JLabel("Дата народження:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        dateOfBirth.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(dateOfBirth);
        // Группа
        l = new JLabel("Група:", JLabel.RIGHT);
        l.setBounds(L_X, 115, L_W, 25);
        getContentPane().add(l);
        groupList.setBounds(L_X + L_W + 10, 115, C_W, 25);
        getContentPane().add(groupList);
        // Год обучения
        l = new JLabel("Рік навчання:", JLabel.RIGHT);
        l.setBounds(L_X, 145, L_W, 20);
        getContentPane().add(l);
        year.setBounds(L_X + L_W + 10, 145, C_W, 20);
        getContentPane().add(year);

        JButton btnOk = new JButton("Ok");
        btnOk.setName("Ok");
        btnOk.addActionListener(this);
        btnOk.setBounds(L_X + L_W + C_W + 10 + 50, 10, 100, 25);
        getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 100, 25);
        getContentPane().add(btnCancel);

        if (newStudent) {
            JButton btnNew = new JButton("Add new");
            btnNew.setName("Add new");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 70, 100, 25);
            getContentPane().add(btnNew);
        }

        // Устанавливаем поведение формы при закрытии - не закрывать совсем.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Получаем размеры экрана
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        // А теперь просто помещаем его по центру, вычисляя координаты на основе полученной информации
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
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName.getText());

//        student.setFirstName(new String(firstName.getText().getBytes(), Charset.forName("-8")));

        student.setSurName(surName.getText());
        student.setLastName(lastName.getText());
        Date d = ((SpinnerDateModel) dateOfBirth.getModel()).getDate();
        student.setDateOfBirth(d);
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
            result = true;
            try {
                ManagementSystem.getInstance().insertStudent(getStudent());
                owner.reloadStudents();
                firstName.setText("");
                surName.setText("");
                lastName.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            return;
        }
        if (source.getName().equals("OK")) {
            result = true;
        }
        if (source.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }


}
