package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserSettingsGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JButton btn_back;
    private JLabel txt_name;
    private JPanel pnl_center;
    private JPanel pnl_bottom;
    private JPanel pnl_center_left;
    private JPanel pnl_center_right;
    private JTextField fld_set_name;
    private JTextField fld_set_uname;
    private JPasswordField fld_set_pass;
    private JComboBox cmb_set_birth;
    private JComboBox cmb_set_city;
    private JComboBox cmb_set_country;
    private JPasswordField fld_set_pass_again;
    private JTextField fld_set_email;
    private JTextField fld_set_email_again;
    private JTextField fld_set_phone;
    private JButton btn_set_send;
    private JButton btn_set_cancel;
    private final User user;

    public UserSettingsGUI(User user) {
        this.user = user;
        add(wrapper);
        setTitle(Config.USER_SETTING_TITLE);
        setSize(550, 350);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        txt_name.setText("Profilinizi güncelleyin : " + user.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        loadUserSettings();

        btn_set_send.addActionListener(e -> {
            int count = 0;
            if (!Helper.isFieldEmpty(fld_set_name)) {
                if (user.getName().equals(fld_set_name.getText())) {
                    Helper.sqlUpdate("UPDATE user SET name = '" + fld_set_name.getText() + "' WHERE id = " + user.getId());
                    count++;
                }
            }
            if (!Helper.isFieldEmpty(fld_set_uname)) {
                if (user.getUname().equals(fld_set_uname.getText())) {
                    Helper.sqlUpdate("UPDATE user SET uname = '" + fld_set_uname.getText() + "' WHERE id = " + user.getId());
                    count++;
                }
            }
            if (!fld_set_pass.getText().equals(fld_set_pass_again.getText())) {
                Helper.showMsg("Şifreler uyumsuz.");
            } else if (!Helper.isFieldEmpty(fld_set_pass) || user.getPass().equals(fld_set_pass.getText())) {
                Helper.sqlUpdate("UPDATE user SET pass = '" + fld_set_pass.getText() + "' WHERE id = " + user.getId());
                count++;
            }
            if (!fld_set_email.getText().equals(fld_set_email_again.getText())) {
                Helper.showMsg("Mailler uyumsuz.");
            } else if (!Helper.isFieldEmpty(fld_set_email) || user.getPass().equals(fld_set_email.getText())) {
                Helper.sqlUpdate("UPDATE user SET email = '" + fld_set_email.getText() + "' WHERE id = " + user.getId());
                count++;
            }
            if (cmb_set_birth.getSelectedItem() != null && !cmb_set_birth.getSelectedItem().toString().isEmpty()) {
                int sBirth = Integer.parseInt(cmb_set_birth.getSelectedItem().toString());
                String query = "UPDATE user SET birth = " + sBirth + " WHERE id = " + user.getId();
                if (Helper.sqlUpdate(query)) {
                    count++;
                }
            }
            if (cmb_set_city.getSelectedItem() != null && !cmb_set_city.getSelectedItem().toString().isEmpty()) {
                Helper.sqlUpdate("UPDATE user SET city = '" + cmb_set_city.getSelectedItem().toString() + "' WHERE id =" + user.getId());
                count++;
            }
            if (cmb_set_country.getSelectedItem() != null && !cmb_set_country.getSelectedItem().toString().isEmpty()) {
                Helper.sqlUpdate("UPDATE user SET country = '" + cmb_set_country.getSelectedItem().toString() + "' WHERE id =" + user.getId());
                count++;
            }
            if (!Helper.isFieldEmpty(fld_set_phone)) {
                if (!user.getPhone().equals(fld_set_phone.getText())) {
                    Helper.sqlUpdate("UPDATE user SET phone = '" + fld_set_phone.getText() + "' WHERE id = " + user.getId());
                    count++;
                }
            }
            if (count == 0) {
                Helper.showMsg("Herhangi bir değişiklik yapılmadı.");
            } else {
                Helper.showMsg("done");
                loadUserSettings();
            }
        });
        btn_back.addActionListener(e -> {
            dispose();
        });
        btn_set_cancel.addActionListener(e -> dispose());
    }

    public void loadUserSettings() {
        fld_set_name.setText(user.getName());
        fld_set_uname.setText(user.getUname());
        fld_set_pass.setText(user.getPass());
        fld_set_pass_again.setText(user.getPass());
        fld_set_email.setText(user.getEmail());
        fld_set_email_again.setText(user.getEmail());
        String selectedCountry = user.getCountry();
        cmb_set_country.setSelectedItem(selectedCountry);
        String selectedCity = user.getCity();
        cmb_set_city.setSelectedItem(selectedCity);
        int selectedBirth = user.getBirth();
        cmb_set_birth.setSelectedItem(String.valueOf(selectedBirth));
        fld_set_phone.setText(user.getPhone());
    }
}
