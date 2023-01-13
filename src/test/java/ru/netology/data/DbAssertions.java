package ru.netology.data;

import org.junit.jupiter.api.Assertions;


public class DbAssertions {

    public void cardApprovedStatus() {
        String expected = "APPROVED";
        String actual = DataBaseHelper.getPurchaseByCardStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void cardDeclinedStatus() {
        String expected = "DECLINED";
        String actual = DataBaseHelper.getPurchaseByCardStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void creditApprovedStatus() {
        String expected = "APPROVED";
        String actual = DataBaseHelper.getPurchaseByCreditStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void creditDeclinedStatus() {
        String expected = "DECLINED";
        String actual = DataBaseHelper.getPurchaseByCreditStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void cardApprovedCount() {
        long expected = 1;
        long actual = DataBaseHelper.getPurchaseByCardCount();
        Assertions.assertEquals(expected,actual);
    }

    public void cardDeclinedCount() {
        long expected = 0;
        long actual = DataBaseHelper.getPurchaseByCardCount();
        Assertions.assertEquals(expected,actual);
    }

    public void creditApprovedCount() {
        long expected = 1;
        long actual = DataBaseHelper.getPurchaseByCreditCount();
        Assertions.assertEquals(expected,actual);
    }

    public void creditDeclinedCount() {
        long expected = 0;
        long actual = DataBaseHelper.getPurchaseByCreditCount();
        Assertions.assertEquals(expected,actual);
    }
}
