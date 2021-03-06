package frame;

import structures.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;

public class StudentsFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

    private static final String MOVE_GROUP = "moveGroup";
    private static final String CLEAR_GROUP = "clearGroup";
    private static final String CREATE_CROUP = "createGroup";
    private static final String INSERT_STUDENT = "insertStudent";
    private static final String UPDATE_STUDENT = "updateStudent";
    private static final String DELETE_STUDENT = "deleteStudent";
    private static final String ALL_STUDENTS = "allStudent";
    private static final String EXIT = "exit";
    private static final String SETTINGS = "settings";  //WIP
    private static final String GROUP_INFO = "groupInfo";

    private ManagementSystem managementSystem;
    private JList groupList;
    private JTable studentsList;
    private JSpinner spYear;
    private DefaultListModel model;

    private JPanel messagePanel;
    private JLabel messageLabel;

    private static void setUIFont(FontUIResource fontUIResource) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource origin = (FontUIResource) value;
                Font font = new Font(fontUIResource.getFontName(), origin.getStyle(), fontUIResource.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

    private void load(){
        try (BufferedReader reader = new BufferedReader(new FileReader("settings"))) {
            String[] buffer = reader.readLine().split(" ");
            MessageResource.loadMessages(buffer);
            SettingsDialog.setColor(reader.readLine().charAt(0));
//            char c;
//            if ((c = reader.readLine().charAt(0)) == 'd') {
//                UIManager.getDefaults().put("Panel.background", new ColorUIResource(new Color(4, 42, 128)));
//                UIManager.getDefaults().put("Label.foreground", new ColorUIResource(Color.WHITE));
//                UIManager.getDefaults().put("Button", new ColorUIResource(new Color(2, 32, 101)));
//                UIManager.getDefaults().put("Table.foreground", new ColorUIResource(new Color(2, 32, 101)));
//            } else if (c == 'l') {
//
//            }
            reader.close();
        } catch (IOException e) {
            showErrorMessage(e.getMessage());
        }
    }

    public StudentsFrame() throws Exception {
        load();

        setUIFont(new FontUIResource(new Font("Courier", Font.BOLD, 13)));
//        System.out.println(UIManager.getSystemLookAndFeelClassName());

        getContentPane().setLayout(new BorderLayout());
//        this.setBackground(ColorTheme.getColor1());

        JMenuBar menuBar = new JMenuBar();

        JMenu firstMenu = new JMenu(MessageResource.getString("id47"));
        JMenuItem settingsItem = new JMenuItem(MessageResource.getString("id48"));
        settingsItem.setName(SETTINGS);
        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            settingsItem.setAccelerator(KeyStroke.getKeyStroke(',', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        } else {
            settingsItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        }
        settingsItem.addActionListener(this);
        firstMenu.add(settingsItem);
        firstMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem(MessageResource.getString("id46"));
        exitItem.setName(EXIT);
        settingsItem.setName(SETTINGS);
        exitItem.addActionListener(this);
        firstMenu.add(exitItem);
        menuBar.add(firstMenu);

        JMenu menu = new JMenu(MessageResource.getString("id1"));
//        JMenu menu = new JMenu("Звіти");
//        menu.setText(Localization.getMessages().getString("JMenuReport"));
//        JMenuItem menuItem = new JMenuItem("Всі студенти");
        JMenuItem menuItem1 = new JMenuItem(MessageResource.getString("id2"));
        menuItem1.setName(ALL_STUDENTS);
        menuItem1.addActionListener(this);
        menu.add(menuItem1);
        menu.addSeparator();

        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        topPanel.add(new JLabel("Рік навчання:"));
        topPanel.add(new JLabel(MessageResource.getString("id3")));
        SpinnerModel sm = new SpinnerNumberModel(2006, 1900, 2100, 1);
        spYear = new JSpinner(sm);
        spYear.addChangeListener(this);
        topPanel.add(spYear);
//        topPanel.setBackground(ColorTheme.getColor1());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
//        bottomPanel.setBackground(ColorTheme.getColor1());

        GroupPanel leftPanel = new GroupPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        leftPanel.setBackground(ColorTheme.getColor1());

        managementSystem = ManagementSystem.getInstance();
        Vector<Group> gr = new Vector<>(managementSystem.getGroups());
//        leftPanel.add(new JLabel("Групи:"), BorderLayout.NORTH);
        leftPanel.add(new JLabel(MessageResource.getString("id4")), BorderLayout.NORTH);


//        groupList = new JList(gr);
        model = new DefaultListModel();
        groupList = new JList(model);
        for (Group g : managementSystem.getGroups()) {
            model.addElement(g);
        }



//        groupList.setCellRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                Component render = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                if (value instanceof Group) {
//                    System.out.println("Coooool");
//                }
//                return render;
//            }
//        });


        groupList.addListSelectionListener(this);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.setSelectedIndex(0);
        leftPanel.add(new JScrollPane(groupList), BorderLayout.CENTER);
//        groupList.setBackground(ColorTheme.getColor1());

//        JButton buttonMoveGroup = new JButton("Перемістити");
        JButton buttonMoveGroup = new JButton(MessageResource.getString("id5"));
        buttonMoveGroup.setName(MOVE_GROUP);
        JButton buttonClearGroup = new JButton(MessageResource.getString("id6"));
//        JButton buttonClearGroup = new JButton("Очистити");
        buttonClearGroup.setName(CLEAR_GROUP);
//        JButton buttonCreateGroup = new JButton(("Створити"));
        JButton buttonCreateGroup = new JButton(MessageResource.getString("id7"));
        buttonCreateGroup.setName(CREATE_CROUP);
        JButton buttonGroupInfo = new JButton(MessageResource.getString("id53"));
        buttonGroupInfo.setName(GROUP_INFO);
        buttonMoveGroup.addActionListener(this);
        buttonClearGroup.addActionListener(this);
        buttonCreateGroup.addActionListener(this);
        buttonGroupInfo.addActionListener(this);

        JPanel panelButtonGroup = new JPanel();
        panelButtonGroup.setLayout(new GridLayout(2, 2));
        panelButtonGroup.add(buttonMoveGroup);
        panelButtonGroup.add(buttonClearGroup);
        panelButtonGroup.add(buttonCreateGroup);
        panelButtonGroup.add(buttonGroupInfo);
        leftPanel.add(panelButtonGroup, BorderLayout.SOUTH);
//        panelButtonGroup.setBackground(ColorTheme.getColor1());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        rightPanel.setBackground(ColorTheme.getColor2());

//        rightPanel.add(new JLabel("Студенти:"), BorderLayout.NORTH);
        rightPanel.add(new JLabel(MessageResource.getString("id8")), BorderLayout.NORTH);

        // Создаем таблицу и вставляем ее в скроллируемую
        // панель, которую в свою очередь уже кладем на панель right
        // Наша таблица пока ничего не умеет - просто положим ее как заготовку
        // Сделаем в ней 4 колонки - Фамилия, Имя, Отчество, Дата рождения
        studentsList = new JTable(1, 4);
//        studentsList.setGridColor(ColorTheme.getColor2());
//        studentsList.setBackground(ColorTheme.getColor2());

        JScrollPane rightPane = new JScrollPane(studentsList);
//        rightPane.setBackground(ColorTheme.getColor2());
        rightPanel.add(rightPane, BorderLayout.CENTER);
//        rightPanel.add(new JScrollPane(studentsList), BorderLayout.CENTER);

//        JButton btnAddSt = new JButton("Добавити");
        JButton btnAddSt = new JButton(MessageResource.getString("id9"));
        btnAddSt.setName(INSERT_STUDENT);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton(MessageResource.getString("id10"));
//        JButton btnUpdSt = new JButton("Редагувати");
        btnUpdSt.setName(UPDATE_STUDENT);
        btnUpdSt.addActionListener(this);
//        JButton btnDelSt = new JButton("Видалити");
        JButton btnDelSt = new JButton(MessageResource.getString("id11"));
        btnDelSt.setName(DELETE_STUDENT);
        btnDelSt.addActionListener(this);

        JPanel panelButtonStudent = new JPanel();
        panelButtonStudent.setLayout(new GridLayout(1, 3));
        panelButtonStudent.add(btnAddSt);
        panelButtonStudent.add(btnUpdSt);
        panelButtonStudent.add(btnDelSt);
        rightPanel.add(panelButtonStudent, BorderLayout.SOUTH);
//        panelButtonStudent.setBackground(ColorTheme.getColor2());

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);

        messagePanel = new JPanel();
        messageLabel = new JLabel("Hello");
        messageLabel.setForeground(Color.RED);
        messagePanel.add(messageLabel);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);
        getContentPane().add(messagePanel, BorderLayout.SOUTH);
        messagePanel.setVisible(false);

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
            if (c.getName().equals(GROUP_INFO)) {
                showGroupInfo();
            }
            if (c.getName().equals(SETTINGS)) {
                showSettings();
            }
            if (c.getName().equals(EXIT)) {
                this.dispose();
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

    void reloadStudents() {
        new Thread(() -> {
            if (studentsList != null) {
                Group group = (Group) groupList.getSelectedValue();
                int year = ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
                try {
                    Collection<Student> students = managementSystem.getStudentsFromGroup(group, year);
                    studentsList.setModel(new StudentTableModel(new Vector<>(students)));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                }
            }
        }).start();
    }

    private void addLastAddedGroup() {
        new Thread( () -> {
            if (groupList != null) {
                try {
                    Group group = managementSystem.getLastAddedGroup();
                    if (group != null && !model.get(model.getSize() - 1).toString().equals(group.toString())) {
                        model.addElement(group);
                    }
                } catch (Exception e) {
                    showErrorMessage(e.getMessage());
                }
            }
        }).start();
    }

    private void moveGroup() {
        new Thread(() -> {
            if (groupList.getSelectedValue() == null) {
//                JOptionPane.showMessageDialog(StudentsFrame.this, "Виберіть групу");
                JOptionPane.showMessageDialog(StudentsFrame.this, MessageResource.getString("id12"));
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
//                        "Ви дійсно хочете видалити студентів із групи?",
                        MessageResource.getString("id13"),
//                        "Видалення групи", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        MessageResource.getString("id14"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
//                    JOptionPane.showMessageDialog(StudentsFrame.this, "Не можна редагувати декілька студентів одночасно");
                    JOptionPane.showMessageDialog(StudentsFrame.this, MessageResource.getString("id15"));
                    return;
                } else if (studentsList.getSelectedRowCount() == 0) {
//                    JOptionPane.showMessageDialog(StudentsFrame.this, "Виберіть студента для редагування");
                    JOptionPane.showMessageDialog(StudentsFrame.this, MessageResource.getString("id16"));
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

                if (studentsList.getSelectedRowCount() == 0) {
                    message = MessageResource.getString("id45");
                    JOptionPane.showOptionDialog(StudentsFrame.this, message,
                            MessageResource.getString("id19"), JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
                            UIManager.getIcon("OptionPane.informationIcon"),
                            null, null);
                    return;
                }

                if (studentsList.getSelectedRowCount() == 1) {
//                    message = "Ви дійсно хочете видалити студента?";
                    message = MessageResource.getString("id17");
//                } else if (studentsList.getSelectedRowCount() == 0) {
//                    message = MessageResource.getString("id45");
//                    return;
                } else {
//                    message = "Ви дійсно хочете видалити студентів?";
                    message = MessageResource.getString("id18");
                }

                StudentTableModel studentTableModel = (StudentTableModel) studentsList.getModel();
                if (JOptionPane.showConfirmDialog(StudentsFrame.this, message,
//                        "Видалення студента", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        MessageResource.getString("id19"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
        new Thread(() -> {
            try {
                CreateGroupDialog dialog = new CreateGroupDialog();
                dialog.setModal(true);
                dialog.setVisible(true);
                addLastAddedGroup();
            } catch (Exception e) {
                showErrorMessage(e.getMessage());
            }
        }).start();
    }

    //WIP
    private void showAllStudents() {
        JOptionPane.showMessageDialog(this, "showAllStudents");
    }

    private void showGroupInfo() {
        new Thread(() -> {
            try {
                if (groupList.getSelectedValue() != null) {
                    GroupInfoDialog groupInfoDialog = new GroupInfoDialog((Group) groupList.getSelectedValue());
                    groupInfoDialog.setModal(true);
                    groupInfoDialog.setVisible(true);
                }
            } catch (Exception e) {
                showErrorMessage(e.getMessage());
            }
        }).start();
    }

    synchronized void showErrorMessage(String message) {
        if (!messagePanel.isVisible()) {
            messagePanel.setVisible(true);
        }
        messageLabel.setText(message);
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {

        }
        messagePanel.setVisible(false);
    }

    private void showSettings() {
        new Thread(() -> {
            SettingsDialog dialog = new SettingsDialog();
            dialog.setModal(true);
            dialog.setVisible(true);
        }).start();
    }

    class GroupPanel extends JPanel {
        public Dimension getPreferredSize() {
            return new Dimension(250, 0);
        }
    }

}
