package ch.usi.si.codelounge;

import ch.usi.si.codelounge.commandline.ParsedLine;
import ch.usi.si.codelounge.commandline.Parser;
import ch.usi.si.codelounge.excel.ExcelParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

class ParserManager {

  private static Logger LOGGER = LogManager.getLogger(ParserManager.class.getName());

  void parse(String[] commandlineArgs) {

    Parser cmdParser = new Parser();
    ParsedLine line = cmdParser.parse(commandlineArgs);

    LOGGER.info(line);

    if (line.hasError()) {
      LOGGER.error("Could not parse command line arguments");
      LOGGER.error(line.getHelpMsg());
      return;
    }

    ExcelParser excelParser = new ExcelParser();
    try {
      excelParser.parse(line);
    } catch (IOException | InvalidFormatException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
