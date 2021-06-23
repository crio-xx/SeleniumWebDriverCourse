package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class StringUtils {

    public static String getRandomString(int length) {
        return getRandomSequence(length, 97, 122);
    }

    public static String getRandomName(int length) {
        return getRandomSequence(1, 65, 90) + getRandomString(length - 1);
    }

    public static String getRandomNumber(int length) {
        return getRandomSequence(length, 48, 57);
    }


    public static String getRandomSequence(int length, int leftLimit, int rightLimit) {
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String getCurrentDate() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getDateInFutureInFewDays(int days){
        return LocalDateTime.now()
                .plusDays(days)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
