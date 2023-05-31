package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Student;

import javax.swing.*;
import java.awt.*;

public class CourseGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_mid;
    private JPanel pnl_bottom;
    private JButton btn_back;
    private JLabel txt_exp;
    private JPanel pnl_mid_left;
    private JPanel pnl_mid_right;
    private JList list_content;
    private JScrollPane scrl_list;
    private JPanel pnl_content;
    private DefaultListModel<String> mdl_list_content;

    private final Student student;
    private final Course course;

    public CourseGUI(Student student, Course course) {
        this.student = student;
        this.course = course;

        add(wrapper);
        setTitle(Config.COURSE_TITLE);
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        txt_exp.setText(course.getName() + " ders içerikleri");

        mdl_list_content = new DefaultListModel<>();
        list_content.setModel(mdl_list_content);
        list_content.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        fillContentList();

        list_content.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedOne = list_content.getSelectedValue().toString();
                Content selectedContent = Content.getFetch("SELECT * FROM content WHERE title = '" + selectedOne + "'");
                updateContentPanel(selectedContent);
            }
        });

        btn_back.addActionListener(e -> {
            dispose();
            new StudentGUI(this.student);
        });
    }

    private void updateContentPanel(Content selectedContent) {
        pnl_mid_right.removeAll();

        // İçerik paneli için gerekli bileşenleri oluşturun
        pnl_content = new JPanel();
        pnl_content.setLayout(new GridLayout(4, 1));
        JLabel lbl_title = new JLabel("İçerik Başlığı : " + selectedContent.getTitle());
        JLabel lbl_video = new JLabel("Video: https://www.youtube.com/watch?v=" + selectedContent.getVideo_link());
        JLabel lbl_comments = new JLabel("Yorumlar: ");
        JLabel lbl_rating = new JLabel("Puan: ");

        // İçerik paneline bileşenleri ekleyin
        pnl_content.add(lbl_title);
        pnl_content.add(lbl_video);
        pnl_content.add(lbl_comments);
        pnl_content.add(lbl_rating);

        pnl_mid_right.add(pnl_content);

        pnl_mid_right.revalidate();
        pnl_mid_right.repaint();
    }

    private void fillContentList() {
        mdl_list_content.clear();
        for (Content content : Content.getList(this.course.getId())) {
            mdl_list_content.addElement(content.getTitle());
        }
    }
}
