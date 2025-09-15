package org.example.util;

public class FileUtils {

    public static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1 || dotIndex == filename.length() - 1) ?
                "" : filename.substring(dotIndex + 1).toLowerCase();
    }

}