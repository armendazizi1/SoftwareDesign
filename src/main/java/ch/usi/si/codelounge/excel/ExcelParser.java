package ch.usi.si.codelounge.excel;


import ch.usi.si.codelounge.commandline.ParsedLine;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.AreaPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.formula.ptg.Ref3DPxg;
import org.apache.poi.ss.formula.ptg.RefPtg;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The ExcelParser program implements an application that
 * given a cell of an excel sheet traverses the whole cell tree
 * and outputs the content of the cells it found.
 *
 * @author Gloria Sassone, Armend Azizi
 * @version 1.0
 * @since December 2018
 */

public class ExcelParser {

    private  System.Logger LOGGER = System.getLogger(ExcelParser.class.getName());

    /**
     * counts the number of cells traversed
     */
    private int cellOutputCounter = 1;

    /**
     * The program traverses a cell only once,
     * so we need to keep track which cell
     * we have visited and not visited.
     */
    private ArrayList<ParserCell> notVisited = new ArrayList<>();
    private HashSet<ParserCell> visited = new LinkedHashSet<>();


    /**
     * This method is used to traverse a cell.
     *
     * @param workbook   This is the first parameter to traverseCell method
     * @param parserCell This is the second parameter to traverseCell method composed of
     *                   a sheetName and cellName
     * @return Nothing.
     */

    private void traverseCell(Workbook workbook, ParserCell parserCell) {
        // Create a cell ref from a string representation.
        CellReference cellReference = new CellReference(parserCell.getCellName());
        Sheet sheet = workbook.getSheet(parserCell.getSheetName());
        Row row = sheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());

        visited.add(parserCell);

        System.out.println((cellOutputCounter++) + ": " + parserCell.getSheetName() + "\t" + parserCell.getCellName() + "= " + cell);

        if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
            return;
        }

        /**
         * The FormulaParsingWorkbook class parses a formula string into a
         * List of tokens in RPN order.
         *
         */
        final FormulaParsingWorkbook fpb;
        fpb = XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);

        // Parse a formula into an array of tokens
        final int sheetIndex = 0;
        Ptg[] ptgs = FormulaParser.parse(cell.getCellFormula(), fpb, FormulaType.NAMEDRANGE, sheetIndex);

        for (Ptg value : ptgs) {
            if (isRangeRef(value)) {
                addRangedCells(sheet, (AreaPtg) value);
                continue;
            }

            if (isRefWithSheet(value)) {
                addRefCells(value);
                continue;
            }

            if (isSingleRef(value)) {
                addSingleRef(value.toFormulaString(), sheet.getSheetName());
            }
        }
    }

    private  void addSingleRef(String s, String sheetName) {
        addNotVisitedCell(new ParserCell(s, sheetName));
    }

    private  void addRefCells(Ptg value) {
        StringTokenizer tokenizer = new StringTokenizer(value.toFormulaString(), "!");
        String sheetName = tokenizer.nextToken();
        String cellName = tokenizer.nextToken();
        if (sheetName.charAt(0) == '\'') {
            sheetName = sheetName.substring(1, sheetName.length() - 1);
        }
        addSingleRef(cellName, sheetName);
    }

    private  void addRangedCells(Sheet sheet, AreaPtg value) {
        List<ParserCell> rangeDependentCells = parseCellRange(sheet, value);
        rangeDependentCells.forEach(this::addNotVisitedCell);
    }


    /**
     * Add cell to the not Visited list
     * only if it has not been visited before.
     */
    private void addNotVisitedCell(ParserCell cell) {
        if (!visited.contains(cell) && !notVisited.contains(cell)) {
            notVisited.add(cell);
        }
    }

    // Check if token takes in a String representation of a cell reference
    private  boolean isSingleRef(Ptg ptg) {
        return ptg instanceof RefPtg;
    }

    // Check if token defines a cell in an external or different sheet.
    private  boolean isRefWithSheet(Ptg ptg) {
        return ptg instanceof Ref3DPxg;
    }

    // Check if token contains structured reference. e.g SUM(A1:B4)
    private  boolean isRangeRef(Ptg ptg) {
        return ptg instanceof AreaPtg;
    }

    /**
     * This method is used to Parse a structured reference.
     * example cell = SUM(A1:B4) will be parsed to: A1,A2,A3,A4,B1,
     * B2,B3,B4
     *
     * @return List of cells from the range.
     */

     private List<ParserCell> parseCellRange(Sheet sheet, AreaPtg areaPtg) {
        List<ParserCell> cells = new ArrayList<>();

        CellRangeAddress region = CellRangeAddress.valueOf(areaPtg.toFormulaString());

        for (int r = region.getFirstRow(); r <= region.getLastRow(); r++) {
            Row ro = sheet.getRow(r);
            for (int c = region.getFirstColumn(); c <= region.getLastColumn(); c++) {
                Cell regionCell = ro.getCell(c);
                ParserCell parserCell = new ParserCell(regionCell.getAddress().toString(), sheet.getSheetName());
                cells.add(parserCell);
            }
        }

        return cells;
    }

    public void parse(ParsedLine parsedLine) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(new File(parsedLine.getFilepath()));
        Sheet sheet = workbook.getSheet(parsedLine.getSheetName());
        ParserCell initialCell = new ParserCell(parsedLine.getCellName(), sheet.getSheetName());

        printInfo(workbook, initialCell);
        addNotVisitedCell(initialCell);

        /*
            Starting from the given cell (e.g "A") start traversing all the cells that cell A depends on.
        */
        while (!notVisited.isEmpty()) {
            ParserCell parserCell = notVisited.get(notVisited.size() - 1);
            traverseCell(workbook, parserCell);
            notVisited.remove(parserCell);
        }

    }

    private void printInfo(Workbook workbook, ParserCell initialCell) {
        LOGGER.log(System.Logger.Level.INFO, "Workbook has " + workbook.getNumberOfSheets() + " Sheets");

        for (Sheet sheets : workbook) {
            LOGGER.log(System.Logger.Level.INFO, "=> " + sheets.getSheetName());
        }

        System.out.println("Starting cell name " + initialCell.toString());
    }
}
