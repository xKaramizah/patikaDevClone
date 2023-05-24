package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGUI extends JFrame {
    private JButton btn_back;
    private JLabel txt_title;
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JScrollPane scrl_quiz_list;
    private JPanel pnl_right;
    private JTable tbl_quiz_list;
    private JTextArea fld_question;
    private JTextArea fld_option_a;
    private JTextArea fld_option_b;
    private JTextArea fld_option_c;
    private JTextArea fld_option_d;
    private JTextArea fld_option_e;
    private JComboBox cmb_answer;
    private JButton btn_add;
    private JButton btn_update;
    private JButton btn_delete;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;
    private int selected_row_id = -1;

    private final Content content;

    public QuizGUI(Content content) {
        this.content = content;
        add(wrapper);
        setTitle(Config.QUIZ_TITLE);
        setSize(550, 650);
        setMinimumSize(getSize());
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        mdl_quiz_list = new DefaultTableModel();
        Object[] quizColNames = {"ID", "Soru", "Ait olduğu başlık"};
        mdl_quiz_list.setColumnIdentifiers(quizColNames);
        row_quiz_list = new Object[quizColNames.length];
        loadQuizModel();
        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);
        tbl_quiz_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                if (!e.getValueIsAdjusting()) {
                    selected_row_id = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString());
                    fld_question.setText(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 1).toString());
                    Quiz quiz = Quiz.getFetch("SELECT * FROM quiz WHERE id = " + this.selected_row_id);
                    fld_option_a.setText(quiz.getOptionA());
                    fld_option_b.setText(quiz.getOptionB());
                    fld_option_c.setText(quiz.getOptionC());
                    fld_option_d.setText(quiz.getOptionD());
                    fld_option_e.setText(quiz.getOptionE());
                    String answer = quiz.getAnswer();
                    cmb_answer.setSelectedItem(answer);
                }
            } catch (Exception exception) {
            }
        });

        btn_back.addActionListener(e -> {
            dispose();
        });
        btn_update.addActionListener(e -> {
            if (this.selected_row_id == -1) {
                Helper.showMsg("Güncellenecek satırı seçiniz.");
            } else {
                if (Helper.confirm(this.selected_row_id + " ID no'lu soruyu güncellemek üzeresiniz.")) {
                    if (Helper.isFieldEmpty(fld_question) || Helper.isFieldEmpty(fld_option_a) || Helper.isFieldEmpty(fld_option_b)
                            || Helper.isFieldEmpty(fld_option_c) || Helper.isFieldEmpty(fld_option_d) || Helper.isFieldEmpty(fld_option_e)) {
                        Helper.showMsg("fill");
                    } else {
                        if (Quiz.update(this.selected_row_id, fld_question.getText(), fld_option_a.getText(), fld_option_b.getText(),
                                fld_option_c.getText(), fld_option_d.getText(), fld_option_e.getText(), cmb_answer.getSelectedItem().toString())) {
                            Helper.showMsg("done");

                            this.selected_row_id = -1;
                            fld_question.setText(null);
                            fld_option_a.setText(null);
                            fld_option_b.setText(null);
                            fld_option_c.setText(null);
                            fld_option_d.setText(null);
                            fld_option_e.setText(null);
                            loadQuizModel();
                        } else {
                            Helper.showMsg("error");
                        }
                    }
                }
            }
        });
        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question) || Helper.isFieldEmpty(fld_option_a) || Helper.isFieldEmpty(fld_option_b)
                    || Helper.isFieldEmpty(fld_option_c) || Helper.isFieldEmpty(fld_option_d) || Helper.isFieldEmpty(fld_option_e)) {
                Helper.showMsg("fill");
            } else {
                if (Quiz.add(fld_question.getText(), fld_option_a.getText(), fld_option_b.getText(),
                        fld_option_c.getText(), fld_option_d.getText(), fld_option_e.getText(), cmb_answer.getSelectedItem().toString(), this.content.getId())) {
                    Helper.showMsg("done");

                    this.selected_row_id = -1;
                    fld_question.setText(null);
                    fld_option_a.setText(null);
                    fld_option_b.setText(null);
                    fld_option_c.setText(null);
                    fld_option_d.setText(null);
                    fld_option_e.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
            loadQuizModel();
        });
        btn_delete.addActionListener(e -> {
            if (this.selected_row_id == -1) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    Quiz.delete(this.selected_row_id);
                    this.selected_row_id = -1;
                    fld_question.setText(null);
                    fld_option_a.setText(null);
                    fld_option_b.setText(null);
                    fld_option_c.setText(null);
                    fld_option_d.setText(null);
                    fld_option_e.setText(null);

                    Helper.showMsg("done");
                } else {
                    Helper.showMsg("error");
                }
            }
            loadQuizModel();
        });
    }

    private void loadQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz quiz : Quiz.getList("SELECT * FROM quiz WHERE content_id = " + content.getId())) {
            i = 0;
            row_quiz_list[i++] = quiz.getId();
            row_quiz_list[i++] = quiz.getQuestion();
            row_quiz_list[i++] = Content.getFetch(quiz.getContent().getId());
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }
}
