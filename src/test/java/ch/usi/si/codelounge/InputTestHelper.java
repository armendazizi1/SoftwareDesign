package ch.usi.si.codelounge;

import java.io.File;

public class InputTestHelper {

    public static String[] generate(String filename, String sheet, String cell) {
        // maven automatically sets the current directory
        String filepath = new File("src/test/data/" + filename).getAbsolutePath();

        return new String[]{"-f",
            filepath,
            "-s",
            String.format("\"%s\"", sheet),
            "-c",
            cell};
    }

    /**
     * @param cell     The name of the cell, e.g. A2
     * @return The command line arguments to invoke the main() method
     */
    public static String[] generate(String cell) {
        return generate("test-example.xlsx", "Revenue-Model", cell);
    }

    /**
     * @param cell     The name of the cell, e.g. A2
     * @return The command line arguments to invoke the main() method
     */
    public static String[] generate(String filename, String cell) {
        return generate(filename, "Revenue-Model", cell);
    }
}
