package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String answer;

    private final Content content;

    public Quiz(int id, String question, String optionA, String optionB, String optionC, String optionD, String optionE, String answer, int content_id) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.answer = answer;
        this.content = Content.getFetch(content_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Content getContent() {
        return content;
    }

    public static ArrayList<Quiz> getList(String query) {
        ArrayList<Quiz> quizList = new ArrayList<>();
        Quiz obj;
        try {
            Connection connection = DBConnector.getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                obj = new Quiz(
                        resultSet.getInt("id"),
                        resultSet.getString("question"),
                        resultSet.getString("optiona"),
                        resultSet.getString("optionb"),
                        resultSet.getString("optionc"),
                        resultSet.getString("optiond"),
                        resultSet.getString("optione"),
                        resultSet.getString("answer"),
                        resultSet.getInt("content_id")
                );
                quizList.add(obj);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizList;
    }

    public static Quiz getFetch(int quiz_id) {
        Quiz obj = null;
        String query = "SELECT * FROM quiz WHERE id = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, quiz_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Quiz(rs.getInt("id"), rs.getString("question"),
                        rs.getString("optiona"), rs.getString("optionb"),
                        rs.getString("optionc"), rs.getString("optiond"),
                        rs.getString("optione"), rs.getString("answer"),
                        rs.getInt("content_id"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
