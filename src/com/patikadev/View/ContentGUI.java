package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ContentGUI extends JFrame {

    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JButton btn_back;
    private JLabel txt_patika_name;
    private JLabel txt_course_name;
    private JLabel txt_lang_name;
    private JScrollPane scrl_content_list;
    private JTable tbl_content_list;
    private JTextField fld_title;
    private JTextArea fld_area_desc;
    private JTextArea fld_area_video;
    private JButton btn_content_add;
    private JButton btn_content_update;
    private JButton btn_quiz_add;
    private JButton btn_content_delete;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private int selected_row_id = -1;

    private final Educator educator;
    private final Course course;

    public ContentGUI(Educator educator, Course course) {
        this.educator = educator;
        this.course = course;
        add(wrapper);
        setTitle(Config.CONTENT_TITLE);
        setSize(1000, 600);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        mdl_content_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] contentColNames = {"ID", "Eğitim Başlığı", "Açıklama", "Video Link"};
        mdl_content_list.setColumnIdentifiers(contentColNames);
        row_content_list = new Object[contentColNames.length];
        loadContentModel();

        tbl_content_list.setModel(mdl_content_list);

        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(50);

        txt_patika_name.setText(course.getPatika().getName());
        txt_course_name.setText(course.getName());
        txt_lang_name.setText(course.getLang());

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                try {
                    this.selected_row_id = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
                    String title = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 1).toString();
                    String desc = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 2).toString();
                    String vid = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 3).toString();

                    fld_title.setText(title);
                    fld_area_desc.setText(desc);
                    fld_area_video.setText(vid);
                } catch (Exception exception) {
                }
            }
        });

        btn_back.addActionListener(e -> {
            dispose();
            EducatorGUI educatorGUI = new EducatorGUI(this.educator);
        });

        btn_content_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_title) || fld_area_desc.getText().isEmpty() || fld_area_video.getText().isEmpty()) {
                Helper.showMsg("fill");
            } else {
                if (Content.getFetch("SELECT * FROM content WHERE video_link = '" + fld_area_video.getText().trim() + "'") == null) {
                    if (Content.add(fld_title.getText(), fld_area_desc.getText(), fld_area_video.getText(), this.course.getId())) {
                        Helper.showMsg("done");
                        fld_title.setText(null);
                        fld_area_desc.setText(null);
                        fld_area_video.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                } else {
                    Helper.showMsg("Aynı video görseli tekrar kullanamazsınız!");
                }
            }
            loadContentModel();
        });
        btn_content_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_title) || fld_area_desc.getText().isEmpty() || fld_area_video.getText().isEmpty()) {
                Helper.showMsg("fill");
            } else {
                if (this.selected_row_id == -1) {
                    Helper.showMsg("Güncellemek istediğiniz çeriği seçiniz.");
                } else {
                    if (Content.update(this.selected_row_id, fld_title.getText(), fld_area_desc.getText(), fld_area_video.getText())) {
                        Helper.showMsg("done");
                        fld_area_video.setText(null);
                        fld_title.setText(null);
                        fld_area_desc.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
            loadContentModel();
            this.selected_row_id = -1;
        });
        btn_content_delete.addActionListener(e -> {
            if (this.selected_row_id != -1) {
                if (Content.delete(this.selected_row_id)) {
                    Helper.showMsg("done");
                } else {
                    Helper.showMsg("error");
                }
            } else {
                Helper.showMsg("Seçim yapınız.");
            }
            loadContentModel();
            this.selected_row_id = -1;
        });
        btn_quiz_add.addActionListener(e -> {
            if (this.selected_row_id == -1) {
                Helper.showMsg("Sınav düzenlemek istediğiniz dersi seçiniz.");
            } else {
                QuizGUI quizGUI = new QuizGUI(Content.getFetch(this.selected_row_id));
            }
        });
    }

    public void loadContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content content : Content.getList(this.course.getId())) {
            i = 0;
            row_content_list[i++] = content.getId();
            row_content_list[i++] = content.getTitle();
            row_content_list[i++] = content.getDescription();
            row_content_list[i++] = content.getVideo_link();
            mdl_content_list.addRow(row_content_list);
        }
    }
}
