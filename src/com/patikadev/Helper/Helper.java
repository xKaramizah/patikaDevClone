package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper {

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equalsIgnoreCase("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static int centerTheScreen(String axis, Dimension dimension) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - dimension.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - dimension.height) / 2;
            default -> 0;
        };
    }

    public static boolean isFieldEmpty(JTextField jTextField) {
        return jTextField.getText().trim().isEmpty();
    }

    public static boolean isFieldEmpty(JTextArea jTextArea) {
        return jTextArea.getText().trim().isEmpty();
    }
    public static boolean isFieldEmpty(JPasswordField jPasswordField) {
        return jPasswordField.getText().isEmpty();
    }

    public static void showMsg(String text) {
        optionPaneToTR();
        String msg, title;
        switch (text) {
            case "fill" -> {
                msg = "Lütfen tüm alanları doldurunuz.";
                title = "Hata!";
            }
            case "done" -> {
                msg = "İşlem başarılı.";
                title = "Sonuç";
            }
            case "error" -> {
                msg = "Bir hata oluştu!";
                title = "Hata!";
            }
            default -> {
                msg = text;
                title = "Mesaj";
            }
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String dialog) {
        String msg, title;
        switch (dialog) {
            case "sure":
                msg = "Bu işlemi geçekleştirmek istediğinize emin misiniz?";
                title = "Emin misin?";
                break;
            default:
                msg = dialog;
                title = "Uyarı!";
                break;
        }
        return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPaneToTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }


    public static boolean sqlUpdate(String query) {
        boolean result;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            result = st.executeUpdate(query) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
