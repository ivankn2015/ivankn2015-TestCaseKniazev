package org.example.analyzer;



import org.example.config.AppConfig;
import org.example.model.FileStats;
import org.example.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class FileWalker {
    private final AppConfig config;
    private final FileProcessor fileProcessor;
    private final GitIgnoreWorker gitIgnoreWorker;

    public FileWalker(AppConfig config, FileProcessor fileProcessor) {
        this.config = config;
        this.fileProcessor = fileProcessor;
        this.gitIgnoreWorker = config.isUseGitIgnore() ? new GitIgnoreWorker() : null;
    }

    public ConcurrentMap<String, FileStats> walk() throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(config.getThreadCount());

        try (Stream<Path> pathStream = Files.walk(config.getPath(), getMaxDepth())) {

            long fileCount = pathStream
                    .filter(Files::isRegularFile)
                    .filter(this::shouldProcessFile)
                    .count();

            System.out.println("Found " + fileCount + " files to process");

            try (Stream<Path> processingStream = Files.walk(config.getPath(), getMaxDepth())) {
                processingStream
                        .filter(Files::isRegularFile)
                        .filter(this::shouldProcessFile)
                        .forEach(file -> executor.submit(() -> fileProcessor.processFile(file)));
            }

            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                throw new RuntimeException("Timeout waiting for file processing");
            }
            return fileProcessor.getStats();
        }
    }
    private int getMaxDepth() {
        if (!config.isRecursive()) return 1;
        return config.getMaxDepth() > 0 ? config.getMaxDepth() : Integer.MAX_VALUE;
    }

    private boolean shouldProcessFile(Path file) {
        if (config.isUseGitIgnore() && gitIgnoreWorker != null && gitIgnoreWorker.shouldIgnore(file)) {
            return false;
        }

        if (isBinaryFile(file)) {
            return false;
        }

        String extension = FileUtils.getFileExtension(file.getFileName().toString());

        if (!config.getIncludeExtensions().isEmpty() &&
                !config.getIncludeExtensions().contains(extension)) {
            return false;
        }

        if (config.getExcludeExtensions().contains(extension)) {
            return false;
        }

        return true;
    }

    private boolean isBinaryFile(Path file) {
        String filename = file.getFileName().toString();
        String extension = FileUtils.getFileExtension(filename);

        List<String> binaryExtensions = List.of(
                "class", "jar", "bin", "lock", "exe", "dll", "so", "dylib",
                "png", "jpg", "jpeg", "gif", "bmp", "ico", "svg",
                "zip", "rar", "7z", "tar", "gz", "bz2", "xz",
                "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
                "mp3", "mp4", "avi", "mov", "wmv", "flv",
                "db", "sqlite", "mdb", "accdb"
        );

        return binaryExtensions.contains(extension.toLowerCase());
    }
}