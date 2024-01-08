package com.najmi.shop.utils;

public class CommonUtil {

    public static String convertToEnglishDigits(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char[] persianDigits = {'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};
        StringBuilder result = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                // Convert Persian digit to English digit
                int persianDigit = Character.getNumericValue(ch);
                if (persianDigit >= 0 && persianDigit <= 9) {
                    result.append((char) ('0' + persianDigit));
                } else {
                    result.append(ch); // Not a Persian digit, keep unchanged
                }
            } else {
                result.append(ch); // Not a digit, keep unchanged
            }
        }

        return result.toString();
    }
}
