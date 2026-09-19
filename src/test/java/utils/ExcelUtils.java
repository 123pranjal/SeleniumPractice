package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static final String FILE_PATH = "src/test/resources/LoginData.xlsx";

    // Reads all rows (except header) into a 2D Object array for @DataProvider
    public static Object[][] getLoginData() throws IOException {
        FileInputStream fis = new FileInputStream(FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum(); // excludes header automatically since header is row 0
        Object[][] data = new Object[rowCount][3];

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue(); // Username
            data[i - 1][1] = row.getCell(1).getStringCellValue(); // Password
            data[i - 1][2] = row.getCell(2).getStringCellValue(); // ExpectedMessage
        }

        workbook.close();
        fis.close();
        return data;
    }

    // Writes the actual result + pass/fail back into a 4th column
    public static void writeResult(int rowIndex, String actualMessage, String status) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        fis.close();

        Row row = sheet.getRow(rowIndex + 1); // +1 to skip header row
        Cell actualCell = row.createCell(3);
        actualCell.setCellValue(actualMessage);

        Cell statusCell = row.createCell(4);
        statusCell.setCellValue(status);

        FileOutputStream fos = new FileOutputStream(FILE_PATH);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
}