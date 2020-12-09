package ink.kikkiz.entity.user;

import ink.kikkiz.entity.question.TestQuestions;

import java.util.Map;

/**
 * @author KikkiZ
 */
public class User {
    private int id;
    private String name;
    private String password;
    private Map<Integer, UserGrade> grades;
    private TestQuestions questions;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id,String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Integer, UserGrade> getGrades() {
        return grades;
    }

    public void setGrades(Map<Integer, UserGrade> grades) {
        this.grades = grades;
    }

    public TestQuestions getQuestions() {
        return questions;
    }

    public void setQuestions(TestQuestions questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
