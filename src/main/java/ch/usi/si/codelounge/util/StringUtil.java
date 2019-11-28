package ch.usi.si.codelounge.util;

public class StringUtil {

    public static boolean isNotEmpty(final String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     *  The empty String "".
     */
    public final static String EMPTY = "";
}
