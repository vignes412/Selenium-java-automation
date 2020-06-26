package ExcelControl;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public  boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK.getCode())
                return false;
        }
        return true;
    }
    public ArrayList<String> GetAllRowDataFromColumn(String sheetName,
                                                     String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workBook.getSheet(sheetName);
            int startingColumnNumber = -1;
            int endColumnNumber = -1;
            ArrayList<String> TempArray = new ArrayList<String>();
            Row row = sheet.getRow(0);
            if (!isRowEmpty(row)) {
                startingColumnNumber=0;
                endColumnNumber=row.getLastCellNum();
//            for (int i = 0; i < row.getLastCellNum(); i++) {
//                if (row.getCell(i).getStringCellValue().trim().equals(startColumnName.trim()))
//                    startingColumnNumber = i;
//
//                if (row.getCell(i).getStringCellValue().trim().equals(endColumnName.trim())) {
//                    endColumnNumber = i;
//                    break;
//                }
//            }
                int totalRecord = sheet.getLastRowNum();
                DataFormatter fmt = new DataFormatter();
                for (int j = 1; j <= totalRecord; j++) {
                    StringBuilder sb = new StringBuilder();
                    Row row1 = sheet.getRow(j);
                    for (int i = startingColumnNumber; i <= endColumnNumber; i++) {
                        sb.append(fmt.formatCellValue(row1.getCell(i)) + (endColumnNumber == i ? "" : "~"));
                    }
                    sb.append("\n");
                    TempArray.add(sb.toString());
                }
                return TempArray;
            }
            return null;
        } catch(Exception e){
                System.out.print(e.getMessage());
            }
        return null;
    }
}
