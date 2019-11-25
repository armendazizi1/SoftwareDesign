package ch.usi.si.codelounge;

/**
 * The ParserCell class represents a cell
 * with its name and the name of the sheet
 *
 * @author  Gloria Sassone, Armend Azizi
 * @since   December 2018
 */

class ParserCell {
    private String cellName;
    private String sheetName;

    public ParserCell(String cellName, String sheetName) {
        super();
        this.cellName = cellName.replace("$", "");
        this.sheetName = sheetName;
    }

    public String getCellName() {
        return cellName;
    }

    public String getSheetName() {
        return sheetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParserCell that = (ParserCell) o;

        if (!getCellName().equals(that.getCellName())) return false;
        return getSheetName().equals(that.getSheetName());
    }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = getCellName().hashCode();
            return prime * result + getSheetName().hashCode();
        }

    @Override
    public String toString() {
        return "ParserCell{" +
                "cellName='" + cellName + '\'' +
                ", sheetName='" + sheetName + '\'' +
                '}';
    }
}
