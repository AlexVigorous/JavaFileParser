package net.moniatovsky.parser;

import java.io.File;
import java.util.Scanner;
import net.moniatovsky.parser.model.DirectoryReport;
import net.moniatovsky.parser.model.FileReport;
import net.moniatovsky.parser.service.ParseFileService;
import net.moniatovsky.parser.service.PrintReportService;

public class ApplicationLauncher {

  public static void main(String[] args) {
    ParseFileService fileParserService = new ParseFileService();
    PrintReportService printerService = new PrintReportService();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter path to file or directory:");
    File file = new File(scanner.nextLine());
    while (!file.exists()) {
      System.err.println("File or directory doesn't exists");
      System.out.println("Enter path to file or directory:");
      file = new File(scanner.nextLine());
    }
    if (file.isFile()) {
      int linesOfCodeCount = fileParserService.getLinesOfCodeCount(file);
      printerService.printFileReport(FileReport.of(file.getName(), linesOfCodeCount), 0);
      return;
    }
    DirectoryReport directoryReport = fileParserService.buildDirectoryReport(file, 0);
    printerService.printDirectoryReport(directoryReport
    );
  }

}
