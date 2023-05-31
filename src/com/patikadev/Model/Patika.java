package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika() {
    }

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika> getList(String query) {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Patika();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                patikaList.add(obj);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patikaList;
    }

    public static boolean add(String name) {
        String query = "INSERT INTO patika (name) VALUES (?)";
        Patika checkPatika = getFetch(name);
        if (checkPatika != null) {
            Helper.showMessageDialog("Kayıtlı bir patika adı girdiniz. Lütfen farklı bir seçim yapınız.");
            return false;
        }
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, name);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean update(int id, String name) {
        String query = "UPDATE patika SET name = ? WHERE id = ?";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM patika WHERE id = ?";
        boolean result;
        ArrayList<Course> courseListForID = Course.getList("SELECT * FROM course");
        for (Course course : courseListForID) {
            if (course.getPatika_id() == id) {
                Course.delete(course.getId());
            }
        }
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Patika getFetch(String name) {
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE name = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Patika();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Patika getFetch(int id) {
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE id = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Patika(rs.getInt("id"), rs.getString("name"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean register(Student student, Patika patika) {
        boolean result;
        Object obj = null;
        String query = "SELECT * FROM student_paths WHERE student_id = ? AND patika_id = ?";
        PreparedStatement ps;
        try {
            ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, student.getId());
            ps.setInt(2, patika.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = 1;
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (obj != null) {
            return false;
        } else {
            query = "INSERT INTO student_paths (student_id, patika_id, completed) VALUES (?,?,?)";
            try {
                ps = DBConnector.getInstance().prepareStatement(query);
                ps.setInt(1, student.getId());
                ps.setInt(2, patika.getId());
                ps.setInt(3, 0);
                result = ps.executeUpdate() != -1;
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
