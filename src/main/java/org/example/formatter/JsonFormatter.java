package org.example.formatter;

import org.example.model.FileStats;

import java.util.Map;

public class JsonFormatter implements OutputFormatter {
    @Override
    public String format(Map<String, FileStats> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"fileStatistics\": [\n");

        boolean first = true;
        for (Map.Entry<String, FileStats> entry : stats.entrySet()) {
            if (!first) {
                sb.append(",\n");
            }
            first = false;

            FileStats s = entry.getValue();
            sb.append("    {\n");
            sb.append("      \"extension\": \"").append(entry.getKey()).append("\",\n");
            sb.append("      \"fileCount\": ").append(s.getFileCount()).append(",\n");
            sb.append("      \"totalSize\": ").append(s.getTotalSize()).append(",\n");
            sb.append("      \"totalLines\": ").append(s.getTotalLines()).append(",\n");
            sb.append("      \"nonEmptyLines\": ").append(s.getNonEmptyLines()).append(",\n");
            sb.append("      \"commentLines\": ").append(s.getCommentLines()).append("\n");
            sb.append("    }");
        }

        sb.append("\n  ]\n}");
        return sb.toString();
    }
}