package org.example.config;

public class HelpPrinter {

    public static void printUsage() {
        System.out.println("Usage: java -jar FileStatsAnalyzer.jar <path> [options]");
        System.out.println("Options:");
        System.out.println("  --recursive           Recursive directory traversal");
        System.out.println("  --max-depth=<number>  Maximum recursion depth");
        System.out.println("  --thread=<number>     Number of threads to use");
        System.out.println("  --include-ext=<list>  Include only specified extensions (comma-separated)");
        System.out.println("  --exclude-ext=<list>  Exclude specified extensions (comma-separated)");
        System.out.println("  --git-ignore          Respect .gitignore files");
        System.out.println("  --output=<format>     Output format: plain, xml, json");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar FileStatsAnalyzer.jar /path/to/dir --recursive --thread=4");
        System.out.println("  java -jar FileStatsAnalyzer.jar /path/to/dir --include-ext=java,xml --output=json");
    }

    public static void printError(String message) {
        System.err.println("Error: " + message);
        System.err.println("Use --help for usage information");
    }
}