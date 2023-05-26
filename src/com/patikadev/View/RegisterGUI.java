package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegisterGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_reg_name;
    private JTextField fld_reg_uname;
    private JPasswordField fld_reg_pass;
    private JButton btn_reg_send;
    private JButton btn_reg_cancel;

    public RegisterGUI() {
        Helper.setLayout();
        add(wrapper);
        setTitle(Config.REGISTER_TITLE);
        setSize(350, 250);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        fld_reg_name.setForeground(Color.GRAY);
        fld_reg_uname.setForeground(Color.GRAY);
        fld_reg_pass.setForeground(Color.GRAY);

        fld_reg_pass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (fld_reg_pass.getText().equals("******")) {
                    fld_reg_pass.setText("");
                    fld_reg_pass.setForeground(Color.BLACK);
                }
            }
        });

        fld_reg_pass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (fld_reg_pass.getText().equals("")) {
                    fld_reg_pass.setText("******");
                    fld_reg_pass.setForeground(Color.GRAY);
                }
            }
        });

        btn_reg_send.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_reg_name) || Helper.isFieldEmpty(fld_reg_uname) || Helper.isFieldEmpty(fld_reg_pass) || fld_reg_pass.getText().equals("******")) {
                Helper.showMessageDialog("fill");
            } else {
                if (User.add(fld_reg_name.getText(), fld_reg_uname.getText(), fld_reg_pass.getText(), "student")) {
                    Helper.showMessageDialog("İşlem başarılı. Giriş ekranına yönlendiriliyorsunuz");
                }
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        btn_reg_cancel.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
    }

    public static void main(String[] args) {
        RegisterGUI registerGUI = new RegisterGUI();
    }
}
