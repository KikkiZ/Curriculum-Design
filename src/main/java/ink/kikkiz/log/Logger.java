package ink.kikkiz.log;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author KikkiZ
 */
public class Logger {
    BufferedWriter writer;

    public Logger() {
        try {
            writer = new BufferedWriter(new FileWriter(new File("logs/" + LocalDate.now() + ".log"), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于记录信息
     *
     * @param info 需要记录的信息
     */
    public void info(String info) {
        send("info", info);
    }

    /**
     * 用于记录警告信息
     *
     * @param warn 需要记录的警告信息
     */
    public void warn(String warn) {
        send("warn", warn);
    }

    /**
     * 用于记录异常信息
     *
     * @param exception 需要记录的异常
     */
    public void warn(Exception exception) {
        exception.printStackTrace(new PrintWriter(writer));
    }

    /**
     * 用于记录错误信息
     *
     * @param err 需要记录的错误信息
     */
    public void err(String err) {
        send("error", err);
    }

    private void send(String type, String massage) {
        try {
            writer.write("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "][" + type + "] " + massage);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            warn("catch an IOException");
        }
    }

    /**
     * 用于关闭Logger
     */
    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            warn("catch an IOException");
        }
    }
}
