package structures;

import java.nio.charset.Charset;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ManagementSystem {

    private static Connection connection;

    private static ManagementSystem instance;

    private ManagementSystem() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
//        connection = DriverManager.getConnection("jdbc:mysql://localhost/students?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        connection = DriverManager.getConnection("jdbc:mysql://localhost/students?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=utf8",
                "root", "data1234");
    }

    public static synchronized ManagementSystem getInstance() throws Exception {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }

    public List<Group> getGroups() throws SQLException {
        List<Group> groups = new ArrayList<>();
//        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from groups");
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt(1));
                group.setNameGroup(resultSet.getString(2));
                group.setCurator(resultSet.getString(3));
                group.setSpeciality(resultSet.getString(4));
                groups.add(group);
            }
//        }
        return groups;
    }

    public Collection<Student> getStudents() throws SQLException {
        Collection<Student> students = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students order by sur_name, first_name");
        while (resultSet.next()) {
            Student student = new Student(resultSet);
            students.add(student);
        }
        return students;
    }

    public Collection<Student> getStudentsFromGroup(Group group, int year) throws SQLException {
        Collection<Student> students = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from students where group_id = ? and education_year = ? order by sur_name, first_name");
        statement.setInt(1, group.getGroupId());
        statement.setInt(2, year);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Student student = new Student(resultSet);
            students.add(student);
        }
        statement.close();
        resultSet.close();
        return students;
    }

    public void moveStudentsToGroup(Group oldGroup, int oldYear, Group newGroup, int newYear) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update students set group_id = ?, education_year = ? " +
                "where group_id = ? and education_year = ?");
        statement.setInt(1, newGroup.getGroupId());
        statement.setInt(2, newYear);
        statement.setInt(3, oldGroup.getGroupId());
        statement.setInt(4, oldYear);
        statement.execute();
        statement.close();
    }

    public void removeStudentsFromGroup(Group group, int year) throws  SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from students where group_id = ? and education_year = ?");
        statement.setInt(1, group.getGroupId());
        statement.setInt(2, year);
        statement.execute();
        statement.close();
    }

    public void insertStudent(Student student) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement("insert into students " +
//                "(first_name, sur_name, last_name, sex, date_of_birth, group_id, education_year) " +
//                "values (?, ?, ?, ?, ?, ?, ?)");

        PreparedStatement statement = connection.prepareStatement(new String( ("insert into students " +
                "(first_name, sur_name, last_name, sex, date_of_birth, group_id, education_year) " +
                "values (?, ?, ?, ?, ?, ?, ?)").getBytes(), Charset.forName("utf-8")));

        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getSurName());
        statement.setString(3, student.getLastName());
        statement.setString(4, String.valueOf(student.getSex()));
        statement.setDate(5, new Date(student.getDateOfBirth().getTime()));
        statement.setInt(6, student.getGroupId());
        statement.setInt(7, student.getEducationYear());
        statement.execute();
        statement.close();
    }

    public void updateStudent(Student student) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update students set " +
                "first_name = ?, last_name = ?, sur_name = ?, sex = ?, date_of_birth = ?, group_id = ?, education_year = ? " +
                "where student_id = ?");
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());
        statement.setString(3, student.getSurName());
        statement.setString(4, String.valueOf(student.getSex()));
        statement.setDate(5, new Date(student.getDateOfBirth().getTime()));
        statement.setInt(6, student.getGroupId());
        statement.setInt(7, student.getEducationYear());
        statement.setInt(8, student.getStudentId());
        statement.execute();
        statement.close();
    }

    public void deleteStudent(Student student) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from students where student_id = ?");
        statement.setInt(1, student.getStudentId());
        statement.execute();
        statement.close();
    }


}
