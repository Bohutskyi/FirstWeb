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
//    private boolean state;

    TextFieldDocumentListener(JTextField textField, JLabel label, JButton button) {
        this.textField = textField;
        this.message = label;
        this.button = button;
        temp = textField.getBorder();
        if (textField.getText().equals("")) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            if (message != null) {
                message.setText(MessageResource.getString("id62"));
            }
            if (button != null) {
                button.setEnabled(false);
            }
        }
    }
//    TextFieldDocumentListener(JLabel label, JButton button, JTextField... textFields) {
//        this.message = label;
//        this.button = button;
//        for (JTextField textField : textFields) {
//
//        }
//    }

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
                message.setText(MessageResource.getString("id62"));
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
