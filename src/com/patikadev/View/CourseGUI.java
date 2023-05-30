package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Model.Course;
import com.patikadev.Model.Student;

import javax.swing.*;

public class CourseGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_mid;
    private JPanel pnl_bottom;
    private JButton geriDönButton;
    private JPanel pnl_mid_left;
    private JPanel pnl_mid_right;

    private final Student student;
    private final Course course;

    public CourseGUI(Student student, Course course) {
        this.student = student;
        this.course = course;

        add(wrapper);
        setTitle(Config.COURSE_TITLE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
