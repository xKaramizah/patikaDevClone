package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;

import javax.swing.*;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JLabel txt_welcome;
    private JButton btn_exit;
    private JButton btn_settings;
    private JTabbedPane tab_student;
    private JPanel pnl_patika_list;
    private JPanel pnl_mainpage_courses;
    private JPanel pnl_mainpage;
    private JTable table1;
    private JLabel txt_avatar;

    public StudentGUI() {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(500, 500);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void createUIComponents() {
        txt_avatar = new JLabel(new ImageIcon("images/avatar_male.jpg"));
    }
}
