package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditPaymentTest {

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

    @Nested
    class MainPathsCardWithDifferentStatus {
        @Test
        void shouldSuccessPayApprovedCard() {
            var cardInfo = DataHelper.getValidAcceptedCardInfo();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.approvedPayment(),
                    () -> paymentPage.creditApprovedStatus(),
                    () -> paymentPage.creditApprovedCount()
            );
        }

        @Test
        void shouldFailPayDeclinedCard() {
            var cardInfo = DataHelper.getValidDeclinedCardInfo();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.declinedPayment(),
                    () -> paymentPage.creditDeclinedStatus(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidCardNumberField{
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyNumber();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitNumber();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithThreeDigitNumber();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFifteenDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithFifteenDigitNumber();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayUnregisteredCard() {
            var cardInfo = DataHelper.getInvalidCardInfoIfCardIsNotRegisteredInBase();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldRejectedPay(),
                    () -> paymentPage.creditDeclinedStatus(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidMonthField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyMonth();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitMonth();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithDoubleZeroMonth();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThirteenMonthField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithThirteenMonth();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongValidity(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidYearField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyYear();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitYear();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayPastYearField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithPastYear();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldExpiredDate(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFarFutureYearField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithFutureYear();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongValidity(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidHolderField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyHolder();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldIsEmpty(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInCyrillic() {
            var cardInfo = DataHelper.getInvalidCardInfoWithHolderInCyrillic();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInDigits() {
            var cardInfo = DataHelper.getInvalidCardInfoWithHolderInDigits();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInSymbols() {
            var cardInfo = DataHelper.getInvalidCardInfoWithHolderInSymbols();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class InvalidCVCField {
        @Test
        void shouldFailPayEmptyField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithEmptyCVC();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithOneDigitCVC();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithTwoDigitCVC();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            var cardInfo = DataHelper.getInvalidCardInfoWithTripleZeroCVC();
            mainPage.selectPayByCredit();
            paymentPage.fillFormPayByCard(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }
}
