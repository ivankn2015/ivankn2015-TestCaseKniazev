package org.example.parser;

import org.example.config.AppConfig;
import org.example.util.ValidationUtils;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class ArgumentParser {

    public AppConfig parse(String[] args) {
        AppConfig.AppConfigBuilder builder = AppConfig.builder();

        for (String arg : args) {
            if (arg.startsWith("--")) {
                parseOption(arg, builder);
            } else if (!arg.startsWith("-")) {
                builder.path(Paths.get(arg));
            }
        }

        AppConfig config = builder.build();
        config.validate();
        return config;
    }

    private void parseOption(String arg, AppConfig.AppConfigBuilder builder) {
        if (arg.equals("--recursive")) {
            builder.recursive(true);
        } else if (arg.equals("--git-ignore")) {
            builder.useGitIgnore(true);
        } else if (arg.startsWith("--max-depth=")) {
            String value = extractValue(arg);
            if (!value.isEmpty()) {
                builder.maxDepth(Integer.parseInt(value));
            }
        } else if (arg.startsWith("--thread=")) {
            String value = extractValue(arg);
            if (!value.isEmpty()) {
                builder.threadCount(Integer.parseInt(value));
            }
        } else if (arg.startsWith("--include-ext=")) {
            String value = extractValue(arg);
            builder.includeExtensions(parseExtensions(value));
        } else if (arg.startsWith("--exclude-ext=")) {
            String value = extractValue(arg);
            builder.excludeExtensions(parseExtensions(value));
        } else if (arg.startsWith("--output=")) {
            String value = extractValue(arg);
            if (!value.isEmpty()) {
                ValidationUtils.validateOutputFormat(value);
                builder.outputFormat(value);
            }
        }
    }

    private String extractValue(String arg) {
        int equalsIndex = arg.indexOf('=');
        if (equalsIndex == -1) {
            throw new IllegalArgumentException("Invalid argument format: " + arg);
        }
        return arg.substring(equalsIndex + 1);
    }

    private Set<String> parseExtensions(String extString) {
        Set<String> extensions = new HashSet<>();
        if (extString == null || extString.trim().isEmpty()) {
            return extensions;
        }
        for (String ext : extString.split(",")) {
            String trimmed = ext.trim().toLowerCase();
            if (!trimmed.isEmpty()) {
                extensions.add(trimmed);
            }
        }
        return extensions;
    }
}