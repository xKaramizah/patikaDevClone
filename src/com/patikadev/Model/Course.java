package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private User educator;

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return this.patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Course> getList(String query) {
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id, user_id, patika_id, name, lang);
                courseList.add(obj);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static boolean add(int user_id, int patika_id, String name, String lang) {
        String query = "INSERT INTO course (user_id, patika_id, name, lang) VALUES (?,?,?,?)";
        boolean result = true;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, user_id);
            ps.setInt(2, patika_id);
            ps.setString(3, name);
            ps.setString(4, lang);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM course WHERE id = ?";
        boolean result;
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

    public static Course getFetch(int course_id) {
        Course obj = null;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement("SELECT * FROM course WHERE id = ?");
            ps.setInt(1, course_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id, user_id, patika_id, name, lang);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Course getFetch(String name) {
        String query = "SELECT * FROM course WHERE name = ?";
        Course obj = null;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String course_name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id, user_id, patika_id, course_name, lang);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
