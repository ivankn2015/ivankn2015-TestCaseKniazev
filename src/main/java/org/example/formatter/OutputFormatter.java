package org.example.formatter;

import org.example.model.FileStats;

import java.util.Map;

public interface OutputFormatter {
    String format(Map<String, FileStats> stats);
}