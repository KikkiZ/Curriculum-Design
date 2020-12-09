package ink.kikkiz.entity.question;

/**
 * @author KikkiZ
 */
public abstract class Question {
    private int id;
    private String question;
    private String[] choices;

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

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Question question = (Question) obj;
        return id == question.id;
    }
}
