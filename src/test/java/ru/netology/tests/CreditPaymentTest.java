package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditPaymentTest {

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

    @Nested
    class mainPathsCardWithDifferentStatus {
        @Test
        void shouldSuccessPayApprovedCard() {
            val cardInfo = DataHelper.getValidAcceptedCardInfo();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.approvedPayment(),
                    () -> paymentPage.creditApprovedStatus(),
                    () -> paymentPage.creditApprovedCount()
            );
        }

        @Test
        void shouldFailPayDeclinedCard() {
            val cardInfo = DataHelper.getValidDeclinedCardInfo();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.declinedPayment(),
                    () -> paymentPage.creditDeclinedStatus(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class invalidCardNumberField{
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyNumber();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitNumber();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithThreeDigitNumber();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFifteenDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithFifteenDigitNumber();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayUnregisteredCard() {
            val cardInfo = DataHelper.getInvalidCardInfoIfCardIsNotRegisteredInBase();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.numberFieldRejectedPay(),
                    () -> paymentPage.creditDeclinedStatus(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class invalidMonthField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyMonth();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitMonth();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithDoubleZeroMonth();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThirteenMonthField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithThirteenMonth();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.monthFieldWrongValidity(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class invalidYearField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyYear();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitYear();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayPastYearField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithPastYear();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldExpiredDate(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayFarFutureYearField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithFutureYear();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.yearFieldWrongValidity(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class invalidHolderField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyHolder();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldIsEmpty(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInCyrillic() {
            val cardInfo = DataHelper.getInvalidCardInfoWithHolderInCyrillic();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInDigits() {
            val cardInfo = DataHelper.getInvalidCardInfoWithHolderInDigits();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayHolderInSymbols() {
            val cardInfo = DataHelper.getInvalidCardInfoWithHolderInSymbols();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.holderFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }

    @Nested
    class invalidCVCField {
        @Test
        void shouldFailPayEmptyField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithEmptyCVC();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayOneDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithOneDigitCVC();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayTwoDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithTwoDigitCVC();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }

        @Test
        void shouldFailPayThreeDigitField() {
            val cardInfo = DataHelper.getInvalidCardInfoWithTripleZeroCVC();
            paymentPage.fillFormPayByCredit(cardInfo);
            Assertions.assertAll(
                    () -> paymentPage.CVCFieldWrongFormat(),
                    () -> paymentPage.creditDeclinedCount()
            );
        }
    }
}
