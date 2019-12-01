package ch.usi.si.codelounge;

import ch.usi.si.codelounge.commandline.CommandLineParser;
import ch.usi.si.codelounge.commandline.ParsedLine;
import ch.usi.si.codelounge.excel.ExcelParser;
import ch.usi.si.codelounge.util.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserManager implements Parser<Boolean> {

  private static Logger LOGGER = LogManager.getLogger(ParserManager.class.getName());
    private final Parser<ParsedLine> cmdParser;

  public ParserManager(String[] commandlineArgs) {
      cmdParser = new CommandLineParser(commandlineArgs);
  }

  public Boolean tryParse() {
    ParsedLine line = cmdParser.tryParse();

    LOGGER.info(line);

    if (line.hasError()) {
      LOGGER.error("Could not parse command line arguments");
      LOGGER.error(line.getHelpMsg());
      return false;
    }

    Parser<Boolean> excelParser = new ExcelParser(line);
    return excelParser.tryParse();
  }
}
