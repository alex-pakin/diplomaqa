package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    @Value
    public static class Card {
        String cardNumber;
        String cardMonth;
        String cardYear;
        String cardHolder;
        String cardCVC;
    }

    static Faker faker = new Faker(new Locale("en"));
    static String acceptedCardNumber = "4444 4444 4444 4441";
    static String declinedCardNumber = "4444 4444 4444 4442";
    static String month = LocalDate.now().plusMonths(6).format(DateTimeFormatter.ofPattern("MM"));
    static String year = LocalDate.now().plusYears(4).format(DateTimeFormatter.ofPattern("yy"));
    static String holder = faker.name().fullName().toUpperCase();
    static String cvc = String.valueOf(faker.number().numberBetween(101, 999));

    public static Card getValidAcceptedCardInfo() {
        return new Card(acceptedCardNumber,month,year,holder,cvc);
    }

    public static Card getValidDeclinedCardInfo() {
        return new Card(declinedCardNumber,month,year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithEmptyNumber() {
        return new Card("",month,year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithOneDigitNumber() {
        return new Card("4",month,year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithThreeDigitNumber() {
        return new Card("444",month,year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithFifteenDigitNumber() {
        return new Card("4444 4444 4444 444",month,year,holder,cvc);
    }

    public static Card getInvalidCardInfoIfCardIsNotRegisteredInBase() {
        return new Card("4444 4444 4444 4440",month,year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithEmptyMonth() {
        return new Card(acceptedCardNumber,"",year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithOneDigitMonth() {
        return new Card(acceptedCardNumber,"9",year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithDoubleZeroMonth() {
        return new Card(acceptedCardNumber,"00",year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithThirteenMonth() {
        return new Card(acceptedCardNumber,"13",year,holder,cvc);
    }

    public static Card getInvalidCardInfoWithEmptyYear() {
        return new Card(acceptedCardNumber,month,"",holder,cvc);
    }

    public static Card getInvalidCardInfoWithOneDigitYear() {
        return new Card(acceptedCardNumber,month,"2",holder,cvc);
    }

    public static Card getInvalidCardInfoWithPastYear() {
        return new Card(acceptedCardNumber,month,"21",holder,cvc);
    }

    public static Card getInvalidCardInfoWithFutureYear() {
        return new Card(acceptedCardNumber,month,"29",holder,cvc);
    }

    public static Card getInvalidCardInfoWithEmptyHolder() {
        return new Card(acceptedCardNumber,month,year,"",cvc);
    }

    public static Card getInvalidCardInfoWithHolderInCyrillic() {
        return new Card(acceptedCardNumber,month,year,"ИВАНОВ ИВАН",cvc);
    }

    public static Card getInvalidCardInfoWithHolderInDigits() {
        return new Card(acceptedCardNumber,month,year,"4444",cvc);
    }

    public static Card getInvalidCardInfoWithHolderInSymbols() {
        return new Card(acceptedCardNumber,month,year,"!@#$%^&*()",cvc);
    }

    public static Card getInvalidCardInfoWithEmptyCVC() {
        return new Card(acceptedCardNumber,month,year,holder,"");
    }

    public static Card getInvalidCardInfoWithOneDigitCVC() {
        return new Card(acceptedCardNumber,month,year,holder,"7");
    }

    public static Card getInvalidCardInfoWithTwoDigitCVC() {
        return new Card(acceptedCardNumber,month,year,holder,"77");
    }

    public static Card getInvalidCardInfoWithTripleZeroCVC() {
        return new Card(acceptedCardNumber,month,year,holder,"000");
    }

}
