package net.moniatovsky.parser.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class FileReport {

  private String fileName;
  private int linesCount;

}
