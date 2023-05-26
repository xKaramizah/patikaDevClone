package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_user_list;
    private JButton btn_logout;
    private JPanel pnl_top;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JLabel lbl_user_add;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_src_user_name;
    private JTextField fld_src_user_uname;
    private JComboBox cmb_src_user_type;
    private JButton user_src;
    private JTable tbl_patika_list;
    private JScrollPane scrl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_patika_list;
    private JPanel pnl_course_list;
    private JPanel pnl_user_top;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JTextField fld_course_delete;
    private JButton btn_course_delete;
    private JButton btn_course_update;
    private JButton btn_user_settings;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(1000, 550);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldiniz : " + operator.getName());

        //UserList

        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] userListColNames = {"ID", "AD SOYAD", "KULLANICI ADI", "ŞİFRE", "K.TİPİ", "E-MAİL", "DOĞUM YILI", "ŞEHİR", "ÜLKE", "TEL NO"};
        mdl_user_list.setColumnIdentifiers(userListColNames);

        row_user_list = new Object[userListColNames.length];
        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        tbl_user_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                if (!e.getValueIsAdjusting()) {
                    String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                    fld_user_id.setText(selected_user_id);
                }
            } catch (Exception exception) {
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int userId = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String userName = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String userUname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String userPass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String userType = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();
                String userEmail = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 5).toString();
                int userBirth = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 6).toString());
                String userCity = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 7).toString();
                String userCountry = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 8).toString();
                String userPhone = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 9).toString();

                if (User.update(userId, userName, userUname, userPass, userType, userEmail, userBirth, userCity, userCountry, userPhone)) {
                    Helper.showMessageDialog("done");
                }
                loadUserModel();
                loadCourseModel();
            }
        });
        // ## UserList

        // PatikaList
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selected_row_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            PatikaUpdateGUI patikaUpdateGUI = new PatikaUpdateGUI(Patika.getFetch(selected_row_id));
            patikaUpdateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.showConfirmDialog("sure")) {
                int selected_row_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
                if (Patika.getFetch(selected_row_id) != null) {
                    if (Patika.delete(selected_row_id)) {
                        Helper.showMessageDialog("done");
                        loadPatikaModel();
                        loadCourseModel();
                    }
                } else {
                    Helper.showMessageDialog("error");
                }
            }
        });

        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] patikaListColNames = {"ID", "NAME"};
        mdl_patika_list.setColumnIdentifiers(patikaListColNames);
        row_patika_list = new Object[patikaListColNames.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedPoint = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selectedPoint, selectedPoint);
            }
        });
        // ## PatikaList

        // CourseList

        mdl_course_list = new DefaultTableModel();
        Object[] courseListColNames = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(courseListColNames);
        row_course_list = new Object[courseListColNames.length];
        loadCourseModel();

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(50);
        loadCourseCombo();
        loadEducatorCombo();

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                try {
                    int selectedRow = tbl_course_list.getSelectedRow();
                    if (selectedRow != -1) {
                        String selected_id = tbl_course_list.getValueAt(selectedRow, 0).toString();
                        String selected_name = tbl_course_list.getValueAt(selectedRow, 1).toString();
                        String selected_lang = tbl_course_list.getValueAt(selectedRow, 2).toString();
                        fld_course_delete.setText(selected_id);
                        fld_course_name.setText(selected_name);
                        fld_course_lang.setText(selected_lang);
                    }
                } catch (Exception exception) {
                }
            }
        });

        // ## CourseList

        btn_logout.addActionListener(e -> {
            if (Helper.showConfirmDialog("sure")) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)) {
                Helper.showMessageDialog("fill");
            } else {
                if (User.add(fld_user_name.getText(), fld_user_uname.getText(), fld_user_pass.getText(), cmb_user_type.getSelectedItem().toString())) {
                    Helper.showMessageDialog("done");
                    loadUserModel();
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)) {
                Helper.showMessageDialog("fill");
            } else {
                if (Helper.showConfirmDialog("sure")) {
                    if (User.delete(Integer.parseInt(fld_user_id.getText()))) {
                        Helper.showMessageDialog("done");
                    } else {
                        Helper.showMessageDialog("error");
                    }
                }
                loadUserModel();
                loadCourseModel();
            }
        });
        user_src.addActionListener(e -> {
            String name = fld_src_user_name.getText();
            String uname = fld_src_user_uname.getText();
            String type = cmb_src_user_type.getSelectedItem().toString();
            String query = User.searchQuery(name, uname, type);
            ArrayList<User> searchUsers = User.getList(query);
            loadUserModel(searchUsers);
        });

        btn_patika_add.addActionListener(e -> {
            if (fld_patika_name.getText().trim().isEmpty()) {
                Helper.showMessageDialog("fill");
            } else {
                String name = fld_patika_name.getText();
                if (Patika.add(name)) {
                    Helper.showMessageDialog("done");
                    fld_patika_name.setText(null);
                } else {
                    Helper.showMessageDialog("error");
                }
            }
            loadPatikaModel();
        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang)) {
                Helper.showMessageDialog("fill");
            } else {
                if (Course.add(userItem.getKey(), patikaItem.getKey(), fld_course_name.getText(), fld_course_lang.getText())) {
                    Helper.showMessageDialog("done");
                    loadCourseModel();
                    fld_course_name.setText(null);
                    fld_course_lang.setText(null);
                } else {
                    Helper.showMessageDialog("error");
                }
            }
        });
        btn_course_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_delete)) {
                Helper.showMessageDialog("fill");
            } else {
                if (Helper.showConfirmDialog("sure")) {
                    if (Course.delete(Integer.parseInt(fld_course_delete.getText()))) {
                        Helper.showMessageDialog("done");
                    } else {
                        Helper.showMessageDialog("error");
                    }
                }
            }
            loadCourseModel();
            fld_course_delete.setText(null);
        });
        btn_user_settings.addActionListener(e -> {
            UserSettingsGUI userSettingsGUI = new UserSettingsGUI(this.operator);
        });
    }

    public void loadUserModel() {
        loadEducatorCombo();
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User user : User.getList("SELECT * FROM user")) {
            i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUname();
            row_user_list[i++] = user.getPass();
            row_user_list[i++] = user.getType();
            row_user_list[i++] = user.getEmail();
            row_user_list[i++] = user.getBirth();
            row_user_list[i++] = user.getCity();
            row_user_list[i++] = user.getCountry();
            row_user_list[i++] = user.getPhone();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> list) {
        loadEducatorCombo();
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User user : list) {
            i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUname();
            row_user_list[i++] = user.getPass();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadPatikaModel() {
        loadCourseCombo();
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Patika patika : Patika.getList("SELECT * FROM patika")) {
            i = 0;
            row_patika_list[i++] = patika.getId();
            row_patika_list[i++] = patika.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course course : Course.getList("SELECT * FROM course")) {
            i = 0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getName();
            row_course_list[i++] = course.getLang();
            row_course_list[i++] = course.getPatika().getName();
            row_course_list[i++] = course.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadCourseCombo() {
        cmb_course_patika.removeAllItems();
        for (Patika patika : Patika.getList("SELECT * FROM patika")) {
            cmb_course_patika.addItem(new Item(patika.getId(), patika.getName()));
        }
    }

    private void loadEducatorCombo() {
        cmb_course_user.removeAllItems();
        for (User user : User.getList("SELECT * FROM user WHERE type = 'educator'")) {
            cmb_course_user.addItem(new Item(user.getId(), user.getName()));
        }
    }

}
