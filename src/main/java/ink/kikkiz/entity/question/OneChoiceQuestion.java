package ink.kikkiz.entity.question;

import java.util.Objects;

/**
 * @author KikkiZ
 */
public class OneChoiceQuestion extends Question {
    Choice answer;
    Choice rightAnswer;

    public Choice getAnswer() {
        return answer;
    }

    public void setAnswer(Choice answer) {
        this.answer = answer;
    }

    public Choice getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Choice rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "OneChoiceQuestion{" +
                "id=" + getId() +
                ",question=" + getQuestion() +
                ", choices=" + getChoices() +
                ", answer=" + answer +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
