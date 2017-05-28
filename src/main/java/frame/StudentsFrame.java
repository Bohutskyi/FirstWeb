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

    private static final String MOVE_GROUP = "moveGroup";
    private static final String CLEAR_GROUP = "clearGroup";
    private static final String CREATE_CROUP = "createGroup";
    private static final String INSERT_STUDENT = "insertStudent";
    private static final String UPDATE_STUDENT = "updateStudent";
    private static final String DELETE_STUDENT = "deleteStudent";
    private static final String ALL_STUDENTS = "allStudent";

    private ManagementSystem managementSystem;
    private JList groupList;
    private JTable studentsList;
    private JSpinner spYear;

    public StudentsFrame() throws Exception {
        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Звіти");
        JMenuItem menuItem = new JMenuItem("Всі студенти");
        menuItem.setName(ALL_STUDENTS);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Рік навчання:"));
        SpinnerModel sm = new SpinnerNumberModel(2006, 1900, 2100, 1);
        spYear = new JSpinner(sm);
        spYear.addChangeListener(this);
        topPanel.add(spYear);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        GroupPanel leftPanel = new GroupPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        managementSystem = ManagementSystem.getInstance();
        Vector<Group> gr = new Vector<Group>(managementSystem.getGroups());
        leftPanel.add(new JLabel("Групи:"), BorderLayout.NORTH);
        groupList = new JList(gr);
        groupList.addListSelectionListener(this);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.setSelectedIndex(0);
        leftPanel.add(new JScrollPane(groupList), BorderLayout.CENTER);

        JButton buttonMoveGroup = new JButton("Перемістити");
        buttonMoveGroup.setName(MOVE_GROUP);
        JButton buttonClearGroup = new JButton("Очистити");
        buttonClearGroup.setName(CLEAR_GROUP);
        JButton buttonCreateGroup = new JButton(("Створити"));
        buttonCreateGroup.setName(CREATE_CROUP);
        buttonMoveGroup.addActionListener(this);
        buttonClearGroup.addActionListener(this);
        buttonCreateGroup.addActionListener(this);

        JPanel panelButtonGroup = new JPanel();
        panelButtonGroup.setLayout(new GridLayout(2, 2));
        panelButtonGroup.add(buttonMoveGroup);
        panelButtonGroup.add(buttonClearGroup);
        panelButtonGroup.add(buttonCreateGroup);
        leftPanel.add(panelButtonGroup, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        rightPanel.add(new JLabel("Студенти:"), BorderLayout.NORTH);

        // Создаем таблицу и вставляем ее в скроллируемую
        // панель, которую в свою очередь уже кладем на панель right
        // Наша таблица пока ничего не умеет - просто положим ее как заготовку
        // Сделаем в ней 4 колонки - Фамилия, Имя, Отчество, Дата рождения
        studentsList = new JTable(1, 4);
        rightPanel.add(new JScrollPane(studentsList), BorderLayout.CENTER);

        JButton btnAddSt = new JButton("Добавити");
        btnAddSt.setName(INSERT_STUDENT);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton("Редагувати");
        btnUpdSt.setName(UPDATE_STUDENT);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Видалити");
        btnDelSt.setName(DELETE_STUDENT);
        btnDelSt.addActionListener(this);

        JPanel panelButtonStudent = new JPanel();
        panelButtonStudent.setLayout(new GridLayout(1, 3));
        panelButtonStudent.add(btnAddSt);
        panelButtonStudent.add(btnUpdSt);
        panelButtonStudent.add(btnDelSt);
        rightPanel.add(panelButtonStudent, BorderLayout.SOUTH);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);

        setBounds(100, 100, 700, 500);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(MOVE_GROUP)) {
                moveGroup();
            }
            if (c.getName().equals(CLEAR_GROUP)) {
                clearGroup();
            }
            if (c.getName().equals(ALL_STUDENTS)) {
                showAllStudents();
            }
            if (c.getName().equals(INSERT_STUDENT)) {
                insertStudent();
            }
            if (c.getName().equals(UPDATE_STUDENT)) {
                updateStudent();
            }
            if (c.getName().equals(DELETE_STUDENT)) {
                deleteStudent();
            }
            if (c.getName().equals(CREATE_CROUP)) {
                createGroup();
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadStudents();
        }
    }

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

    private void moveGroup() {
        new Thread(() -> {
            if (groupList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(StudentsFrame.this, "Виберіть групу");
                return;
            }
            try {
                Group group = (Group) groupList.getSelectedValue();
                int year = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                MoveGroupDialog dialog = new MoveGroupDialog(year, managementSystem.getGroups());
                dialog.setModal(true);
                dialog.setVisible(true);
                if (dialog.getResult()) {
                    managementSystem.moveStudentsToGroup(group, year, dialog.getGroup(), dialog.getYear());
                    reloadStudents();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
            }
        }).start();
    }

    private void clearGroup() {
        new Thread(() -> {
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
        }).start();
    }

    private void insertStudent() {
        new Thread(() -> {
            try {
                StudentDialog dialog = new StudentDialog(managementSystem.getGroups(), true, StudentsFrame.this);
                dialog.setModal(true);
                dialog.setVisible(true);
                reloadStudents();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
            }
        }).start();
    }

    private void updateStudent() {
        new Thread(() -> {
            if (studentsList != null) {
                if (studentsList.getSelectedRowCount() > 1) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, "Не можна редагувати декілька студентів одночасно");
                    return;
                } else if (studentsList.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, "Виберіть студента для редагування");
                    return;
                }
                try {
                    StudentDialog dialog = new StudentDialog(managementSystem.getGroups(), false, StudentsFrame.this);
                    dialog.setStudent(((StudentTableModel) studentsList.getModel()).getStudent(studentsList.getSelectedRow()));
                    dialog.setModal(true);
                    dialog.setVisible(true);
                    reloadStudents();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        }).start();
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

    private void createGroup() {
//        JOptionPane.showMessageDialog(StudentsFrame.this, CREATE_CROUP);

        new Thread(() -> {
            CreateGroupDialog dialog = new CreateGroupDialog();
            dialog.setModal(true);
            dialog.setVisible(true);
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
