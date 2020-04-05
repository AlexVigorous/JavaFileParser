package net.moniatovsky.parser.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class DirectoryReport {

    private String name;
    private int level;
    private long totalLinesCount = 0L;
    private List<DirectoryReport> directories = new ArrayList<>();
    private List<FileReport> files = new ArrayList<>();

    private DirectoryReport(final String name, final int level) {
        this.name = name;
        this.level = level;
    }

    public static DirectoryReport of(final String name, final int level) {
        return new DirectoryReport(name, level);
    }

    public void addDirectory(DirectoryReport directoryReport) {
        directories.add(directoryReport);
        totalLinesCount = totalLinesCount + directoryReport.getTotalLinesCount();
    }

    public void addFile(FileReport fileReport) {
        files.add(fileReport);
        totalLinesCount = totalLinesCount + fileReport.getLinesCount();
    }
}
