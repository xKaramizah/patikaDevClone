package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuizGUI extends JFrame {
    private JButton btn_back;
    private JLabel txt_title;
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JScrollPane scrl_quiz_list;
    private JPanel pnl_right;
    private JTable tbl_quiz_list;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    private final Content content;

    public QuizGUI(Content content) {
        this.content = content;
        add(wrapper);
        setTitle(Config.QUIZ_TITLE);
        setSize(600, 400);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        mdl_quiz_list = new DefaultTableModel();
        Object[] quizColNames = {"ID", "Soru", "Ait olduğu başlık"};
        mdl_quiz_list.setColumnIdentifiers(quizColNames);
        row_quiz_list = new Object[quizColNames.length];
//        loadQuizModel();
        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);
    }

//    private void loadQuizModel() {
//        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
//        clearModel.setRowCount(0);
//        int i;
//        for (Quiz quiz : Quiz.getList()) {
//            i = 0;
//            row_quiz_list[i++] = quiz.getId();
//            row_quiz_list[i++] = quiz.getQuestion();
//
//        }
//    }
}
