package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;

public class DataBaseHelper {

    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    @SneakyThrows
    public static String getPurchaseByCardStatus() {
        var sql = "SELECT status FROM payment_entity;";
        var runner = new QueryRunner();
        String paymentStatus;

        try (
                var conn = DriverManager.getConnection(url,user,password
                );
        ) {
            paymentStatus = runner.query(conn, sql, new ScalarHandler<>());
        }
        return paymentStatus;
    }

    @SneakyThrows
    public static String getPurchaseByCreditStatus() {
        var status = "SELECT status FROM credit_request_entity;";
        var runner = new QueryRunner();
        String creditStatus;

        try (
                var conn = DriverManager.getConnection(url,user,password
                );
        ) {
            creditStatus = runner.query(conn, status, new ScalarHandler<>());
        }
        return creditStatus;
    }

    @SneakyThrows
    public static long getPurchaseByCardCount() {
        var sql = "SELECT COUNT(id) as count FROM payment_entity;";
        var runner = new QueryRunner();
        long paymentCount;

        try (
                var conn = DriverManager.getConnection(url,user,password
                );
        ) {
            paymentCount = runner.query(conn, sql, new ScalarHandler<>());
        }
        return paymentCount;
    }

    @SneakyThrows
    public static long getPurchaseByCreditCount() {
        var sql = "SELECT COUNT(id) as count FROM credit_request_entity;";
        var runner = new QueryRunner();
        long creditCount;

        try (
                var conn = DriverManager.getConnection(url,user,password
                );
        ) {
            creditCount = runner.query(conn, sql, new ScalarHandler<>());
        }
        return creditCount;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var cleanOrder = "DELETE FROM order_entity;";
        var cleanPayment = "DELETE FROM payment_entity;";
        var cleanCredit = "DELETE FROM credit_request_entity;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(url,user,password
                );
        ) {
            runner.update(conn, cleanOrder);
            runner.update(conn, cleanPayment);
            runner.update(conn, cleanCredit);
        }
    }
}
