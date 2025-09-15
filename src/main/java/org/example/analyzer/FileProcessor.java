package org.example.analyzer;

import org.example.model.FileStats;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileProcessor {
    private final ConcurrentMap<String, FileStats> statsMap = new ConcurrentHashMap<>();
    private final FileAnalyzer analyzer = new FileAnalyzer();

    public void processFile(Path filePath) {
        try {
            FileStats fileStats = analyzer.analyzeFile(filePath);
            statsMap.merge(fileStats.getExtension(), fileStats,
                    (existing, newStats) -> {
                        existing.merge(newStats);
                        return existing;
                    });
        } catch (Exception e) {
            System.err.println("Error processing file: " + filePath + " - " + e.getMessage());
        }
    }

    public ConcurrentMap<String, FileStats> getStats() {
        return statsMap;
    }
}