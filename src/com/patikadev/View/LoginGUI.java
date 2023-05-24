package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.*;


public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_uname;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private JLabel txt_logo;
    private JButton btn_reg;

    public LoginGUI() {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(350, 375);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_pass) || Helper.isFieldEmpty(fld_uname)) {
                Helper.showMsg("fill");
            } else {
                User user = User.getFetch(fld_uname.getText(), fld_pass.getText());
                if (user == null) {
                    Helper.showMsg("Kullanıcı bulunamadı.");
                } else {
                    String userType = user.getType();
                    switch (userType) {
                        case "operator" -> {
                            OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                        }
                        case "educator" -> {
                            EducatorGUI educatorGUI = new EducatorGUI((Educator) user);
                        }
                        case "student" -> {
                            StudentGUI studentGUI = new StudentGUI((Student) user);
                        }
                    }
                    dispose();
                }
            }
        });
        btn_reg.addActionListener(e -> {
            dispose();
            RegisterGUI registerGUI = new RegisterGUI();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }

    private void createUIComponents() {
        txt_logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("images/patika.png").getImage().getScaledInstance(250, 90, Image.SCALE_SMOOTH));
        txt_logo.setIcon(logoIcon);
    }
}
