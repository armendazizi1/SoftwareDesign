package ch.usi.si.codelounge;




import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.cli.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.AreaPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The ExcelParser program implements an application that
 * given a cell of an excel sheet traverses the whole cell tree
 * and outputs the content of the cells it found.
 *
 * @author  Gloria Sassone, Armend Azizi
 * @version 1.0
 * @since   December 2018
 */

public class ExcelParser {

    public static int counter = 1;

    /**
     * The program traverses a cell only once,
     * so we need to keep track which cell
     * we have visited and not visited.
     *
     */
    private static ArrayList<ParserCell> notVisited = new ArrayList<>();
    private static HashSet<ParserCell> visited = new LinkedHashSet<>();


    /**
     * This method is used to traverse a cell.
     * @param workbook This is the first parameter to traverseCell method
     * @param parserCell  This is the second parameter to traverseCell method composed of
     *                    a sheetName and cellName
     * @return Nothing.
     */

    public static void traverseCell(Workbook workbook, ParserCell parserCell) {

       // Create a cell ref from a string representation.
        CellReference cellReference = new CellReference(parserCell.getCellName());
        Sheet sheet = workbook.getSheet(parserCell.getSheetName());
        Row row = sheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());


        visited.add(parserCell);


        System.out.println((counter++) + ": " + parserCell.getSheetName() + "\t" + parserCell.getCellName() + "= " + cell);

        if (!(cell.getCellType() == Cell.CELL_TYPE_FORMULA)) {
            return;
        }


        /**
         * This class parses a formula string into a
         * List of tokens in RPN order.
         *
         */
        final FormulaParsingWorkbook fpb;
        fpb = XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);

        // Parse a formula into an array of tokens
        Ptg[] ptg = FormulaParser.parse(cell.getCellFormula(), fpb, FormulaType.NAMEDRANGE, 0);


        for (int i = 0; i < ptg.length; i++) {
            if (ptg[i].getClass() == org.apache.poi.ss.formula.ptg.AreaPtg.class) {
                List<ParserCell> rangeDependentCells = parseCellRange(sheet, (AreaPtg) ptg[i]);
                for (ParserCell pc : rangeDependentCells) {
                    addNotVisitedCell(pc);
                }
            }

            else if (ptg[i].getClass() == org.apache.poi.ss.formula.ptg.RefPtg.class || ptg[i].getClass() == org.apache.poi.ss.formula.ptg.Ref3DPxg.class) {
                if (ptg[i].getClass() == org.apache.poi.ss.formula.ptg.Ref3DPxg.class) {
                    StringTokenizer st1 = new StringTokenizer(ptg[i].toFormulaString(), "!");
                    String s1 = st1.nextToken();
                    String c1 = st1.nextToken();
                    if (s1.charAt(0) == '\'') {
                        s1 = s1.substring(1, s1.length() - 1);
                    }
                    addNotVisitedCell(new ParserCell(c1, s1));
                } else {
                    addNotVisitedCell(new ParserCell(ptg[i].toFormulaString(), sheet.getSheetName()));
                }
            }
        }
    }




    /**
     * Add cell to the not Visited list
     * only if it has not been visited before.
     *
     */
    public static void addNotVisitedCell(ParserCell cell) {
        if (!visited.contains(cell) && !notVisited.contains(cell)) {
            notVisited.add(cell);
        }
    }



    /**
     * This method is used to Parse a structured reference.
     * example cell = SUM(A1:B4) will be parsed to: A1,A2,A3,A4,B1,
     * B2,B3,B4
     * @return List of cells from the range.
     */

    static List<ParserCell> parseCellRange(Sheet sheet, AreaPtg areaPtg) {
        List<ParserCell> cells = new ArrayList<>();

        CellRangeAddress region = CellRangeAddress.valueOf(areaPtg.toFormulaString());

        for (int r = region.getFirstRow(); r <= region.getLastRow(); r++) {
            Row ro = sheet.getRow(r);
            Cell regionCell = null;
            for (int c = region.getFirstColumn(); c <= region.getLastColumn(); c++) {
                if (ro.getCell(c)!= null) {
                    regionCell = ro.getCell(c);
                }
                ParserCell parserCell = new ParserCell(regionCell.getAddress().toString(), sheet.getSheetName());
                cells.add(parserCell);
            }
        }

        return cells;
    }


    /**
     * This is the main method which makes use of traverseCell method.
     * @param args and command options.
     * @return Nothing.
     * @exception IOException On input error
     * @exception  ParseException
     * @exception InvalidFormatException
     * @see IOException
     */

    public static void main(String[] args) throws ParseException, IOException, InvalidFormatException {

        String SAMPLE_XLSX_FILE_PATH = "";


        //***Definition Stage***
        // create Options object
        //Create GNU like options
        Options gnuOptions = new Options();


        gnuOptions.addOption("f", "filepath", true, "Path of the excel file");
        gnuOptions.addOption("s", "sheetname", true, "Name of the sheet(surround by single-quotes)");
        gnuOptions.addOption("c", "cellname", true, "Name of the cell");

        CommandLineParser gnuParser = new GnuParser();
        CommandLine cmd = gnuParser.parse(gnuOptions, args);


        // A formatter of help messages for command line options.
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ExcelParser", gnuOptions);

        if (cmd.hasOption("f")) {
            System.out.println("file path passed: " + cmd.getOptionValue("f"));
            SAMPLE_XLSX_FILE_PATH = cmd.getOptionValue("f");
        }

        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));


            String sheetName = "";
            if (cmd.hasOption("s")) {
                System.out.println("sheet name passed: " + cmd.getOptionValue("s"));
                sheetName = cmd.getOptionValue("s");
            }

            // Getting the Sheet by its name
            Sheet sheet = workbook.getSheet(sheetName);

            String cellName = "";
            if (cmd.hasOption("c")) {
                System.out.println("cell name passed: " + cmd.getOptionValue("c"));
                cellName = cmd.getOptionValue("c");
            }


            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            System.out.println("Retrieving Sheets using for-each loop");
            for (Sheet sheets : workbook) {
                System.out.println("=> " + sheets.getSheetName());
            }


            ParserCell initialCell = new ParserCell(cellName, sheet.getSheetName());
            addNotVisitedCell(initialCell);


            System.out.println("Starting cell name " + initialCell.toString());


            /**
             * Starting from the given cell (e.g "A")
             * start traversing all the cells that
             * cell A depends on.
             *
             */
            while (!notVisited.isEmpty()) {
                ParserCell parserCell = notVisited.get(notVisited.size() - 1);
                traverseCell(workbook, parserCell);
                notVisited.remove(parserCell);
            }

//          TODO:  workbook.close(); should be called?? but modifies file somehow..
        }
}
