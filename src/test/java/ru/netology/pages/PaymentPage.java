package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

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

}
