package ch.usi.si.codelounge;

import ch.usi.si.codelounge.commandline.ParsedLine;
import ch.usi.si.codelounge.commandline.Parser;
import ch.usi.si.codelounge.excel.ExcelParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class ParserManager {

    private static System.Logger LOGGER = System.getLogger(ParserManager.class.getName());

    public void parse(String[] commandlineArgs) {

        Parser cmdParser = new Parser();
        ParsedLine line = cmdParser.parse(commandlineArgs);

        LOGGER.log(System.Logger.Level.INFO, line);

        if (line.hasError()) {
            LOGGER.log(System.Logger.Level.ERROR, "Could not parse command line arguments");
            LOGGER.log(System.Logger.Level.INFO, line.getHelpMsg());
            return;
        }

        ExcelParser excelParser = new ExcelParser();
        try {
            excelParser.parse(line);
        } catch (IOException | InvalidFormatException e) {
            LOGGER.log(System.Logger.Level.ERROR, e.getMessage());
        }

    }

}
