package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_patika;
    private JTable tbl_patika_list;
    private JScrollPane scrl_pnl_list;
    private JButton btn_logout;
    private JLabel txt_welcome;
    private JTabbedPane tab_mainpage;
    private JPanel pnl_course;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    DefaultTableModel mdl_patika_list;
    Object[] row_patika_list;
    DefaultTableModel mdl_course_list;
    Object[] row_course_list;

    private final Student student;

    public StudentGUI(Student student) {
        this.student = student;
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        txt_welcome.setText("Hoş geldiniz : " + student.getName());

        // Patika Model

        Object[] patikaColNames = {"Patika Adı", "İlerleme Durumu", ""};
        mdl_patika_list = new DefaultTableModel(null, patikaColNames);
        tbl_patika_list.setModel(mdl_patika_list);
        row_patika_list = new Object[mdl_patika_list.getRowCount()];

        int progressColumnIndex = 1;
        tbl_patika_list.getColumnModel().getColumn(progressColumnIndex).setCellRenderer(new ProgressBarCellRenderer());
        tbl_patika_list.getColumnModel().getColumn(progressColumnIndex).setCellEditor(new ProgressBarCellEditor());

        loadStudentPatikaModel();
        // ## Patika Model

        // Course Model
        String[] courseColNames = {"Ders Adı", "İlerleme Durumu", ""};
        mdl_course_list = new DefaultTableModel(null, courseColNames);
        tbl_course_list.setModel(mdl_course_list);
        row_course_list = new Object[mdl_course_list.getRowCount()];

        tbl_course_list.getColumnModel().getColumn(progressColumnIndex).setCellRenderer(new ProgressBarCellRenderer());
        tbl_course_list.getColumnModel().getColumn(progressColumnIndex).setCellEditor(new ProgressBarCellEditor());

        loadStudentCourseModel();

        // ## Course Model

        btn_logout.addActionListener(e -> {
            if (Helper.showConfirmDialog("sure")) {
                dispose();
                new LoginGUI();
            }
        });
    }

    private class ProgressBarCellRenderer extends DefaultTableCellRenderer {
        private final JProgressBar progressBar;
        private final JPanel panel;

        public ProgressBarCellRenderer() {
            super();
            panel = new JPanel(new BorderLayout());
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);

            panel.add(progressBar, BorderLayout.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            int progressValue = Integer.parseInt(value.toString());
            progressBar.setValue(progressValue);
            return progressBar;
        }
    }

    private class ProgressBarCellEditor extends DefaultCellEditor {
        private final JProgressBar progressBar;

        public ProgressBarCellEditor() {
            super(new JCheckBox());
            setClickCountToStart(1);
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            int progressValue = Integer.parseInt(value.toString());
            progressBar.setValue(progressValue);
            return progressBar;
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return false;
        }
    }

    private void loadStudentPatikaModel() {
        mdl_patika_list.setRowCount(0);
        String query = "SELECT patika.name, student_paths.completed FROM student_paths " +
                "INNER JOIN patika ON student_paths.patika_id = patika.id " +
                "WHERE student_paths.student_id = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, this.student.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String patikaName = rs.getString("name");
                boolean isCompleted = rs.getBoolean("completed");
                String completionStatus = isCompleted ? "Tamamlandı" : "Devam Et";

                int progressValue = 25;
                mdl_patika_list.addRow(new Object[]{patikaName, progressValue, completionStatus});
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadStudentCourseModel() {
        mdl_course_list.setRowCount(0);
        String query = "SELECT course.name, student_courses.completed FROM student_courses " +
                "INNER JOIN course ON student_courses.course_id = course.id " +
                "WHERE student_id = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, this.student.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int progressValue = 75;
                String completionStatus = rs.getBoolean("completed") ? "TAMAMLANDI" : "Devam Et";
                mdl_course_list.addRow(new Object[]{rs.getString("name"), progressValue, completionStatus});
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
