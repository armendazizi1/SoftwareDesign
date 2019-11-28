package ch.usi.si.codelounge.util;

public class StringUtil {

    /**
     * The empty String "".
     */
    public final static String EMPTY = "";

    public static boolean isNotEmpty(final String s) {
        return s != null && !s.trim().isEmpty();
    }
}
