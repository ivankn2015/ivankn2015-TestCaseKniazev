package org.example.formatter;


import org.example.model.FileStats;

import java.util.Map;

public class PlainFormatter implements OutputFormatter {
    @Override
    public String format(Map<String, FileStats> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("File Statistics:\n");
        sb.append("===============================================================================\n");
        sb.append(String.format("%-15s %-10s %-12s %-12s %-12s %-12s%n",
                "Extension", "Files", "Size(bytes)", "Total Lines", "Non-empty", "Comments"));
        sb.append("===============================================================================\n");

        for (Map.Entry<String, FileStats> entry : stats.entrySet()) {
            FileStats s = entry.getValue();

            sb.append(String.format("%-15s %-10d %-12d %-12d %-12d %-12d%n",
                    entry.getKey().isEmpty() ? "(no ext)" : entry.getKey(),
                    s.getFileCount(),
                    s.getTotalSize(),
                    s.getTotalLines(),
                    s.getNonEmptyLines(),
                    s.getCommentLines()));
        }

        return sb.toString();
    }
}