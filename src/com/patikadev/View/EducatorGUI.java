package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JLabel txt_welcome;
    private JButton btn_exit;
    private JButton btn_settings;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_bottom;
    private JTextField fld_patika_name;
    private JTextField fld_course_name;
    private JTextField fld_lang_name;
    private JButton btn_set_content;
    private JTextField fld_course_id;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final Educator educator;

    public EducatorGUI(Educator educator) {
        this.educator = educator;
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(750, 500);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        txt_welcome.setText("Hoş geldiniz: " + this.educator.getName());

        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] courseListColNames = {"ID", "Patika", "Ders Adı", "Programlama Dili"};
        mdl_course_list.setColumnIdentifiers(courseListColNames);
        row_course_list = new Object[courseListColNames.length];

        loadCourseModel();

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                if (!e.getValueIsAdjusting()) {
                    String selectedID = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString();
                    String selectedPatika = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 1).toString();
                    String selectedCourse = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 2).toString();
                    String selectedLang = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 3).toString();

                    fld_course_id.setText(selectedID);
                    fld_patika_name.setText(selectedPatika);
                    fld_course_name.setText(selectedCourse);
                    fld_lang_name.setText(selectedLang);
                }
            } catch (Exception exception) {
            }

        });

        btn_exit.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        btn_settings.addActionListener(e -> {
            UserSettingsGUI userSettingsGUI = new UserSettingsGUI(this.educator);
        });
        btn_set_content.addActionListener(e -> {
            if (!Helper.isFieldEmpty(fld_course_id) || !Helper.isFieldEmpty(fld_patika_name) || !Helper.isFieldEmpty(fld_course_name) || !Helper.isFieldEmpty(fld_lang_name)) {
                int selectedCourseID = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString());
                Course selectedCourse = Course.getFetch(selectedCourseID);
                dispose();
                ContentGUI contentGUI = new ContentGUI(this.educator, selectedCourse);
            }
        });

    }

    public void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        for (Course course : Course.getList("SELECT * FROM course WHERE user_id = "+ this.educator.getId())) {
            row_course_list[0] = course.getId();
            row_course_list[1] = course.getPatika().getName();
            row_course_list[2] = course.getName();
            row_course_list[3] = course.getLang();
            mdl_course_list.addRow(row_course_list);
        }
    }
}
