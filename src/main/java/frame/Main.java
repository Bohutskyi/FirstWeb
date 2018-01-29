package frame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                StudentsFrame studentsFrame = new StudentsFrame();
                studentsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                studentsFrame.setVisible(true);

                studentsFrame.reloadStudents();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}
