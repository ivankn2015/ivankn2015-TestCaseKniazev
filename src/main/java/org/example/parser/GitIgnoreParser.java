package org.example.parser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class GitIgnoreParser {

    public List<String> parseGitIgnore(Path gitIgnorePath) {
        try {
            return Files.readAllLines(gitIgnorePath)
                    .stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Warning: Cannot read .gitignore file: " + gitIgnorePath);
            return List.of();
        }
    }

    public boolean matchesPattern(Path file, String pattern) {
        String filename = file.getFileName().toString();
        String relativePath = file.toString(); // Упрощенная реализация

        if (pattern.startsWith("/")) {
            // Абсолютный путь от корня репозитория
            return relativePath.endsWith(pattern.substring(1));
        } else if (pattern.contains("/")) {
            // Относительный путь
            return relativePath.contains(pattern);
        } else {
            // Просто имя файла
            return filename.equals(pattern) || matchesWildcard(filename, pattern);
        }
    }

    private boolean matchesWildcard(String filename, String pattern) {
        if (pattern.equals("*")) return true;
        if (pattern.startsWith("*.")) {
            String ext = pattern.substring(2);
            return filename.endsWith("." + ext);
        }
        if (pattern.startsWith("**/")) {
            return filename.equals(pattern.substring(3));
        }
        return false;
    }
}