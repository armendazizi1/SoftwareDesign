package ch.usi.si.codelounge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class IOTestHelper {
  private final static PrintStream systemOut = System.out;

  private ByteArrayOutputStream testOut;

  IOTestHelper() {
    testOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(testOut));
  }

  public void restoreSystemInputOutput() {
    System.setOut(systemOut);
  }

  public String getOutput() {
    try {
      return testOut.toString();
    } finally {
      restoreSystemInputOutput();
    }
  }
}
