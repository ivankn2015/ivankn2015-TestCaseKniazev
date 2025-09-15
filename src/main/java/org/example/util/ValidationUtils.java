package org.example.util;

public class ValidationUtils {

    public static void validateOutputFormat(String format) {
        if (!format.equalsIgnoreCase("plain") &&
                !format.equalsIgnoreCase("xml") &&
                !format.equalsIgnoreCase("json")) {
            throw new IllegalArgumentException("Unsupported output format: " + format);
        }
    }
}