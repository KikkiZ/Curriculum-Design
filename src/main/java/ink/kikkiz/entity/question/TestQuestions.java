package ink.kikkiz.entity.question;

import ink.kikkiz.client.Client;
import ink.kikkiz.service.QuestionService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public class TestQuestions {
    private int amount;
    private int oneChoiceAmount;
    private int multipleChoiceAmount;
    private TreeMap<Integer, OneChoiceQuestion> oneChoiceQuestionTreeMap;
    private TreeMap<Integer, MultipleChoiceQuestion> multipleChoiceQuestionTreeMap;

    Random random = new Random();

    /**
     * 设置总题目数量，单选题和多选题题数随机
     *
     * @param amount 总题目数量
     */
    public TestQuestions(int amount) {
        this(amount, 0, 0);
    }

    /**
     * 主动设置试卷出现的单选题和多选题的比例（数量）
     * 只做单选题或只做多选题时，另一类题目的数量设为零
     *
     * @param amount 总题目数量
     * @param oneChoiceAmount 单选题数量
     * @param multipleChoiceAmount 多选题数量
     */
    public TestQuestions(int amount, int oneChoiceAmount, int multipleChoiceAmount) {
        if (oneChoiceAmount == 0 && multipleChoiceAmount == 0) {
            oneChoiceAmount = random.nextInt(amount + 1);
            multipleChoiceAmount = amount - oneChoiceAmount;
        }
        this.amount = amount;
        this.oneChoiceAmount = oneChoiceAmount;
        this.multipleChoiceAmount = multipleChoiceAmount;

        oneChoiceQuestionTreeMap = (TreeMap<Integer, OneChoiceQuestion>) randomDrawing(oneChoiceAmount, Client.getOneService());
        multipleChoiceQuestionTreeMap = (TreeMap<Integer, MultipleChoiceQuestion>) randomDrawing(multipleChoiceAmount, Client.getMultiService());
    }

    public TreeMap<Integer, OneChoiceQuestion> getOneChoiceQuestionTreeMap() {
        return oneChoiceQuestionTreeMap;
    }

    public void setOneChoiceQuestionTreeMap(TreeMap<Integer, OneChoiceQuestion> oneChoiceQuestionTreeMap) {
        this.oneChoiceQuestionTreeMap = oneChoiceQuestionTreeMap;
    }

    public TreeMap<Integer, MultipleChoiceQuestion> getMultipleChoiceQuestionTreeMap() {
        return multipleChoiceQuestionTreeMap;
    }

    public void setMultipleChoiceQuestionTreeMap(TreeMap<Integer, MultipleChoiceQuestion> multipleChoiceQuestionTreeMap) {
        this.multipleChoiceQuestionTreeMap = multipleChoiceQuestionTreeMap;
    }

    public int getAmount() {
        return amount;
    }

    public int getOneChoiceAmount() {
        return oneChoiceAmount;
    }

    public int getMultipleChoiceAmount() {
        return multipleChoiceAmount;
    }

    //随机获取题目
    private TreeMap<Integer, ? extends Question> randomDrawing(int amount, QuestionService service) {
        LinkedHashSet<Integer> ids = new LinkedHashSet<>();
        final int MAX = service.getQuestionCount();
        while (ids.size() < amount) {
            ids.add(random.nextInt(MAX) + 1);
        }
        return service.getQuestionList(ids);
    }
}
