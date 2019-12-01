package ch.usi.si.codelounge.commandline;

import ch.usi.si.codelounge.util.StringUtil;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandLineParser implements ch.usi.si.codelounge.util.Parser<ParsedLine> {

    private static Logger LOGGER = LogManager.getLogger(CommandLineParser.class.getName());
   private ParsedLine parsedLine;
   private final String[] commandLineArgs;

   public CommandLineParser(String[] args) {
       this.commandLineArgs = args;
   }

    @Override
    public ParsedLine tryParse() {
        // Create GNU like options
        Options gnuOptions =
            new Options()
                .addOption("f", "filepath", true, "Path of the excel file")
                .addOption("s", "sheetname", true, "Name of the sheet(surround by single-quotes)")
                .addOption("c", "cellname", true, "Name of the cell");

        org.apache.commons.cli.CommandLineParser gnuParser = new GnuParser();
        CommandLine cmd = null;

        try {
            cmd = gnuParser.parse(gnuOptions, getCommandLineArgs());
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }

        if (cmd == null) {
            return new ParsedLine(
                StringUtil.EMPTY, StringUtil.EMPTY, StringUtil.EMPTY, gnuOptions.toString());
        }

        return new ParsedLine(
            cmd.getOptionValue("f"),
            cmd.getOptionValue("s"),
            cmd.getOptionValue("c"),
            gnuOptions.toString());
    }

    private String[] getCommandLineArgs() {
        return commandLineArgs;
    }
}
