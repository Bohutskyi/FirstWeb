package structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Student implements Comparable {

    private int studentId;
    private String firstName;
    private String surName;
    private String lastName;
    private Date dateOfBirth;
    private char sex;
    private int groupId;
    private int educationYear;

    public Student() {

    }

    public Student(ResultSet resultSet) throws SQLException {
        this.studentId = resultSet.getInt(1);
        this.firstName = resultSet.getString(2);
        this.surName = resultSet.getString(3);
        this.lastName = resultSet.getString(4);
        this.sex = resultSet.getString(6).charAt(0);
        this.dateOfBirth = resultSet.getDate(5);
        this.groupId = resultSet.getInt(7);
        this.educationYear = resultSet.getInt(8);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(int educationYear) {
        this.educationYear = educationYear;
    }

    @Override
    public String toString() {
        return "structures.Student{" +
                "surName='" + surName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth) +
                ", groupId=" + groupId +
                ", educationYear=" + educationYear +
                '}';
    }

    public int compareTo(Object o) {
        Collator collator = Collator.getInstance(new Locale("ua"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(this.toString(), o.toString());
    }
}
