package ink.kikkiz.entity.question;

/**
 * @author KikkiZ
 */
public enum Choice {
    A, B, C, D;

    /**
     * 获取指定的枚举对象
     *
     * @param choice 获取选项
     * @return 返回选项对应的枚举对象，无对应对象时返回null
     */
    public static Choice getChoice(String choice) {
        switch (choice) {
            case "A" : return Choice.A;
            case "B" : return Choice.B;
            case "C" : return Choice.C;
            case "D" : return Choice.D;
            default : return null;
        }
    }

    /**
     * 获取指定枚举对象的字符
     *
     * @param choice 获取选项
     * @return 返回选项对应的字符，无对应对象时返回空
     */
    public static char toChar(Choice choice) {
        switch (choice) {
            case A : return 'A';
            case B : return 'B';
            case C : return 'C';
            case D : return 'D';
            default : return ' ';
        }
    }
}
