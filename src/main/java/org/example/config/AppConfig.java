package org.example.config;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Getter
public class AppConfig {
    private Path path;
    private boolean recursive = false;
    private int maxDepth = 0;
    private int threadCount = Runtime.getRuntime().availableProcessors();
    private Set<String> includeExtensions = Set.of();
    private Set<String> excludeExtensions = Set.of();
    private boolean useGitIgnore = false;
    private String outputFormat = "plain";

    public static class Builder {
        private final AppConfig config = new AppConfig();

        public Builder path(Path path) {
            config.path = path;
            return this;
        }

        public Builder recursive(boolean recursive) {
            config.recursive = recursive;
            return this;
        }

        public Builder maxDepth(int maxDepth) {
            config.maxDepth = maxDepth;
            return this;
        }

        public Builder threadCount(int threadCount) {
            config.threadCount = threadCount;
            return this;
        }

        public Builder includeExtensions(Set<String> includeExtensions) {
            config.includeExtensions = includeExtensions;
            return this;
        }

        public Builder excludeExtensions(Set<String> excludeExtensions) {
            config.excludeExtensions = excludeExtensions;
            return this;
        }

        public Builder useGitIgnore(boolean useGitIgnore) {
            config.useGitIgnore = useGitIgnore;
            return this;
        }

        public Builder outputFormat(String outputFormat) {
            config.outputFormat = outputFormat;
            return this;
        }

        public AppConfig build() {
            if (config.path == null) {
                throw new IllegalStateException("Path is required");
            }
            if (config.threadCount <= 0) {
                throw new IllegalArgumentException("Thread count must be positive");
            }
            if (config.maxDepth < 0) {
                throw new IllegalArgumentException("Max depth cannot be negative");
            }
            if (!Files.exists(config.path)) {
                throw new IllegalArgumentException("Path does not exist: " + config.path);
            }
            if (!Files.isDirectory(config.path)) {
                throw new IllegalArgumentException("Path is not a directory: " + config.path);
            }
            return config;
        }
    }
}