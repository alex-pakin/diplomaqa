package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataBaseHelper {

    private static final String url = System.getProperty("db.url","jdbc:mysql://localhost:3306/app");
    private static final String user = System.getProperty("db.user", "app");
    private static final String password = System.getProperty("db.password", "pass");

    @SneakyThrows
    public static String getPurchaseByCardStatus() {
        var sql = "SELECT status FROM payment_entity;";
        var runner = new QueryRunner();
        return runner.query(DriverManager.getConnection(url,user,password), sql, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getPurchaseByCreditStatus() {
        var status = "SELECT status FROM credit_request_entity;";
        var runner = new QueryRunner();
        return runner.query(DriverManager.getConnection(url,user,password), status, new ScalarHandler<>());
    }

    @SneakyThrows
    public static long getPurchaseByCardCount() {
        var sql = "SELECT COUNT(id) as count FROM payment_entity;";
        var runner = new QueryRunner();
        return runner.query(DriverManager.getConnection(url,user,password), sql, new ScalarHandler<>());
    }

    @SneakyThrows
    public static long getPurchaseByCreditCount() {
        var sql = "SELECT COUNT(id) as count FROM credit_request_entity;";
        var runner = new QueryRunner();
        return runner.query(DriverManager.getConnection(url,user,password), sql, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var cleanOrder = "DELETE FROM order_entity;";
        var cleanPayment = "DELETE FROM payment_entity;";
        var cleanCredit = "DELETE FROM credit_request_entity;";
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url,user,password);

        runner.update(conn, cleanOrder);
        runner.update(conn, cleanPayment);
        runner.update(conn, cleanCredit);
    }
}
