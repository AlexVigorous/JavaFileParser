package net.moniatovsky.parser.util;

import java.io.File;
import java.util.Optional;

public class ParserUtils {

  public static String getTabsString(int tabCount) {
    StringBuilder tabsString = new StringBuilder();
    while (tabCount > 0) {
      tabsString.append("\t");
      tabCount--;
    }
    return tabsString.toString();
  }

  public static boolean isJavaFile(File file) {
    String fileName = file.getName();
    Optional<String> optionalExtension = Optional.of(fileName)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(fileName.lastIndexOf(".") + 1));
    return "java".equals(optionalExtension.get());
  }

}
