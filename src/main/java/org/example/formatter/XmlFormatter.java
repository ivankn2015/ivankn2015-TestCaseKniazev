package org.example.formatter;



import org.example.model.FileStats;

import java.util.Map;

public class XmlFormatter implements OutputFormatter {
    @Override
    public String format(Map<String, FileStats> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<fileStatistics>\n");

        for (Map.Entry<String, FileStats> entry : stats.entrySet()) {
            FileStats s = entry.getValue();
            sb.append("  <fileType extension=\"").append(entry.getKey()).append("\">\n");
            sb.append("    <fileCount>").append(s.getFileCount()).append("</fileCount>\n");
            sb.append("    <totalSize>").append(s.getTotalSize()).append("</totalSize>\n");
            sb.append("    <totalLines>").append(s.getTotalLines()).append("</totalLines>\n");
            sb.append("    <nonEmptyLines>").append(s.getNonEmptyLines()).append("</nonEmptyLines>\n");
            sb.append("    <commentLines>").append(s.getCommentLines()).append("</commentLines>\n");
            sb.append("  </fileType>\n");
        }

        sb.append("</fileStatistics>");
        return sb.toString();
    }
}