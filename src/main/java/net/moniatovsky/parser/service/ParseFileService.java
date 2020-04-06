package net.moniatovsky.parser.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Stream;
import net.moniatovsky.parser.model.DirectoryReport;
import net.moniatovsky.parser.model.FileReport;
import net.moniatovsky.parser.util.ParserUtils;

/*****
 * This is a test program with 5 lines of code
 *  \/* no nesting allowed!
 //*****/

/***/// Slightly pathological comment ending...

public class ParseFileService {

  private static final String OPEN_COMMENT_SIGN = "/*";
  private static final String CLOSE_COMMENT_SIGN = "*/";
  private static final String BLOCK_COMMENT_IN_ONE_LINE_REGEX = "/\\*.*?\\*/";
  private static final String COMMENTS_INSIDE_STRING_REGEX = "\"(.*[/\\*/\\*//])\"";
  private static final String ONE_LINE_COMMENT_REGEX = "//.*";
  private static final String SPACES_TABS_REGEX = "[\\t ]";

  public int getLinesOfCodeCount(File file) {
    return new FileProcessor().processFile(file);
  }

  public DirectoryReport buildDirectoryReport(File directory, int level) {
    DirectoryReport directoryReport = DirectoryReport.of(directory.getName(), level);
    File[] directoryFiles = directory.listFiles();
    if (directoryFiles != null) {
      Stream.of(directoryFiles).forEach(file -> {
        if (file.isDirectory()) {
          directoryReport.addDirectory(buildDirectoryReport(file, level + 1));
        } else {
          if (!ParserUtils.isJavaFile(file)) {
            System.err.println("WARN: file " + file.getName()
                + " extension is not .java, skipping file processing");
          } else {
            int linesOfCodeCount = getLinesOfCodeCount(file);
            directoryReport.addFile(FileReport.of(file.getName(), linesOfCodeCount));
          }
        }
      });
    }
    return directoryReport;
  }

  private static class FileProcessor {

    private boolean isCommentOngoing = false;

    private int processFile(File file) {
      int total = 0;
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
          line = line.replaceAll(SPACES_TABS_REGEX, "")
              .replaceAll(COMMENTS_INSIDE_STRING_REGEX, "\"some_string\"");
          if (isCodeLine(line)) {
            total++;
          }
        }
      } catch (Exception e) {
        System.err.println("Smth went wrong parsing file " + file.getName());
        e.printStackTrace();
      }
      if (isCommentOngoing) {
        System.err.println("File " + file.getName() + " contains not closed comment");
      }
      return total;
    }

    private boolean isCodeLine(String line) {
      if (!isCommentOngoing) {
        line = line.replaceAll(BLOCK_COMMENT_IN_ONE_LINE_REGEX, "")
            .replaceAll(ONE_LINE_COMMENT_REGEX, "");
        if (line.length() == 0) {
          return false;
        }
        int indexOfOpenCommentSign = line.indexOf(OPEN_COMMENT_SIGN);
        if (indexOfOpenCommentSign == -1) {
          return true;
        }
        isCommentOngoing = true;
        return indexOfOpenCommentSign > 0;
      } else {
        int indexOfCloseCommentSign = line.indexOf(CLOSE_COMMENT_SIGN);
        if (indexOfCloseCommentSign == -1) {
          return false;
        }
        line = line.substring(indexOfCloseCommentSign + 2);
        isCommentOngoing = false;
        return isCodeLine(line);
      }
    }
  }
}

