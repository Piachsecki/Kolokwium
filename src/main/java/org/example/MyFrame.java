package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener{
    private JButton jButton;
    private MyPanel panel;

    public MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        panel = new MyPanel();
        jButton = new JButton("Change the activity");
        jButton.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        add(jButton, BorderLayout.SOUTH);
        setVisible(true);


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyFrame();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == jButton.getActionCommand()){
                panel.toggleMode();
            }
    }
}
