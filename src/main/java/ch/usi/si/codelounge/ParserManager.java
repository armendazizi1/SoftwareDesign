package ch.usi.si.codelounge;

import ch.usi.si.codelounge.commandline.ParsedLine;
import ch.usi.si.codelounge.commandline.Parser;

import java.util.logging.Logger;

public class ParserManager {

    private static System.Logger LOGGER = System.getLogger(ParserManager.class.getName());

    public void parse(String[] commandlineArgs) {

        Parser parser = new Parser();
        ParsedLine line = parser.parse(commandlineArgs);

        LOGGER.log(System.Logger.Level.INFO, line);

        if (line.hasError()) {
            LOGGER.log(System.Logger.Level.ERROR, "Could not parse command line arguments");
            return;
        }


    }

}
