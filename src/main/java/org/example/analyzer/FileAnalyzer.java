package org.example.analyzer;

import org.example.model.FileStats;
import org.example.model.FileType;
import org.example.util.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileAnalyzer {

    public FileStats analyzeFile(Path filePath) throws IOException {
        String filename = filePath.getFileName().toString();
        String extension = FileUtils.getFileExtension(filename);
        FileType fileType = FileType.fromExtension(extension);

        FileStats stats = new FileStats(extension);
        long size = Files.size(filePath);
        long totalLines = 0;
        long nonEmptyLines = 0;
        long commentLines = 0;

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;

                if (!line.trim().isEmpty()) {
                    nonEmptyLines++;
                }

                if (fileType.isCommentLine(line)) {
                    commentLines++;
                }
            }
        }

        stats.addFile(size, totalLines, nonEmptyLines, commentLines);
        return stats;
    }
}