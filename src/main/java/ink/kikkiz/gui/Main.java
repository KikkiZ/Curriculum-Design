package ink.kikkiz.gui;

import ink.kikkiz.client.Client;
import ink.kikkiz.entity.question.*;
import ink.kikkiz.entity.user.User;
import ink.kikkiz.entity.user.UserGrade;
import ink.kikkiz.gui.factory.ButtonFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author KikkiZ 唐洋
 */
public class Main implements IScene {
    private Question thisQuestion;
    private int questionNum, minute, second;
    //判断当前是否正在考试中
    private boolean check = false;

    private UserGrade grade;

    //问题
    private final Label question = new Label();

    //单选选项
    private final RadioButton[] single = new RadioButton[4];

    //多选选项
    private final CheckBox[] multiple = new CheckBox[4];

    private final Text answer = new Text();
    private final Text time = new Text();

    private final Button prevQuestion = new Button("上一题");
    private final Button nextQuestion = new Button("下一题");

    private Timeline timeline;

    //按钮工厂
    private final ButtonFactory factory = new ButtonFactory();

    //左侧选择区
    private final FlowPane selectPane = new FlowPane();

    //答题界面
    private final VBox questionPane = new VBox();

    public Scene getScene() {
        //菜单
        MenuBar menuBar = new MenuBar();

        MenuItem newExamination = new MenuItem("新建考试(N)");
        MenuItem viewGrades = new MenuItem("查看成绩(G)");
        MenuItem addQuestion = new MenuItem("增加题目(A)");
        MenuItem logout = new MenuItem("退出登录(L)");
        MenuItem quit = new MenuItem("退出(Q)");
        Menu menu = new Menu("主页");
        menu.getItems().addAll(newExamination, addQuestion, viewGrades, new SeparatorMenuItem(), logout, new SeparatorMenuItem(), quit);

        //将菜单的宽度设为顶格
        menuBar.prefWidthProperty().bind(Client.getStage().widthProperty());

        menuBar.getMenus().addAll(menu);

        //菜单设置快捷键
        newExamination.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        viewGrades.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN));
        addQuestion.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
        logout.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN));

        //给菜单设置事件
        newExamination.setOnAction(event -> newExaminationMethod());
        viewGrades.setOnAction(event -> viewGradesMethod());
        addQuestion.setOnAction(event -> addQuestionMethod());
        logout.setOnAction(event -> logoutMethod());
        quit.setOnAction(event -> {
            Client.close();
            System.exit(0);
        });

        //左侧选题栏
        selectPane.setPadding(new Insets(10,10,10,10));
        selectPane.setHgap(5);
        selectPane.setVgap(5);
        selectPane.setLayoutX(0);
        selectPane.setLayoutY(20);
        selectPane.setPrefWrapLength(200);

        //提交
        Button submit = new Button("提交");
        submit.getStyleClass().add("base-button");
        submit.setPrefSize(180,50);
        submit.setLayoutX(10);
        submit.setLayoutY(640);

        submit.setOnAction(actionEvent -> {
            Stage caution = new Stage();

            Text text = new Text("是否确认提交试卷?");
            text.setFont(new Font(14));
            text.setLayoutX(50);
            text.setLayoutY(45);

            Button sure = new Button("提交");
            sure.setPrefSize(120, 30);
            sure.setLayoutX(50);
            sure.setLayoutY(80);
            sure.setOnAction(event -> {
                submitMethod();
                caution.close();
            });

            Button cancel = new Button("取消");
            cancel.setPrefSize(120, 30);
            cancel.setLayoutX(230);
            cancel.setLayoutY(80);
            cancel.setOnAction(event -> caution.close());

            Pane pane = new Pane(text, sure, cancel);
            pane.setPrefSize(400, 130);

            caution.setTitle("警告");
            caution.setScene(new Scene(pane));
            caution.setAlwaysOnTop(true);
            caution.setResizable(false);
            caution.getIcons().add(new Image("file:src/main/resources/image/icon.png"));
            caution.show();
        });

        questionPane.setPrefSize(1000,620);
        questionPane.setLayoutX(200);
        questionPane.setLayoutY(40);
        questionPane.setPadding(new Insets(20,20,20,20));
        questionPane.setSpacing(20);

        question.setText("");
        question.setFont(new Font(22));
        question.setPrefSize(1000,200);
        question.setWrapText(true);
        question.setStyle("-fx-border-radius: 10px; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: #dedede; -fx-padding: 10px");

        answer.setFont(new Font(16));
        answer.setFill(Color.rgb(120, 120, 120));

        //前一题/后一题按钮
        prevQuestion.getStyleClass().add("base-button");
        prevQuestion.setPrefSize(150,50);
        prevQuestion.setLayoutX(210);
        prevQuestion.setLayoutY(640);

        nextQuestion.getStyleClass().add("base-button");
        nextQuestion.setPrefSize(150,50);
        nextQuestion.setLayoutX(1040);
        nextQuestion.setLayoutY(640);

        //上一题
        prevQuestion.setOnAction(actionEvent -> {
            //单选情况下
            if (thisQuestion instanceof OneChoiceQuestion && questionNum != 1) {
                questionNum -= 1;
                oneChoicePaneSet();
                showAnswer();
            }

            //多选情况下
            if (thisQuestion instanceof MultipleChoiceQuestion && questionNum != 1){
                questionNum -= 1;
                multiChoicePaneSet();
                showAnswer();
            }
        });

        //下一题
        nextQuestion.setOnAction(actionEvent -> {
            //单选情况下
            if (thisQuestion instanceof OneChoiceQuestion && questionNum != Client.getTestQuestions().getOneChoiceAmount()) {
                questionNum += 1;
                oneChoicePaneSet();
                showAnswer();
            }
            //多选情况下
            if (thisQuestion instanceof MultipleChoiceQuestion && questionNum != Client.getTestQuestions().getMultipleChoiceAmount()){
                questionNum += 1;
                multiChoicePaneSet();
                showAnswer();
            }
        });

        time.setFont(new Font(20));
        time.setFill(Color.GREEN);
        time.setLayoutX(665);
        time.setLayoutY(675);

        timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            second--;
            if (second < 0 && minute != 0) {
                second = 59;
                minute--;
            } else if (second <= 0 && minute == 0) {
                submitMethod();
            }

            if (second >= 10) {
                time.setText(minute + ":" + second);
            } else {
                time.setText(minute + ":0" + second);
            }
        }));

        Pane pane = new Pane();
        pane.getChildren().addAll(submit, selectPane, questionPane, nextQuestion, prevQuestion, menuBar, time);
        pane.setPrefSize(1200,700);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/style.css");
        Client.getStage().setTitle("考试界面");

        Client.getStage().setOnCloseRequest(event -> Client.close());

        return scene;
    }

    //开始考试
    private void newExaminationMethod() {
        check = false;

        if (timeline != null) {
            timeline.pause();
            time.setText("");
        }

        //成绩对象
        grade = new UserGrade();
        grade.setId(Client.getUser().getId());
        grade.setTimes(Client.getUser().getGrades().size() + 1);

        //新建试卷
        Text singleText = new Text("单选题个数");
        singleText.setStyle("-fx-font-size: 14px;");
        singleText.setLayoutX(60);
        singleText.setLayoutY(69);

        Text multipleText = new Text("多选题个数");
        multipleText.setStyle("-fx-font-size: 14px;");
        multipleText.setLayoutX(60);
        multipleText.setLayoutY(119);

        //单选题输入框
        TextField single = new TextField();
        single.setPrefSize(200,30);
        single.setLayoutX(140);
        single.setLayoutY(50);

        //多选题输入框
        TextField multiple = new TextField();
        multiple.setPrefSize(200,30);
        multiple.setLayoutX(140);
        multiple.setLayoutY(100);

        Button ensure = new Button("确定");
        ensure.setStyle("-fx-font-size: 14px");
        ensure.setPrefSize(130,40);
        ensure.setLayoutX(60);
        ensure.setLayoutY(160);

        Button quit = new Button("退出");
        quit.setStyle("-fx-font-size: 14px");
        quit.setPrefSize(130,40);
        quit.setLayoutX(210);
        quit.setLayoutY(160);

        Text caution = new Text();
        caution.setStyle("-fx-font-size: 14px;");
        caution.setFill(Color.RED);
        caution.setLayoutX(60);
        caution.setLayoutY(149);

        //清除上次测试留下的按钮和答题界面
        selectPane.getChildren().clear();
        questionPane.getChildren().clear();

        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:src/main/resources/image/icon.png"));

        //给按钮添加事件
        quit.setOnAction(event -> stage.close());

        ensure.setOnAction(event -> {
            //单选题和多选题题数
            int oneChoiceAmount = 0, multipleChoiceAmount = 0;

            //获取单选题题数
            if(!single.getText().equals("")){
                oneChoiceAmount = Integer.parseInt(single.getText());
            }
            //获取多选题题数
            if(!multiple.getText().equals("")){
                multipleChoiceAmount = Integer.parseInt(multiple.getText());
            }

            if (oneChoiceAmount > Client.getOneService().getQuestionCount()) {
                caution.setText("单选题题数超过上限，最大可填：" + Client.getOneService().getQuestionCount());
                oneChoiceAmount = multipleChoiceAmount = 0;
            } else if (multipleChoiceAmount > Client.getMultiService().getQuestionCount()) {
                caution.setText("多选题题数超过上限，最大可填：" + Client.getMultiService().getQuestionCount());
                oneChoiceAmount = multipleChoiceAmount = 0;
            } else if (oneChoiceAmount == 0 && multipleChoiceAmount == 0) {
                caution.setText("请输入至少一个数");
            }

            //获取考题
            Client.setTestQuestions(new TestQuestions(oneChoiceAmount + multipleChoiceAmount, oneChoiceAmount, multipleChoiceAmount));
            grade.setOneChoiceNumber(oneChoiceAmount);
            grade.setMultipleChoiceNumber(multipleChoiceAmount);
            grade.setQuestionNumber(oneChoiceAmount + multipleChoiceAmount);

            //单选题
            if (oneChoiceAmount != 0) {
                Label singleLabel = new Label("单选题");
                singleLabel.setPrefSize(200, 25);
                singleLabel.setFont(Font.font(14));
                selectPane.getChildren().add(singleLabel);
            }

            //添加单选题按钮
            for (int i = 0; i < oneChoiceAmount; i++) {
                Button button = factory.commonButton(String.valueOf(i + 1));
                button.setOnAction(actionEvent -> {
                    questionNum = Integer.parseInt(button.getText());
                    oneChoicePaneSet();
                });
                selectPane.getChildren().add(button);
            }

            //多选题
            if (multipleChoiceAmount != 0) {
                Label multipleLabel = new Label("多选题");
                multipleLabel.setPrefSize(200, 25);
                multipleLabel.setFont(Font.font(14));
                selectPane.getChildren().add(multipleLabel);
            }

            //添加多选题按钮
            for (int i = 0; i < multipleChoiceAmount; i++) {
                Button button = factory.commonButton(String.valueOf(i + 1));
                button.setOnAction(actionEvent -> {
                    questionNum = Integer.parseInt(button.getText());
                    multiChoicePaneSet();
                });
                selectPane.getChildren().add(button);
            }
            //上方代码正常

            if(oneChoiceAmount != 0 || multipleChoiceAmount != 0) {
                minute = 2 * (oneChoiceAmount + multipleChoiceAmount);
                second = 0;

                //将动画执行次数设置为无限次
                timeline.setCycleCount(Client.getTestQuestions().getAmount() * 2 * 60);
                //执行动画
                timeline.play();

                stage.close();
            }
        });


        Pane pane = new Pane(singleText, multipleText, single, multiple, ensure, quit, caution);
        pane.setPrefSize(400,250);

        //窗口设置
        stage.setTitle("设置题数");
        stage.setScene(new Scene(pane));
        //置于顶层
        stage.setAlwaysOnTop(true);
        //禁止更改大小
        stage.setResizable(false);
        stage.show();
    }

    //添加考题
    private void addQuestionMethod() {
        Stage stage = new Stage();

        ToggleGroup group = new ToggleGroup();

        RadioButton singleChoice = new RadioButton("单选题");
        singleChoice.setToggleGroup(group);
        singleChoice.setFont(new Font(14));
        singleChoice.setLayoutX(50);
        singleChoice.setLayoutY(50);
        RadioButton multiChoice = new RadioButton("多选题");
        multiChoice.setToggleGroup(group);
        multiChoice.setFont(new Font(14));
        multiChoice.setLayoutX(200);
        multiChoice.setLayoutY(50);

        Text topic = new Text("题目");
        topic.setFont(new Font(14));
        topic.setLayoutX(50);
        topic.setLayoutY(144);

        //题目输入框
        TextArea topicArea = new TextArea();
        topicArea.setWrapText(true);
        topicArea.setPrefSize(300,80);
        topicArea.setLayoutX(95);
        topicArea.setLayoutY(100);

        TextField[] choices = new TextField[4];

        Text answer = new Text("答案");
        answer.setFont(new Font(14));
        answer.setLayoutX(50);
        answer.setLayoutY(419);

        TextField answerFiled = new TextField();
        answerFiled.setPrefSize(300, 30);
        answerFiled.setLayoutX(95);
        answerFiled.setLayoutY(400);

        Text caution = new Text();
        caution.setFont(new Font(14));
        caution.setFill(Color.RED);
        caution.setLayoutX(50);
        caution.setLayoutY(449);

        Button sure = new Button("确认");
        sure.setPrefSize(160, 40);
        sure.setLayoutX(50);
        sure.setLayoutY(460);
        sure.setOnAction(event -> {
            Question question;
            boolean judge = false;
            String[] option = new String[4];

            caution.setFill(Color.RED);

            for (TextField choice : choices) {
                if (choice.getText().equals("")) {
                    judge = true;
                    break;
                }
            }

            if (!singleChoice.isSelected() && !multiChoice.isSelected()) {
                caution.setText("请选择添加题目的类型");
            } else if (topicArea.getText().equals("")) {
                caution.setText("请输入题目");
            } else if (judge) {
                caution.setText("请输入选项内容");
            } else if (answerFiled.getText().equals("")) {
                caution.setText("请输入答案");
            } else {
                for (int index = 0; index < 4; index++) {
                    option[index] = choices[index].getText();
                }

                if (singleChoice.isSelected()) {
                    question = new OneChoiceQuestion();
                    if (answerFiled.getText().length() != 1 || Choice.getChoice(answerFiled.getText()) == null) {
                        caution.setText("请输入正确的答案");
                    } else {
                        question.setQuestion(topicArea.getText());
                        question.setChoices(option);
                        ((OneChoiceQuestion)question).setRightAnswer(Choice.getChoice(answerFiled.getText()));

                        Client.getOneService().creat(question);
                        caution.setFill(Color.GREEN);
                        caution.setText("单选题添加成功");
                        Client.getLogger().info("添加单选题成功");
                    }
                } else {
                    question = new MultipleChoiceQuestion();
                    EnumSet<Choice> choice = EnumSet.noneOf(Choice.class);

                    for (char ch : answerFiled.getText().toCharArray()) {
                        choice.add(Choice.getChoice(String.valueOf(ch)));
                    }

                    if (answerFiled.getText().length() > 4 || answerFiled.getText().length() < 1) {
                        caution.setText("请输入正确的答案");
                    } else {
                        question.setQuestion(topicArea.getText());
                        question.setChoices(option);
                        ((MultipleChoiceQuestion)question).setRightAnswers(choice);

                        Client.getMultiService().creat(question);
                        caution.setFill(Color.GREEN);
                        caution.setText("多选题添加成功");
                        Client.getLogger().info("添加多选题成功");
                    }
                }
            }
        });

        Button cancel = new Button("取消");
        cancel.setPrefSize(160, 40);
        cancel.setLayoutX(235);
        cancel.setLayoutY(460);
        cancel.setOnAction(event -> stage.close());

        Pane pane = new Pane(singleChoice, multiChoice, topic, topicArea, answer, answerFiled, caution, sure, cancel);
        pane.setPrefSize(450, 540);


        for (int index = 0; index < 4; index++) {
            choices[index] = new TextField();
            choices[index].setPrefSize(300, 30);
            choices[index].setLayoutX(95);
            choices[index].setLayoutY(200 + index * 50);

            Text choice = new Text("  " + (char) ('A' + index));
            choice.setFont(new Font(14));
            choice.setLayoutX(50);
            choice.setLayoutY(219 + index * 50);

            pane.getChildren().add(choices[index]);
            pane.getChildren().add(choice);
        }


        stage.getIcons().add(new Image("file:src/main/resources/image/icon.png"));
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setTitle("添加题目");
        stage.setScene(new Scene(pane));
        stage.show();
    }

    //查看成绩
    private void viewGradesMethod() {
        //生成舞台
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:src/main/resources/image/icon.png"));

        //设置各信息栏
        Label id = new Label("id: " + Client.getUser().getId());
        id.setFont(new Font(14));
        id.setLayoutX(40);
        id.setLayoutY(35);

        Label name = new Label("姓名: " + Client.getUser().getName());
        name.setFont(new Font(14));
        name.setLayoutX(200);
        name.setLayoutY(35);

        Label correctPer = new Label("正确率: ");
        correctPer.setFont(new Font(14));
        correctPer.setLayoutX(40);
        correctPer.setLayoutY(75);

        Label grade = new Label("成绩: ");
        grade.setFont(new Font(14));
        grade.setLayoutX(200);
        grade.setLayoutY(75);

        Label times = new Label("测试次数: ");
        times.setFont(new Font(14));
        times.setLayoutX(40);
        times.setLayoutY(115);

        //查询历史成绩
        //如果无历史成绩则显示空
        Map<Integer, UserGrade> grades = Client.getUser().getGrades();
        UserGrade lastGrade = grades.get(grades.size());
        if (lastGrade != null) {
            grade.setText(grade.getText() + lastGrade.getGrade());
            correctPer.setText(correctPer.getText() + Math.round(lastGrade.getCorrectNumber() * 10000.0 / lastGrade.getQuestionNumber()) / 100.0 + "%");
            times.setText(times.getText() + Client.getUser().getGrades().size());
        }

        Button exit = new Button("退出");
        exit.setPrefSize(120,40);
        exit.setLayoutX(210);
        exit.setLayoutY(160);

        exit.setOnAction(actionEvent -> stage.close());

        Pane pane = new Pane();
        pane.setPrefSize(400, 250);
        pane.getChildren().addAll(id, name, correctPer, grade, times, exit);

        stage.setTitle("查询成绩");
        stage.setScene(new Scene(pane));
        //置于顶层
        stage.setAlwaysOnTop(true);
        //禁止更改大小
        stage.setResizable(false);
        stage.show();
    }

    //登出
    private void logoutMethod() {
        User user = Client.getUser();

        Client.getStage().setScene(new Login().getScene());

        Client.getUserService().updateGrades(user.getId(), (TreeMap)user.getGrades());
        Client.getLogger().info("用户["+ user.getName() +"]成绩上传成功");

        Client.setUser(null);
        Client.getLogger().info("用户[" + user.getName() + "]登出成功");
    }

    //提交并检查答案
    private void submitMethod() {
        check = true;
        selectPane.getChildren().clear();

        Map<Integer, ? extends Question> questions = Client.getTestQuestions().getOneChoiceQuestionTreeMap();
        Set<Integer> ids = questions.keySet();

        int correctNumber = 0;

        if (ids.size() != 0) {
            Label singleLabel = new Label("单选题");
            singleLabel.setPrefSize(200, 25);
            singleLabel.setFont(Font.font(14));
            selectPane.getChildren().add(singleLabel);
        }

        //遍历单选题
        for (int id : ids) {
            Button button;
            OneChoiceQuestion question = (OneChoiceQuestion)questions.get(id);

            if (question.getRightAnswer().equals(question.getAnswer())) {
                correctNumber++;
                button = factory.trueButton(String.valueOf(id));
            } else {
                button = factory.wrongButton(String.valueOf(id));
            }

            button.setOnAction(event -> {
                questionNum = Integer.parseInt(button.getText());
                oneChoicePaneSet();
                showAnswer();
            });

            selectPane.getChildren().add(button);
        }

        questions = Client.getTestQuestions().getMultipleChoiceQuestionTreeMap();
        ids = questions.keySet();

        if (ids.size() != 0) {
            Label multipleLabel = new Label("多选题");
            multipleLabel.setPrefSize(200, 25);
            multipleLabel.setFont(Font.font(14));
            selectPane.getChildren().add(multipleLabel);
        }

        for (int id : ids) {
            Button button;
            MultipleChoiceQuestion question = (MultipleChoiceQuestion)questions.get(id);

            if (question.getRightAnswers().equals(question.getAnswers())) {
                correctNumber++;
                button = factory.trueButton(String.valueOf(id));
            } else {
                button = factory.wrongButton(String.valueOf(id));
            }

            button.setOnAction(event -> {
                questionNum = Integer.parseInt(button.getText());
                multiChoicePaneSet();
                showAnswer();
            });

            selectPane.getChildren().add(button);
        }

        grade.setCorrectNumber(correctNumber);
        grade.setGrade((float) (grade.getCorrectNumber() * 100.0 / grade.getQuestionNumber()));
        Client.getUser().getGrades().put(grade.getTimes(), grade);

        timeline.pause();
    }

    //更新多选题选择项
    private void update(boolean judge, String choice) {
        //获取已经选中的选项
        EnumSet<Choice> choices = ((MultipleChoiceQuestion) thisQuestion).getAnswers();
        //当选项为空时新建一个空的结果集
        if (choices == null) {
            choices = EnumSet.noneOf(Choice.class);
        }

        if (judge) {
            choices.add(Choice.getChoice(choice));
        } else {
            choices.remove(Choice.getChoice(choice));
        }

        Client.getTestQuestions().getMultipleChoiceQuestionTreeMap().get(questionNum).setAnswers(choices);
    }

    //设置单选题答题面板
    private void oneChoicePaneSet() {
        thisQuestion = Client.getTestQuestions().getOneChoiceQuestionTreeMap().get(questionNum);
        flush();

        int choice = 4;
        if (((OneChoiceQuestion)thisQuestion).getAnswer() != null) {
            choice = Choice.toChar(((OneChoiceQuestion)thisQuestion).getAnswer()) - 'A';
        }

        //选项组
        ToggleGroup singleGroup = new ToggleGroup();

        for (int index = 0; index < 4; index++) {
            single[index] = new RadioButton();
            single[index].setPrefSize(1000, 50);
            single[index].getStyleClass().add("choice-box");
            single[index].setToggleGroup(singleGroup);
            single[index].setText(thisQuestion.getChoices()[index]);
            if (choice == index) {
                single[index].setSelected(true);
            }
            questionPane.getChildren().add(single[index]);
        }

        //添加选项监听器
        singleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton radioButton = (RadioButton) newValue;

            for (int index = 0; index < 4; index++) {
                if (radioButton.getText().equals(thisQuestion.getChoices()[index])) {
                    //记录回答的答案
                    Client.getTestQuestions().getOneChoiceQuestionTreeMap().get(questionNum).setAnswer(Choice.getChoice(String.valueOf((char) ('A' + index))));
                    //题目标记
                    selectPane.getChildren().get(questionNum).getStyleClass().add("have-done");
                    break;
                }
            }

            answer.setText("你选择的答案: [" + ((OneChoiceQuestion) thisQuestion).getAnswer() + "]");
            if (!questionPane.getChildren().contains(answer)) {
                questionPane.getChildren().add(answer);
            }
        });

        Choice choices = ((OneChoiceQuestion)thisQuestion).getAnswer();
        if (choices != null) {
            answer.setText("你选择的答案: [" + choices + "]");
            questionPane.getChildren().add(answer);
        }
    }

    //设置多选题答题面板
    private void multiChoicePaneSet() {
        thisQuestion = Client.getTestQuestions().getMultipleChoiceQuestionTreeMap().get(questionNum);
        flush();

        boolean[] judge = {false, false, false, false};

        if (((MultipleChoiceQuestion)thisQuestion).getAnswers() != null) {
            for (Choice choice : ((MultipleChoiceQuestion) thisQuestion).getAnswers()) {
                judge[Choice.toChar(choice) - 'A'] = true;
            }
        }

        for (int index = 0; index < 4; index++) {
            //lambda表达式只能使用final的临时变量
            int finalIndex = index;
            multiple[index] = new CheckBox();
            multiple[index].setPrefSize(1000, 50);
            multiple[index].getStyleClass().add("choice-box");
            multiple[index].setText(thisQuestion.getChoices()[index]);
            if (judge[index]) {
                multiple[index].setSelected(true);
            }
            questionPane.getChildren().add(multiple[index]);

            multiple[index].selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                update(newValue, String.valueOf((char) ('A' + finalIndex)));
                selectPane.getChildren().get(questionNum + Client.getTestQuestions().getOneChoiceAmount() + 1).getStyleClass().add("have-done");

                answer.setText("你选择的答案: " + ((MultipleChoiceQuestion) thisQuestion).getAnswers());
                if (!questionPane.getChildren().contains(answer)) {
                    questionPane.getChildren().add(answer);
                }
            });
        }

        EnumSet<Choice> choices = ((MultipleChoiceQuestion)thisQuestion).getAnswers();
        if (choices != null) {
            answer.setText("你选择的答案: " + choices);
            questionPane.getChildren().add(answer);
        }
    }

    //清空答题面板、设置问题
    private void flush() {
        //清空vbox
        questionPane.getChildren().clear();

        question.setText(questionNum + ". " +thisQuestion.getQuestion());
        questionPane.getChildren().add(question);
    }

    //展示参考答案
    private void showAnswer() {
        if (check) {
            for (int i = 0; i < 4; i++) {
                single[i].getStyleClass().remove("true-box");
                multiple[i].getStyleClass().remove("true-box");
            }

            if (thisQuestion instanceof OneChoiceQuestion) {
                single[((byte) Choice.toChar(((OneChoiceQuestion) thisQuestion).getRightAnswer())) - ((byte) 'A')].getStyleClass().add("true-box");
            } else if (thisQuestion instanceof MultipleChoiceQuestion) {
                for (Choice choice : ((MultipleChoiceQuestion) thisQuestion).getRightAnswers()) {
                    multiple[((byte) Choice.toChar(choice)) - ((byte) 'A')].getStyleClass().add("true-box");
                }
            }
        }
    }
}
