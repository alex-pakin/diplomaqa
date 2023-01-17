package ru.netology.data;

import org.junit.jupiter.api.Assertions;


public class DbAssertions {

    public void cardApprovedStatus() {
        var expected = "APPROVED";
        var actual = DataBaseHelper.getPurchaseByCardStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void cardDeclinedStatus() {
        var expected = "DECLINED";
        var actual = DataBaseHelper.getPurchaseByCardStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void creditApprovedStatus() {
        var expected = "APPROVED";
        var actual = DataBaseHelper.getPurchaseByCreditStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void creditDeclinedStatus() {
        var expected = "DECLINED";
        var actual = DataBaseHelper.getPurchaseByCreditStatus();
        Assertions.assertEquals(expected,actual);
    }

    public void cardApprovedCount() {
        var expected = 1;
        var actual = DataBaseHelper.getPurchaseByCardCount();
        Assertions.assertEquals(expected,actual);
    }

    public void cardDeclinedCount() {
        var expected = 0;
        var actual = DataBaseHelper.getPurchaseByCardCount();
        Assertions.assertEquals(expected,actual);
    }

    public void creditApprovedCount() {
        var expected = 1;
        var actual = DataBaseHelper.getPurchaseByCreditCount();
        Assertions.assertEquals(expected,actual);
    }

    public void creditDeclinedCount() {
        var expected = 0;
        var actual = DataBaseHelper.getPurchaseByCreditCount();
        Assertions.assertEquals(expected,actual);
    }
}
