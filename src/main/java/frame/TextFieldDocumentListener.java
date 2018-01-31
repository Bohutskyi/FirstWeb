package frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextFieldDocumentListener implements DocumentListener {

    private JTextField textField;
    private Border temp;
    private JLabel message;
    private JButton button;

    public TextFieldDocumentListener(JTextField textField, JLabel label, JButton button) {
        this.textField = textField;
        this.message = label;
        this.button = button;
        temp = textField.getBorder();
        if (textField.getText().equals("")) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            if (message != null) {
                message.setText("Red fields cannot be empty add id");
            }
            if (button != null) {
                button.setEnabled(false);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        textField.setBorder(temp);
        if (message != null) {
            message.setText("");
        }
        if (button != null) {
            button.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (textField.getText().equals("")) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            if (message != null) {
                message.setText("Red fields cannot be empty add id");
            }
            if (button != null) {
                button.setEnabled(false);
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

}
