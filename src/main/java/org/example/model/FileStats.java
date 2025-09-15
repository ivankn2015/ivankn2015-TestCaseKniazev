package org.example.model;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;


public class FileStats {
    @Getter
    private final String extension;
    private final AtomicLong fileCount = new AtomicLong(0);
    private final AtomicLong totalSize = new AtomicLong(0);
    private final AtomicLong totalLines = new AtomicLong(0);
    private final AtomicLong nonEmptyLines = new AtomicLong(0);
    private final AtomicLong commentLines = new AtomicLong(0);

    public FileStats(String extension) {
        this.extension = extension;
    }

    public void addFile(long size, long lines, long nonEmpty, long comments) {
        fileCount.incrementAndGet();
        totalSize.addAndGet(size);
        totalLines.addAndGet(lines);
        nonEmptyLines.addAndGet(nonEmpty);
        commentLines.addAndGet(comments);
    }

    public void merge(FileStats other) {
        fileCount.addAndGet(other.fileCount.get());
        totalSize.addAndGet(other.totalSize.get());
        totalLines.addAndGet(other.totalLines.get());
        nonEmptyLines.addAndGet(other.nonEmptyLines.get());
        commentLines.addAndGet(other.commentLines.get());
    }

    public long getFileCount() { return fileCount.get(); }
    public long getTotalSize() { return totalSize.get(); }
    public long getTotalLines() { return totalLines.get(); }
    public long getNonEmptyLines() { return nonEmptyLines.get(); }
    public long getCommentLines() { return commentLines.get(); }
}