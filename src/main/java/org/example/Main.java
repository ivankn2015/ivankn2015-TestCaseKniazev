package org.example;

import org.example.analyzer.FileProcessor;
import org.example.analyzer.FileWalker;
import org.example.config.AppConfig;
import org.example.config.HelpPrinter;
import org.example.formatter.FormatterFactory;
import org.example.formatter.OutputFormatter;
import org.example.parser.ArgumentParser;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            HelpPrinter.printUsage();
            return;
        }

        try {
            ArgumentParser parser = new ArgumentParser();
            AppConfig config = parser.parse(args);

            FileProcessor processor = new FileProcessor();
            FileWalker walker = new FileWalker(config, processor);

            var stats = walker.walk();

            OutputFormatter formatter = FormatterFactory.createFormatter(config.getOutputFormat());
            System.out.println(formatter.format(stats));

        } catch (Exception e) {
            HelpPrinter.printError(e.getMessage());
            System.exit(1);
        }
    }
}