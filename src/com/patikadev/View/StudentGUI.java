package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JLabel txt_welcome;
    private JTabbedPane tab_student;
    private JPanel pnl_patika_list;
    private JPanel pnl_mainpage_paths;
    private JPanel pnl_mainpage;
    private JTable tbl_patika_list;
    private JLabel txt_avatar;
    private JPanel pnl_profile;
    private JButton btn_logout;
    private JButton btn_settings;
    private JPanel pnl_profile_top;
    private JPanel pnl_profile_bottom;
    private JLabel txt_profile_name;
    private JPanel pnl_top;
    private JPanel pnl_mainpage_courses;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

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

        mdl_patika_list = new DefaultTableModel();

        Object[] patikaColNames = {"Patika Adı", "Durumu", "Devam Et"};
        mdl_patika_list.setColumnIdentifiers(patikaColNames);
        row_patika_list = new Object[mdl_patika_list.getRowCount()];

        loadStudentPathsModel();
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);

        btn_settings.addActionListener(e -> {
            UserSettingsGUI userSettingsGUI = new UserSettingsGUI(this.student);
        });
        btn_logout.addActionListener(e -> {
            if (Helper.showConfirmDialog("sure")) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        btn_settings.addActionListener(e -> {
            UserSettingsGUI userSettingsGUI = new UserSettingsGUI(this.student);
        });
    }

    private void loadStudentPathsModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
    }

    private void createUIComponents() {
        txt_avatar = new JLabel();
        ImageIcon avatar = new ImageIcon(new ImageIcon("images/avatar_male.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        txt_avatar.setIcon(avatar);
    }
}
