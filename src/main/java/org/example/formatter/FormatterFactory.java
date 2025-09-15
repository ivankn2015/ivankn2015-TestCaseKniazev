package org.example.formatter;

public class FormatterFactory {

    public static OutputFormatter createFormatter(String format) {
        if (format == null) {
            return new PlainFormatter();
        }

        return switch (format.toLowerCase()) {
            case "xml" -> new XmlFormatter();
            case "json" -> new JsonFormatter();
            default -> new PlainFormatter();
        };
    }
}