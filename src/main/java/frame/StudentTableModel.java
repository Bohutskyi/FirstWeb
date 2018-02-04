package frame;

import structures.Student;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.Vector;

public class StudentTableModel extends AbstractTableModel {

    private Vector students;

    StudentTableModel(Vector students) {
        this.students = students;
    }

    public int getRowCount() {
        if (students != null) {
            return students.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int column) {
        if (column < 0 || column > 4) {
            return "";
        }
        String[] columnNames = {MessageResource.getString("id42"), MessageResource.getString("id43"),
                MessageResource.getString("id44"), MessageResource.getString("id39")};
        return  columnNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (students != null) {
            Student student = (Student) students.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return student.getSurName();
                case 1:
                    return student.getFirstName();
                case 2:
                    return student.getLastName();
                case 3:
                    return DateFormat.getDateInstance(DateFormat.SHORT).format(student.getDateOfBirth());
            }
        }
        return null;
    }

    Student getStudent(int rowIndex) {
        if (students != null) {
            if (rowIndex < students.size() && rowIndex >= 0) {
                return (Student) students.get(rowIndex);
            }
        }
        return null;
    }

}
