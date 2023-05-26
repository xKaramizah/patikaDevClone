package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;

public class PatikaUpdateGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patika_update;
    private JButton btn_patika_update;
    private Patika patika;

    public PatikaUpdateGUI(Patika patika) {
        this.patika = patika;
        add(wrapper);
        setSize(300, 150);
        setTitle(Config.PROJECT_TITLE);
        setLocation(Helper.centerTheScreen("x", getSize()), Helper.centerTheScreen("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        fld_patika_update.setText(patika.getName());

        btn_patika_update.addActionListener(e -> {
            if (fld_patika_update.getText().trim().isEmpty()) {
                Helper.showMessageDialog("fill");
            } else {
                if (Patika.update(patika.getId(), fld_patika_update.getText())) {
                    Helper.showMessageDialog("done");
                } else {
                    Helper.showMessageDialog("error");
                }
                dispose();
            }
        });
    }
}
