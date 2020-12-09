package ink.kikkiz.db;

import java.sql.Connection;

/**
 * @author KikkiZ
 */
public interface DatabaseConnection {
    Connection getConnection();

    void close();
}
