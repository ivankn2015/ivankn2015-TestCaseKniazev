package org.example.model;

import java.util.HashMap;
import java.util.Map;

public enum FileType implements CommentDetector {
    JAVA(new String[]{"java"}, new String[]{"//", "/*", "*"}),
    SHELL(new String[]{"sh", "bash"}, new String[]{"#"}),
    PYTHON(new String[]{"py"}, new String[]{"#"}),
    XML(new String[]{"xml", "html", "htm"}, new String[]{"<!--"}),
    C_FAMILY(new String[]{"c", "cpp", "h", "cs"}, new String[]{"//", "/*", "*"}),
    JAVASCRIPT(new String[]{"js", "ts"}, new String[]{"//", "/*", "*"}),
    RUBY(new String[]{"rb"}, new String[]{"#"}),
    PERL(new String[]{"pl"}, new String[]{"#"}),
    UNKNOWN(new String[]{}, new String[]{});

    private final String[] extensions;
    private final String[] commentPrefixes;
    private static final Map<String, FileType> EXTENSION_MAP = new HashMap<>();

    FileType(String[] extensions, String[] commentPrefixes) {
        this.extensions = extensions;
        this.commentPrefixes = commentPrefixes;
    }

    static {
        for (FileType type : values()) {
            for (String ext : type.extensions) {
                EXTENSION_MAP.put(ext.toLowerCase(), type);
            }
        }
    }

    public static FileType fromExtension(String extension) {
        return EXTENSION_MAP.getOrDefault(extension.toLowerCase(), UNKNOWN);
    }

    public boolean isCommentLine(String line) {
        String trimmed = line.trim();
        for (String prefix : commentPrefixes) {
            if (trimmed.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}