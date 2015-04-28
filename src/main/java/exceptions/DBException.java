package exceptions;

import java.sql.SQLException;

public class DBException extends Exception {
    public DBException(SQLException e) {
    }
}
