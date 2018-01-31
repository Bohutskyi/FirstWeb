package structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Teacher {

    private int teacherID;
    private String firstName;
    private String surName;
    private String lastName;
    private Date dateOfBirth;
    private char sex;

    Teacher(ResultSet resultSet) throws SQLException {
        this.teacherID = resultSet.getInt(1);
        this.firstName = resultSet.getString(2);
        this.surName = resultSet.getString(3);
        this.lastName = resultSet.getString(4);
        this.sex = resultSet.getString(6).charAt(0);
        this.dateOfBirth = resultSet.getDate(5);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherID +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex=" + sex +
                '}';
    }

    public int getTeacherID() {
        return teacherID;
    }
}
