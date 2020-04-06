package net.moniatovsky.parser.service;

import net.moniatovsky.parser.model.DirectoryReport;
import net.moniatovsky.parser.model.FileReport;
import net.moniatovsky.parser.util.ParserUtils;

public class PrintReportService {

  public void printFileReport(FileReport fileReport, int level) {
    System.out.println(
        ParserUtils.getTabsString(level) + fileReport.getFileName() + " : " + fileReport
            .getLinesCount());
  }

  public void printDirectoryReport(DirectoryReport directoryReport) {
    System.out
        .println(ParserUtils.getTabsString(directoryReport.getLevel()) + directoryReport.getName() +
            " : " + directoryReport.getTotalLinesCount());
    directoryReport.getFiles()
        .forEach(fileReport -> printFileReport(fileReport, directoryReport.getLevel() + 1));
    directoryReport.getDirectories().forEach(this::printDirectoryReport);
  }

}
