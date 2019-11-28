package ch.usi.si.codelounge.commandline;

import ch.usi.si.codelounge.util.StringUtil;
import org.apache.commons.cli.*;

public class Parser {

  public ParsedLine parse(String[] args) {

    // Create GNU like options
    Options gnuOptions =
        new Options()
            .addOption("f", "filepath", true, "Path of the excel file")
            .addOption("s", "sheetname", true, "Name of the sheet(surround by single-quotes)")
            .addOption("c", "cellname", true, "Name of the cell");

    CommandLineParser gnuParser = new GnuParser();
    CommandLine cmd;

    try {
      cmd = gnuParser.parse(gnuOptions, args);
    } catch (ParseException e) {
      e.printStackTrace();
      return new ParsedLine(
          StringUtil.EMPTY, StringUtil.EMPTY, StringUtil.EMPTY, gnuOptions.toString());
    }

    return new ParsedLine(
        cmd.getOptionValue("f"),
        cmd.getOptionValue("s"),
        cmd.getOptionValue("c"),
        gnuOptions.toString());
  }
}
