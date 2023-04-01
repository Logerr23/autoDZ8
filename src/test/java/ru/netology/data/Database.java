package ru.netology.data;

import lombok.SneakyThrows;
import lombok.Value;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private Database(){}

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public static String getValidUserID() {
        var conn = getConnection();
        String id = "";
        try (var rs = conn.createStatement().executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                id = rs.getString("id");
                var login = rs.getString("login");
                if (login.equals("vasya")) {
                    break;
                }
            }
        }
        return id;
    }
    @Value
    public static class UserCode {
        private String verifyCode;
    }
    @SneakyThrows
    public static UserCode getCode() {
        var conn = getConnection();
        String code = "";
        try (var rs = conn.createStatement().executeQuery("SELECT * FROM auth_codes ORDER BY created DESC")) {
            while (rs.next()) {
                code = rs.getString("code");
                var user_id = rs.getString("user_id");
                if (user_id.equals(getValidUserID())) {
                    break;
                }
            }
        }
        return new UserCode(code);
    }

    @SneakyThrows
    public static void clearDataBase(){
        var conn = Database.getConnection();
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 0;"); //отключить проверку внешних ключей
        conn.createStatement().executeUpdate("TRUNCATE cards;");             //очистить все таблицы
        conn.createStatement().executeUpdate("TRUNCATE auth_codes;");
        conn.createStatement().executeUpdate("TRUNCATE users;");
        conn.createStatement().executeUpdate("TRUNCATE card_transactions");
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 1;"); //включить проверку внешних ключей
    }

}
