package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class CardPaymentTest {

    PaymentPage paymentPage = new PaymentPage();
    MainPage mainPage = new MainPage();

    @BeforeEach
    void shouldStart() {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @AfterEach
    void shouldCleanDB() {
        DataBaseHelper.cleanDataBase();
    }

    @BeforeAll
    static void startAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void closeAllure() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    class MainPathsCardWithDifferentStatus {
        @Test
        void shouldSuccessPayApprovedCard() {
            var cardInfo = DataHelper.getValidAcceptedCardInfo();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.approvedPayment(),
                    () -> paymentPage.cardApprovedStatus(),
                    () -> paymentPage.cardApprovedCount()
            );
        }

        @Test
        void shouldFailPayDeclinedCard() {
            var cardInfo = DataHelper.getValidDeclinedCardInfo();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.declinedPayment(),
                    () -> paymentPage.cardDeclinedStatus(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidCardNumberField{
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyNumber();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitNumber();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithThreeDigitNumber();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFifteenDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithFifteenDigitNumber();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayUnregisteredCard() {
            var cardInfo = DataHelper.getInvalidCardInfoIfCardIsNotRegisteredInBase();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldRejectedPay(),
                    () -> paymentPage.cardDeclinedStatus(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidMonthField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyMonth();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitMonth();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithDoubleZeroMonth();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThirteenMonthField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithThirteenMonth();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongValidity(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidYearField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyYear();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitYear();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayPastYearField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithPastYear();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldExpiredDate(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFarFutureYearField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithFutureYear();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongValidity(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidHolderField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyHolder();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldIsEmpty(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInCyrillic() {
            var cardInfo = DataHelper.getInvalidCardInfoWithHolderInCyrillic();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInDigits() {
            var cardInfo = DataHelper.getInvalidCardInfoWithHolderInDigits();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInSymbols() {
            var cardInfo = DataHelper.getInvalidCardInfoWithHolderInSymbols();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidCVCField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyCVC();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitCVC();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithTwoDigitCVC();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithTripleZeroCVC();
            mainPage.selectPayByCard();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }
}
