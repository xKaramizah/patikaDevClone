package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;
    private String email;
    private int birth;
    private String city;
    private String country;
    private String phone;

    public User() {
    }

    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCounty(String county) {
        this.country = county;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static ArrayList<User> getList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                obj.setEmail(rs.getString("email"));
                obj.setBirth(rs.getInt("birth"));
                obj.setCity(rs.getString("city"));
                obj.setCounty(rs.getString("country"));
                obj.setPhone(rs.getString("phone"));
                userList.add(obj);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static boolean add(String name, String uName, String pass, String type) {
        String query = "INSERT INTO user (name, uname, pass, type) VALUES (?,?,?,?)";
        User checkUname = User.getFetch(uName);
        if (checkUname != null) {
            Helper.showMsg("Kayıtlı bir kullanıcı adı girdiniz. Lütfen farklı bir kullanıcı adı seçiniz.");
            return false;
        } else if (type.equals("operator")) {
            Helper.showMsg("Operator ekleme yetkiniz bulunmuyor");
            return false;
        }
        boolean result = true;

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, uName);
            ps.setString(3, pass);
            ps.setString(4, type);
            result = ps.executeUpdate() != -1;
            if (!result) {
                Helper.showMsg("error");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        boolean result;
        ArrayList<Course> courseListForID = Course.getList("SELECT * FROM course");
        for (Course course : courseListForID) {
            if (course.getUser_id() == id) {
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

    public static boolean update(int id, String name, String uname, String pass, String type, String email, int birth, String city, String county, String phone) {
        boolean result;
        User checkUname = getFetch(uname);
        if (checkUname != null && id != checkUname.getId()) {
            Helper.showMsg("Kayıtlı bir kullanıcı adı girdiniz. Lütfen farklı bir kullanıcı adı seçiniz.");
            return false;
        } else if (!isValidUserType(type)) {
            Helper.showMsg("Geçerli bir kullanıcı tipi giriniz!");
            return false;
        } else {
            String query = "UPDATE user SET name = ?, uname = ?, pass = ?, type = ?, email = ?, birth = ?, city = ?, country = ?, phone = ? WHERE id = ?";
            try {
                PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, uname);
                ps.setString(3, pass);
                ps.setString(4, type);
                ps.setString(5, email);
                ps.setInt(6, birth);
                ps.setString(7, city);
                ps.setString(8, county);
                ps.setString(9, phone);
                ps.setInt(10, id);
                result = ps.executeUpdate() != -1;
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static User getFetch(String uname) {
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                obj.setEmail(rs.getString("email"));
                obj.setBirth(rs.getInt("birth"));
                obj.setCity(rs.getString("city"));
                obj.setCounty(rs.getString("country"));
                obj.setPhone(rs.getString("phone"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                obj.setEmail(rs.getString("email"));
                obj.setBirth(rs.getInt("birth"));
                obj.setCity(rs.getString("city"));
                obj.setCounty(rs.getString("country"));
                obj.setPhone(rs.getString("phone"));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static User getFetch(String uname, String pass) {
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ? AND pass = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                switch (rs.getString("type")) {
                    case "operator":
                        obj = new Operator();
                        break;
                    case "educator":
                        obj = new Educator();
                        break;
                    case "student":
                        obj = new Student();
                        break;
                    default:
                        obj = new User();
                        Helper.showMsg("Böyle bir type bulunmuyor. User olarak oluşturuldu.");
                        break;
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                obj.setEmail(rs.getString("email"));
                obj.setBirth(rs.getInt("birth"));
                obj.setCity(rs.getString("city"));
                obj.setCounty(rs.getString("country"));
                obj.setPhone(rs.getString("phone"));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean isValidUserType(String type) {
        String query = "SELECT DISTINCT type FROM user";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals(type)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static String searchQuery(String name, String uname, String type) {
        String query = "SELECT * FROM user WHERE name LIKE '%{{name}}%' AND uname LIKE '%{{uname}}%'";
        query = query.replace("{{name}}", name);
        query = query.replace("{{uname}}", uname);

        if (!type.isEmpty()) {
            query += " AND type LIKE '%{{type}}%'";
            query = query.replace("{{type}}", type);
        }
        return query;
    }
}

