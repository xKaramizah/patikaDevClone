package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JLabel txt_welcome;
    private JTabbedPane tab_student;
    private JPanel pnl_patika_list;
    private JPanel pnl_mainpage_courses;
    private JPanel pnl_mainpage;
    private JTable table1;
    private JLabel txt_avatar;
    private JPanel pnl_profile;
    private JButton btn_logout;
    private JButton btn_settings;

    public final Student student;

    public StudentGUI(Student student) {
        this.student = student;
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(500, 500);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        txt_welcome.setText("Hoşgeldiniz : " + this.student.getName());


        btn_settings.addActionListener(e -> {
            UserSettingsGUI userSettingsGUI = new UserSettingsGUI(this.student);
        });
        btn_logout.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        btn_settings.addActionListener(e -> {
            UserSettingsGUI userSettingsGUI = new UserSettingsGUI(this.student);
        });
    }

    private void createUIComponents() {
        txt_avatar = new JLabel();
        ImageIcon avatar = new ImageIcon(new ImageIcon("images/avatar_male.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        txt_avatar.setIcon(avatar);
    }
}
