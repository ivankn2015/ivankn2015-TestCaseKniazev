package org.example.analyzer;


import org.example.parser.GitIgnoreParser;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GitIgnoreWorker {
    private final GitIgnoreParser parser = new GitIgnoreParser();
    private final Map<Path, List<String>> gitIgnoreCache = new ConcurrentHashMap<>();

    public boolean shouldIgnore(Path file) {
        Path dir = file.getParent();
        List<String> patterns = gitIgnoreCache.computeIfAbsent(dir, this::loadGitIgnorePatterns);
        return patterns.stream().anyMatch(pattern -> parser.matchesPattern(file, pattern));
    }

    private List<String> loadGitIgnorePatterns(Path directory) {
        Path gitIgnore = directory.resolve(".gitignore");
        if (java.nio.file.Files.exists(gitIgnore)) {
            return parser.parseGitIgnore(gitIgnore);
        }
        return List.of();
    }
}