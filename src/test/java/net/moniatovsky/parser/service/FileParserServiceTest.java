package net.moniatovsky.parser.service;

import net.moniatovsky.parser.model.DirectoryReport;
import net.moniatovsky.parser.model.FileReport;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FileParserServiceTest {

    private FileParserService fileParserService = new FileParserService();

    @Test
    public void testDirectory() {
        File directory = new File("src/test/resources/testDir");
        DirectoryReport expectedRootReport = DirectoryReport.of("testDir", 0);
        expectedRootReport.addFile(FileReport.of("Test1.java", 3));
        DirectoryReport expectedSubReport = DirectoryReport.of("subDir", 1);
        expectedSubReport.addFile(FileReport.of("Test3.java", 5));
        expectedSubReport.addFile(FileReport.of("Test2.java", 6));
        expectedRootReport.addDirectory(expectedSubReport);
        DirectoryReport resultReport = fileParserService.buildDirectoryReport(directory, 0);
        Assert.assertEquals("Result report should be equal to expected report", expectedRootReport, resultReport);
    }
}