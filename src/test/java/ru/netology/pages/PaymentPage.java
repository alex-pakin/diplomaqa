package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    MainPage mainPage = new MainPage();

    private final SelenideElement numberField = $x("//*[.='Номер карты'] //input");
    private final SelenideElement monthField = $x("//*[.='Месяц'] //input");
    private final SelenideElement yearField = $x("//*[.='Год'] //input");
    private final SelenideElement holderField = $x("//*[.='Владелец'] //input");
    private final SelenideElement CVCField = $x("//*[.='CVC/CVV'] //input");
    private final SelenideElement buttonContinue = $$("button").find(exactText("Продолжить"));
    private final SelenideElement invalidFormat = $(byText("Неверный формат"));
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement invalidExpiredDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredCard = $(byText("Истёк срок действия карты"));
    private final SelenideElement notificationSuccess = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");

    public void fillFormPayByCard(DataHelper.Card cardInfo) {
        mainPage.selectPayByCard();
        numberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getCardMonth());
        yearField.setValue(cardInfo.getCardYear());
        holderField.setValue(cardInfo.getCardHolder());
        CVCField.setValue(cardInfo.getCardCVC());
        buttonContinue.click();
    }

    public void fillFormPayByCredit (DataHelper.Card cardInfo) {
        mainPage.selectPayByCredit();
        numberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getCardMonth());
        yearField.setValue(cardInfo.getCardYear());
        holderField.setValue(cardInfo.getCardHolder());
        CVCField.setValue(cardInfo.getCardCVC());
        buttonContinue.click();
    }

    public void approvedPayment() {
        notificationSuccess.shouldBe(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    public void declinedPayment() {
        notificationError.shouldBe(Condition.text("Ошибка! Банк отказал в проведении операции."),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void numberFieldWrongFormat() {
        invalidFormat.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void numberFieldRejectedPay() {
        notificationError.shouldBe(Condition.text("Ошибка! Банк отказал в проведении операции."),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void monthFieldWrongFormat() {
        invalidFormat.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void monthFieldWrongValidity() {
        invalidExpiredDate.shouldBe(Condition.text("Неверно указан срок действия карты"), visible);
    }

    public void yearFieldWrongFormat() {
        invalidFormat.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void yearFieldExpiredDate() {
        expiredCard.shouldBe(Condition.text("Истёк срок действия карты"), visible);
    }

    public void yearFieldWrongValidity() {
        invalidExpiredDate.shouldBe(Condition.text("Неверно указан срок действия карты"), visible);
    }

    public void holderFieldWrongFormat() {
        invalidFormat.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void holderFieldIsEmpty() {
        emptyField.shouldBe(Condition.text("Поле обязательно для заполнения"), visible);
    }

    public void CVCFieldWrongFormat() {
        invalidFormat.shouldBe(Condition.text("Неверный формат"), visible);
    }

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

//    public void clearWholeForm() {
//        numberField.sendKeys(Keys.LEFT_CONTROL + "A");
//        numberField.sendKeys(Keys.BACK_SPACE);
//        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
//        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
//        holderField.doubleClick().sendKeys(Keys.BACK_SPACE);
//        CVCField.doubleClick().sendKeys(Keys.BACK_SPACE);
//    }
}
