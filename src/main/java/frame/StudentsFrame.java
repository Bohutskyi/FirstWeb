package frame;

import structures.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

public class StudentsFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

    private static final String MOVE_GR = "moveGroup";
    private static final String CLEAR_GR = "clearGroup";
    private static final String INSERT_ST = "insertStudent";
    private static final String UPDATE_ST = "updateStudent";
    private static final String DELETE_ST = "deleteStudent";
    private static final String ALL_STUDENTS = "allStudent";

    private ManagementSystem managementSystem;
    private JList groupList;
    private JTable studentsList;
    private JSpinner spYear;

    public StudentsFrame() throws Exception {
        // Устанавливаем layout для всей клиентской части формы
        getContentPane().setLayout(new BorderLayout());

        // Создаем строку меню
        JMenuBar menuBar = new JMenuBar();
        // Создаем выпадающее меню
        JMenu menu = new JMenu("Звіти");
        // Создаем пункт в выпадающем меню
        JMenuItem menuItem = new JMenuItem("Всі студенти");
        menuItem.setName(ALL_STUDENTS);
        // Добавляем листенер
        menuItem.addActionListener(this);
        // Вставляем пункт меню в выпадающее меню
        menu.add(menuItem);
        // Вставляем выпадающее меню в строку меню
        menuBar.add(menu);
        // Устанавливаем меню для формы
        setJMenuBar(menuBar);

        // Создаем верхнюю панель, где будет поле для ввода года
        JPanel top = new JPanel();
        // Устанавливаем для нее layout
        top.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Вставляем пояснительную надпись
        top.add(new JLabel("Рік навчання:"));
        // Делаем спин-поле
        // 1. Задаем модель поведения - только цифры
        // 2. Вставляем в панель
        SpinnerModel sm = new SpinnerNumberModel(2006, 1900, 2100, 1);
        spYear = new JSpinner(sm);
        // Добавляем листенер
        spYear.addChangeListener(this);
        top.add(spYear);

        // Создаем нижнюю панель и задаем ей layout
        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());

        // Создаем левую панель для вывода списка групп
        // Она у нас
        GroupPanel left = new GroupPanel();
        // Задаем layout и задаем "бордюр" вокруг панели
        left.setLayout(new BorderLayout());
        left.setBorder(new BevelBorder(BevelBorder.LOWERED));

        // Получаем коннект к базе и создаем объект ManagementSystem
        managementSystem = ManagementSystem.getInstance();
        // Получаем список групп
        Vector<Group> gr = new Vector<Group>(managementSystem.getGroups());
        // Создаем надпись
        left.add(new JLabel("Групи:"), BorderLayout.NORTH);
        // Создаем визуальный список и вставляем его в скроллируемую
        // панель, которую в свою очередь уже кладем на панель left
        groupList = new JList(gr);
        // Добавляем листенер
        groupList.addListSelectionListener(this);
        // Сразу выделяем первую группу
        groupList.setSelectedIndex(0);
        left.add(new JScrollPane(groupList), BorderLayout.CENTER);
        // Создаем кнопки для групп
        JButton btnMvGr = new JButton("Перемістити");
        btnMvGr.setName(MOVE_GR);
        JButton btnClGr = new JButton("Очистити");
        btnClGr.setName(CLEAR_GR);
        // Добавляем листенер
        btnMvGr.addActionListener(this);
        btnClGr.addActionListener(this);
        // Создаем панель, на которую положим наши кнопки и кладем ее вниз
        JPanel pnlBtnGr = new JPanel();
        pnlBtnGr.setLayout(new GridLayout(1, 2));
        pnlBtnGr.add(btnMvGr);
        pnlBtnGr.add(btnClGr);
        left.add(pnlBtnGr, BorderLayout.SOUTH);

        // Создаем правую панель для вывода списка студентов
        JPanel right = new JPanel();
        // Задаем layout и задаем "бордюр" вокруг панели
        right.setLayout(new BorderLayout());
        right.setBorder(new BevelBorder(BevelBorder.LOWERED));

        // Создаем надпись
        right.add(new JLabel("Студенти:"), BorderLayout.NORTH);
        // Создаем таблицу и вставляем ее в скроллируемую
        // панель, которую в свою очередь уже кладем на панель right
        // Наша таблица пока ничего не умеет - просто положим ее как заготовку
        // Сделаем в ней 4 колонки - Фамилия, Имя, Отчество, Дата рождения
        studentsList = new JTable(1, 4);
        right.add(new JScrollPane(studentsList), BorderLayout.CENTER);
        // Создаем кнопки для студентов
        JButton btnAddSt = new JButton("Добавити");
        btnAddSt.setName(INSERT_ST);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton("Редагувати");
        btnUpdSt.setName(UPDATE_ST);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Видалити");
        btnDelSt.setName(DELETE_ST);
        btnDelSt.addActionListener(this);
        // Создаем панель, на которую положим наши кнопки и кладем ее вниз
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddSt);
        pnlBtnSt.add(btnUpdSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

        // Вставляем панели со списками групп и студентов в нижнюю панель
        bot.add(left, BorderLayout.WEST);
        bot.add(right, BorderLayout.CENTER);

        // Вставляем верхнюю и нижнюю панели в форму
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(bot, BorderLayout.CENTER);

        // Задаем границы формы
        setBounds(100, 100, 700, 500);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(MOVE_GR)) {
                moveGroup();
            }
            if (c.getName().equals(CLEAR_GR)) {
                clearGroup();
            }
            if (c.getName().equals(ALL_STUDENTS)) {
                showAllStudents();
            }
            if (c.getName().equals(INSERT_ST)) {
                insertStudent();
            }
            if (c.getName().equals(UPDATE_ST)) {
                updateStudent();
            }
            if (c.getName().equals(DELETE_ST)) {
                deleteStudent();
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadStudents();
        }
    }

    // Метод для обеспечения интерфейса ChangeListener
    public void stateChanged(ChangeEvent e) {
        reloadStudents();
    }

    public void reloadStudents() {
        new Thread(() -> {
            if (studentsList != null) {
                Group group = (Group) groupList.getSelectedValue();
                int year = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                try {
                    Collection<Student> students = managementSystem.getStudentsFromGroup(group, year);
                    studentsList.setModel(new StudentTableModel(new Vector<Student>(students)));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        }).start();
    }

    // метод для переноса группы
    private void moveGroup() {
//        JOptionPane.showMessageDialog(this, "moveGroup");
        new Thread() {
            @Override
            public void run() {
                if (groupList.getSelectedValue() == null) {
                    return;
                }
                try {
                    Group group = (Group) groupList.getSelectedValue();
                    int year = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                    MoveGroupDialog dialog = new MoveGroupDialog(year, managementSystem.getGroups());
                    dialog.setModal(true);
                    dialog.setVisible(true);
                    if (dialog.getResult()) {
                        managementSystem.moveStudentsToGroup(group, year, dialog.getGroup(), dialog.getY());
                        reloadStudents();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        }.start();
    }

    // метод для очистки группы
    private void clearGroup() {
//        JOptionPane.showMessageDialog(this, "clearGroup");
        new Thread() {
            @Override
            public void run() {
                if (groupList.getSelectedValue() != null) {
                    if (JOptionPane.showConfirmDialog(StudentsFrame.this,
                            "Ви дійсно хочете видалити студентів із групи?",
                            "Видалення групи", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Group group = (Group) groupList.getSelectedValue();
                        int year = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                        try {
                            managementSystem.removeStudentsFromGroup(group, year);
                            reloadStudents();
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                        }
                    }
                }
            }
        }.start();
    }

    // метод для добавления студента
    private void insertStudent() {
//        JOptionPane.showMessageDialog(this, "insertStudent");
        new Thread() {
            @Override
            public void run() {
//                if (studentsList.getSelectedRowCount() > 1) {
//                    JOptionPane.showMessageDialog(StudentsFrame.this, "Редагувати дані можна про одного студента одночасно");
//                    return;
//                } else if (studentsList.getSelectedRowCount() == 0) {
//                    JOptionPane.showMessageDialog(StudentsFrame.this, "Виберіть студента");
//                    return;
//                }
                try {
                    StudentDialog dialog = new StudentDialog(managementSystem.getGroups(), true, StudentsFrame.this);
                    dialog.setModal(true);
                    dialog.setVisible(true);
                    reloadStudents();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        }.start();
    }

    // метод для редактирования студента
    private void updateStudent() {
        JOptionPane.showMessageDialog(this, "updateStudent");
//        new Thread(() -> {
//
//            if (studentsList != null) {
//                if (studentsList.getSelectedRowCount() > 1) {
//                    JOptionPane.showMessageDialog(StudentsFrame.this, "Не можна редагувати декілька студентів одночасно");
//                } else if (studentsList.getSelectedRowCount() == 0) {
//                    JOptionPane.showMessageDialog(StudentsFrame.this, "Виберіть студента для редагування");
//                }
//
//                try {
//                    StudentDialog dialog = new StudentDialog(managementSystem.getGroups(), false, StudentsFrame.this);
//                    dialog.setStudent(((StudentTableModel) studentsList.getModel()).getStudent(studentsList.getSelectedRow()));
//                    dialog.setModal(true);
//                    dialog.setVisible(true);
//                    reloadStudents();
//                } catch (SQLException e) {
//                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
//                }
//
//
//            }
//        }).start();
    }

    private void deleteStudent() {
        new Thread(() -> {
            if (studentsList != null) {

                String message;
                if (studentsList.getSelectedRowCount() == 1) {
                    message = "Ви дійсно хочете видалити студента?";
                } else if (studentsList.getSelectedRowCount() == 0) {
                    return;
                } else {
                    message = "Ви дійсно хочете видалити студентів?";
                }

                StudentTableModel studentTableModel = (StudentTableModel) studentsList.getModel();
                if (JOptionPane.showConfirmDialog(StudentsFrame.this, message,
                        "Видалення студента", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (int i : studentsList.getSelectedRows()) {
                        Student student = studentTableModel.getStudent(i);
                        try {
                            managementSystem.deleteStudent(student);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                        }
                    }
                }
                reloadStudents();
            }
        }).start();
    }

    private void showAllStudents() {
        JOptionPane.showMessageDialog(this, "showAllStudents");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                StudentsFrame studentsFrame = new StudentsFrame();
                studentsFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                studentsFrame.setVisible(true);

                studentsFrame.reloadStudents();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    class GroupPanel extends JPanel {
        public Dimension getPreferredSize() {
            return new Dimension(250, 0);
        }
    }

}
