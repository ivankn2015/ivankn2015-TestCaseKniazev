package org.example.config;

import lombok.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Getter
@Builder
public class AppConfig {
    @NonNull
    private Path path;
    @Builder.Default private boolean recursive = false;
    @Builder.Default private int maxDepth = 0;
    @Builder.Default private int threadCount = Runtime.getRuntime().availableProcessors();
    @Builder.Default private Set<String> includeExtensions = Set.of();
    @Builder.Default private Set<String> excludeExtensions = Set.of();
    @Builder.Default private boolean useGitIgnore = false;
    @Builder.Default private String outputFormat = "plain";

    @SneakyThrows
    public void validate() {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be positive");
        }
        if (maxDepth < 0) {
            throw new IllegalArgumentException("Max depth cannot be negative");
        }
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path does not exist: " + path);
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path is not a directory: " + path);
        }
    }
}