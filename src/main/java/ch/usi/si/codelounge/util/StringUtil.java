package ch.usi.si.codelounge.util;

public class StringUtil {

  /** The empty String "". */
  public static final String EMPTY = "";

  private StringUtil() {}

  public static boolean isNotEmpty(final String s) {
    return s != null && !s.trim().isEmpty();
  }
}
