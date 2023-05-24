package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private String title;
    private String description;
    private String video_link;

    private final Course course;

    public Content(int id, String title, String description, String video_link, int course_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.video_link = video_link;
        this.course = Course.getFetch(course_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public Course getCourse() {
        return course;
    }

    public static ArrayList<Content> getList(int course_id) {
        String query = "SELECT * FROM content WHERE course_id = ?";
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, course_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt("id");
                String title1 = rs.getString("title");
                String desc = rs.getString("description");
                String videoLink = rs.getString("video_link");
                int courseId = rs.getInt("course_id");
                obj = new Content(id1, title1, desc, videoLink, courseId);
                contentList.add(obj);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static boolean add(String title, String description, String video_link, int course_id) {
        String query = "INSERT INTO content (title, description, video_link, course_id) VALUES (?,?,?,?)";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, video_link);
            ps.setInt(4, course_id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean update(int id, String title, String description, String video_link) {
        String query = "UPDATE content SET title = ?, description = ?, video_link = ? WHERE id = ?";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, video_link);
            ps.setInt(4, id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM content WHERE id = ?";
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

    public static Content getFetch(int id) {
        Content obj = null;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM content WHERE id = " + id);
            if (rs.next()) {
                int id_id = rs.getInt("id");
                String title = rs.getNString("title");
                String description = rs.getNString("description");
                String video_link = rs.getNString("video_link");
                int course_id = rs.getInt("course_id");
                obj = new Content(id_id, title, description, video_link, course_id);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Content getFetch(String query) {
        Content obj = null;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                int id_id = rs.getInt("id");
                String title = rs.getNString("title");
                String description = rs.getNString("description");
                String video_link = rs.getNString("video_link");
                int course_id = rs.getInt("course_id");
                obj = new Content(id_id, title, description, video_link, course_id);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
