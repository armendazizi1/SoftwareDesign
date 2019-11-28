package ch.usi.si.codelounge.commandline;
import ch.usi.si.codelounge.util.StringUtil;

public class ParsedLine {


    private final String filepath;
    private final String sheetName;
    private final String cellName;

    public ParsedLine(String filepath, String sheetName, String cellName) {
        this.filepath = filepath;
        this.sheetName = sheetName;
        this.cellName = cellName;
    }

    public boolean hasError() {
        return StringUtil.isNotEmpty(getFilepath()) &&
            StringUtil.isNotEmpty(getSheetName()) &&
            StringUtil.isNotEmpty(getCellName());
    }

    public String getFilepath() {
        return filepath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getCellName() {
        return cellName;
    }

    @Override
    public String toString() {
        return "filepath: " + getFilepath() + System.lineSeparator() +
            "sheetName: " + getSheetName() + System.lineSeparator() +
            "cellName: " + getCellName() + System.lineSeparator();
    }
}
