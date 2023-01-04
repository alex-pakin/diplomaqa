package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class CardPaymentTest {

    PaymentPage paymentPage = new PaymentPage();

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
    class mainPathsCardWithDifferentStatus {
        @Test
        void shouldSuccessPayApprovedCard() {
            val cardInfo = DataHelper.getValidAcceptedCardInfo();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.approvedPayment(),
                    () -> paymentPage.cardApprovedStatus(),
                    () -> paymentPage.cardApprovedCount()
            );
        }

        @Test
        void shouldFailPayDeclinedCard() {
            val cardInfo = DataHelper.getValidDeclinedCardInfo();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.declinedPayment(),
                    () -> paymentPage.cardDeclinedStatus(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class invalidCardNumberField{
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyNumber();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitNumber();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithThreeDigitNumber();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFifteenDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithFifteenDigitNumber();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayUnregisteredCard() {
            val cardInfo = DataHelper.getInvalidCardInfoIfCardIsNotRegisteredInBase();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldRejectedPay(),
                    () -> paymentPage.cardDeclinedStatus(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class invalidMonthField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyMonth();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitMonth();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithDoubleZeroMonth();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThirteenMonthField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithThirteenMonth();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongValidity(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class invalidYearField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyYear();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitYear();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayPastYearField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithPastYear();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldExpiredDate(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFarFutureYearField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithFutureYear();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongValidity(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class invalidHolderField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyHolder();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldIsEmpty(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInCyrillic() {
            val cardInfo = DataHelper.getInvalidCardInfoWithHolderInCyrillic();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInDigits() {
            val cardInfo = DataHelper.getInvalidCardInfoWithHolderInDigits();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInSymbols() {
            val cardInfo = DataHelper.getInvalidCardInfoWithHolderInSymbols();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }

    @Nested
    class invalidCVCField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyCVC();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitCVC();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithTwoDigitCVC();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithTripleZeroCVC();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.cardDeclinedCount()
            );
        }
    }
}
