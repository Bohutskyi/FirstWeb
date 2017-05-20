package frame;

import structures.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.List;

public class MoveGroupDialog extends JDialog implements ActionListener {

    private static final int HEIGHT = 150, WIDTH = 400;
    private JSpinner spYear;
    private JComboBox groupList;
    private JButton buttonOk = new JButton("Ok");
    private JButton buttonCancel = new JButton("Cancel");
    private boolean result = false;

    public MoveGroupDialog(int year, List groups) {
        setTitle("Пеміщення групи");
        setUndecorated(true);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5,5, 5);

        JLabel label = new JLabel("Нова група");
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(label, constraints);
        getContentPane().add(label);

        groupList = new JComboBox(new Vector(groups));
        // Элемент занимает всю оставшуюся ширину
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        // Растягиваем компонент по всему пространству для него
        constraints.fill = GridBagConstraints.BOTH;
        // "Привязываем" его к левой части
        constraints.anchor = GridBagConstraints.WEST;
        // Устанавливаем это правило для нашего компонета
        gridBagLayout.setConstraints(groupList, constraints);
        // Добавляем компонент
        getContentPane().add(groupList);

        // Третий элемент - заголовок для поля выбора года
        label = new JLabel("Новий рік:");
        // После него можно будет еще помещать компоненты
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        // Не заполняем все пространство, отведенное компоненту
        constraints.fill = GridBagConstraints.NONE;
        // "Привязываем" компонент к правому краю
        constraints.anchor = GridBagConstraints.EAST;
        // Устанавливаем это правило для нашего компонета
        gridBagLayout.setConstraints(label, constraints);
        // Добавляем компонент
        getContentPane().add(label);

        // Сразу увеличиваем группу на один год - для перевода
        spYear = new JSpinner(new SpinnerNumberModel(year + 1, 1900, 2100, 1));
        // Элемент занимает всю оставшуюся ширину
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        // Растягиваем компонент по всему пространству для него
        constraints.fill = GridBagConstraints.BOTH;
        // "Привязываем" его к левой части
        constraints.anchor = GridBagConstraints.WEST;
        // Устанавливаем это правило для нашего компонета
        gridBagLayout.setConstraints(spYear, constraints);
        // Добавляем компонент
        getContentPane().add(spYear);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        buttonOk.setName("OK");
        // Добавляем листенер для кнопки
        buttonOk.addActionListener(this);
        // Устанавливаем это правило для нашего компонета
        gridBagLayout.setConstraints(buttonOk, constraints);
        // Добавляем компонент
        getContentPane().add(buttonOk);

        buttonCancel.setName("Cancel");
        // Добавляем листенер для кнопки
        buttonCancel.addActionListener(this);
        // Устанавливаем это правило для нашего компонета
        gridBagLayout.setConstraints(buttonCancel, constraints);
        // Добавляем компонент
        getContentPane().add(buttonCancel);

        // Устанавливаем поведение формы при закрытии - не закрывать совсем.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Получаем размеры экрана
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        // А теперь просто помещаем его по центру, вычисляя координаты на основе полученной информации
        setBounds(((int) d.getWidth() - MoveGroupDialog.WIDTH) / 2, ((int) d.getHeight() - MoveGroupDialog.HEIGHT) / 2,
                MoveGroupDialog.WIDTH, MoveGroupDialog.HEIGHT);

    }

    public int getYear() {
        return ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
    }

    // Возврат группы, которая установлена на форме
    public Group getGroup() {
        if (groupList.getModel().getSize() > 0) {
            return (Group) groupList.getSelectedItem();
        }
        return null;
    }

    /*Отримання результату
    * true - при Ok, false - при Cancel
    * */
    public boolean getResult() {
        return result;
    }

    /* Обробка натискання кнопок
    * */
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }

}
