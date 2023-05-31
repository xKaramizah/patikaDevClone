package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatikaGUI extends JFrame {
    private JButton btn_back;
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_mid;
    private JPanel pnl_bottom;
    private JLabel txt_patika;
    private JButton btn_register;
    private JLabel txt_attend;
    private JScrollPane scrl_courses;
    private JTable tbl_courses;
    private DefaultTableModel mdl_courses;
    private Object[] row_courses;

    private final Student student;
    private final Patika patika;

    public PatikaGUI(Student student, Patika patika) {
        this.student = student;
        this.patika = patika;
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        txt_patika.setText(patika.getName() + " 'sına ait bilgiler");
        txt_attend.setText("Katılım durumu : ");

        // ------ Courses Model ------ //
        String[] coursesColNames = {"Dersin Adı"};
        mdl_courses = new DefaultTableModel(null, coursesColNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl_courses.setModel(mdl_courses);
        row_courses = new Object[mdl_courses.getColumnCount()];
        loadCoursesModel();

        // ## ------ Courses Model ------ //

        btn_back.addActionListener(e -> {
            dispose();
            new StudentGUI(this.student);
        });
        btn_register.addActionListener(e -> {
            if (Patika.register(this.student, this.patika)) {
                Helper.showMessageDialog("done");
            } else {
                Helper.showMessageDialog("Patikaya daha önceden kayıt oldunuz!");
            }
        });
    }

    private void loadCoursesModel() {
        mdl_courses.setRowCount(0);

        for (Course course : Course.getList("SELECT * FROM course WHERE patika_id = " + this.patika.getId())) {
            row_courses[0] = course.getName();
            mdl_courses.addRow(row_courses);
        }
    }
}
