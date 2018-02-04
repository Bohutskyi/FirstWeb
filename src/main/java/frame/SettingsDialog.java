package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

public class SettingsDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 350, WIDTH = 300;
    private static final String LIGHT = "light", DARK = "dark", OK = "ok";

    private static char color;

    private ButtonGroup colorBar;
    private JComboBox languageBox;

    SettingsDialog() {
        setTitle(MessageResource.getString("id48"));
        setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();
        this.add(tabbedPane);

        JPanel settings = new JPanel();
        tabbedPane.addTab(MessageResource.getString("id48"), settings);

//        settings.setLayout(new BoxLayout(settings, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel(MessageResource.getString("id54"));
        settings.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorBar = new ButtonGroup();
        JRadioButton light = new JRadioButton(MessageResource.getString("id55"));
        light.setActionCommand(LIGHT);
        JRadioButton dark = new JRadioButton(MessageResource.getString("id56"));
        dark.setActionCommand(DARK);
        if (SettingsDialog.color == 'l') {
            light.setSelected(true);
        } else if (SettingsDialog.color == 'd') {
            dark.setSelected(true);
        }
        colorBar.add(light);
        colorBar.add(dark);

        settings.add(light);
        settings.add(dark);

        label = new JLabel(MessageResource.getString("id58"));
        settings.add(label);

        languageBox = new JComboBox();
        languageBox.addItem(new ComboItem("Project.properties", "Default"));
        try {
            File files = new File("target/classes/Localization");
            for (String temp : files.list()) {
                if (temp.startsWith("Project_")) {
                    languageBox.addItem(new ComboItem(temp, temp.split("_")[1]));
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        settings.add(languageBox);


        JButton okButton = new JButton(MessageResource.getString("id57"));
        okButton.setName(OK);
        settings.add(okButton);
        okButton.addActionListener(this);

        JPanel about = new JPanel();
        tabbedPane.addTab(MessageResource.getString("id59"), about);
        about.setLayout(new BoxLayout(about, BoxLayout.PAGE_AXIS));
        about.add(Box.createVerticalGlue());
        label = new JLabel("Project name add id");
        about.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label = new JLabel(MessageResource.getString("id60") + " 1.2.5");
        about.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createVerticalGlue());
        label = new JLabel(MessageResource.getString("id61"));
        about.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label = new JLabel("bohutskyi.oleksandr@gmail.com");
        about.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createVerticalGlue());
        label = new JLabel("2018");
        about.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createVerticalGlue());

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) dimension.getWidth() - WIDTH) / 2, ((int) dimension.getHeight() - HEIGHT) / 2,
                WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getName().equals(OK)) {
            try {
                FileWriter writer = new FileWriter("settings");

                ComboItem selected = ((ComboItem) languageBox.getSelectedItem());

                if (selected.value.equals("Default")) {
                    writer.write("en US");
                } else {
                    String[] buffer = selected.key.split("_");
                    writer.write(buffer[1] + " " + buffer[2].substring(0, 2));
                }

                for (Enumeration elements = colorBar.getElements(); elements.hasMoreElements();) {
                    AbstractButton ab = (AbstractButton) elements.nextElement();
                    if (ab.isSelected()) {
                        writer.write("\n" + ab.getActionCommand().charAt(0));
                    }
                }
                writer.close();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

    static void setColor(char color) {
        SettingsDialog.color = color;
    }

    class ComboItem {
        private String key, value;

        ComboItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
