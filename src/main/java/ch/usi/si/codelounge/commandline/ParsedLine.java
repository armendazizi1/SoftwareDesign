package ch.usi.si.codelounge.commandline;
import ch.usi.si.codelounge.util.StringUtil;

public class ParsedLine {


    private final String filepath;
    private final String sheetName;
    private final String cellName;
    private final String helpMsg;

    public ParsedLine(String filepath, String sheetName, String cellName, String helpMsg) {
        this.filepath = filepath;
        this.sheetName = sheetName;
        this.cellName = cellName;
        this.helpMsg = helpMsg;
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

    public String getHelpMsg() {
        return helpMsg;
    }

    @Override
    public String toString() {
        return "filepath: " + getFilepath() + System.lineSeparator() +
            "sheetName: " + getSheetName() + System.lineSeparator() +
            "cellName: " + getCellName() + System.lineSeparator();
    }


}
