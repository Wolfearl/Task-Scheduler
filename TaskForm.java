package com.task_scheduler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TaskForm extends JFrame {

    private int count = 1;
    Container container;
    GridBagConstraints constraints;
    List<JCheckBox> checkBoxes = new ArrayList<>();
    JPanel panel;

    public TaskForm (int width, int height) {

        super("Планировщик задач");
        super.setBounds(500, 150, width, height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = super.getContentPane();
        container.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.weighty = 0.0;
        constraints.gridx = 0;
        constraints.insets = new Insets(2, 2, 2, 2);

        JLabel text = new JLabel("Задачи");
        constraints.gridy = 0;
        constraints.ipady = 50;
        container.add(text, constraints);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(350, 250));
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        container.add(scrollPane, constraints);

        JButton button_add = new JButton("Добавить");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridy = 10;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.weighty = 0.0;
        container.add(button_add, constraints);

        JButton button_del = new JButton("Удалить");
        constraints.gridy = 10;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        container.add(button_del, constraints);

        JButton button_clear = new JButton("Очистить");
        constraints.gridy = 11;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        container.add(button_clear, constraints);


        button_add.addActionListener(new ButtonAddManager());
        button_del.addActionListener(new ButtonDelManager());
        button_clear.addActionListener(new ButtonClearManager());
    }

    class ButtonAddManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(TaskForm.this, "Введите задачу", true);
            dialog.setSize(300, 100);
            dialog.setLocationRelativeTo(TaskForm.this);
            dialog.setLayout(new FlowLayout());

            JTextField inputField = new JTextField(20);
            JButton send_Button = new JButton("Отправить");

            send_Button.addActionListener(ee -> {
                String text = inputField.getText();
                if (!text.trim().isEmpty()) {
                    JCheckBox new_task = new JCheckBox(text, false);
                    constraints.gridy = count;
                    count++;
                    checkBoxes.add(new_task);
                    new_task.setAlignmentX(Container.CENTER_ALIGNMENT);
                    panel.add(new_task, constraints);
                }
                dialog.dispose();
            });

            dialog.add(inputField);
            dialog.add(send_Button);
            dialog.setVisible(true);
            container.revalidate();
            container.repaint();
        }
    }

    class ButtonDelManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Iterator<JCheckBox> iterator = checkBoxes.iterator();
            while (iterator.hasNext()) {
                JCheckBox cb = iterator.next();
                boolean selected = cb.isSelected();
                if (selected) {
                    panel.remove(cb);
                    iterator.remove();
                }
            }
            container.revalidate();
            container.repaint();
        }
    }


    class ButtonClearManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Iterator<JCheckBox> iterator = checkBoxes.iterator();
            while (iterator.hasNext()) {
                JCheckBox cb = iterator.next();
                panel.remove(cb);
                iterator.remove();
            }
            container.revalidate();
            container.repaint();
        }
    }
}
