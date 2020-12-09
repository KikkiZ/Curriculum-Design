package ink.kikkiz.entity.user;

/**
 * @author KikkiZ
 */
public class UserGrade {
    private int id;
    private int times;
    private float grade;
    private int correctNumber;
    private int questionNumber;
    private int oneChoiceNumber;
    private int multipleChoiceNumber;

    public UserGrade() {}

    public UserGrade(int id, int times, float grade, int correctNumber, int questionNumber, int oneChoiceNumber, int multipleChoiceNumber) {
        this.id = id;
        this.times = times;
        this.grade = grade;
        this.correctNumber = correctNumber;
        this.questionNumber = questionNumber;
        this.oneChoiceNumber = oneChoiceNumber;
        this.multipleChoiceNumber = multipleChoiceNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getOneChoiceNumber() {
        return oneChoiceNumber;
    }

    public void setOneChoiceNumber(int oneChoiceNumber) {
        this.oneChoiceNumber = oneChoiceNumber;
    }

    public int getMultipleChoiceNumber() {
        return multipleChoiceNumber;
    }

    public void setMultipleChoiceNumber(int multipleChoiceNumber) {
        this.multipleChoiceNumber = multipleChoiceNumber;
    }

    @Override
    public String toString() {
        return "UserGrade{" +
                "id=" + id +
                ", times=" + times +
                ", grade=" + grade +
                ", correctNumber=" + correctNumber +
                ", questionNumber=" + questionNumber +
                ", oneChoiceNumber=" + oneChoiceNumber +
                ", multipleChoiceNumber=" + multipleChoiceNumber +
                '}';
    }
}
