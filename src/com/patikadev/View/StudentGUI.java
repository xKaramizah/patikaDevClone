package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;

import javax.swing.*;

public class StudentGUI extends JFrame {
    private JPanel wrapper;

    public StudentGUI() {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(500, 500);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
