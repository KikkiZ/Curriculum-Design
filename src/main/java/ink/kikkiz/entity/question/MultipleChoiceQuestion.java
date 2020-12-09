package ink.kikkiz.entity.question;

import java.util.EnumSet;

/**
 * @author KikkiZ
 */
public class MultipleChoiceQuestion extends Question {
    EnumSet<Choice> answers;
    EnumSet<Choice> rightAnswers;

    public EnumSet<Choice> getAnswers() {
        return answers;
    }

    public void setAnswers(EnumSet<Choice> answers) {
        this.answers = answers;
    }

    public EnumSet<Choice> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(EnumSet<Choice> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public void setRightAnswers(String answers) {
        EnumSet<Choice> rightAnswers = EnumSet.noneOf(Choice.class);
        for (Character answer : answers.toCharArray()) {
            rightAnswers.add(Choice.getChoice(answer.toString()));
        }
        this.rightAnswers = rightAnswers;
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "id=" + getId() +
                ",question=" + getQuestion() +
                ", choices=" + getChoices() +
                ", answers=" + answers +
                ", rightAnswers=" + rightAnswers +
                '}';
    }
}
