package com.nashtech.automation.excelhelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.nashtech.automation.logger.Log;

public class ExcelHelper {

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 0;
			int totalRows = ExcelWSheet.getLastRowNum();
			XSSFRow row = ExcelWSheet.getRow(0);
			int totalCols = row.getLastCellNum();
			tabArray = new String[totalRows][totalCols];
			for (int i = startRow; i <= totalRows; i++) {
				for (int j = startCol; j < totalCols; j++) {
					tabArray[i - 1][j] = getCellData(i, j, ExcelWSheet);
				}
			}
		} catch (FileNotFoundException e) {
			Log.error("Could not read the Excel file: [" + FilePath + "] - sheet: [" + SheetName + "]");
			Log.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("Could not read the Excel file: [" + FilePath + "] - sheet: [" + SheetName + "]");
			Log.error(e.toString());
			e.printStackTrace();
		}
		return (tabArray);
	}

	public static String getCellData(int rowNum, int colNum, XSSFSheet ExcelWSheet) throws Exception {
		try {
			XSSFCell Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
			int dataType = Cell.getCellType();
			if (dataType == 3) {
				return "";
			} else {
				DataFormatter formatter = new DataFormatter();
				String CellData = formatter.formatCellValue(Cell);
				return CellData;
			}
		} catch (Exception e) {
			// add log error for get cell data
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	/**
	 * This function is used to get data of a sheet then store as a list of
	 * dictionary array
	 * 
	 * @param excelSheetName
	 *            The Sheet name
	 * @return The 2D arrays and each element is a dictionary
	 * @throws Exception
	 *             Throw an Exception if can't get file data
	 */
	public static HashMap<String, String> getDictionaryDataFromRow(int rowNumber, XSSFSheet ExcelWSheet) throws Exception {
		HashMap<String, String> rowData = new HashMap<String, String>();
		XSSFRow row = ExcelWSheet.getRow(0);
		int totalCols = row.getLastCellNum();
		try {
			for(int i = 0;i<totalCols;i++) {
				rowData.put(getCellData(0,i,ExcelWSheet), getCellData(rowNumber,i,ExcelWSheet));
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
			throw e;
		}

		return rowData;
	}
	
	public static Object[][] getTableToHashMap(String FilePath, String SheetName) throws Exception {
		Object[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 0;
			int totalRows = ExcelWSheet.getLastRowNum();
			XSSFRow row = ExcelWSheet.getRow(0);
			int totalCols = row.getLastCellNum();
			tabArray = new Object[totalRows][1];
			for (int i = startRow; i <= totalRows; i++) {
				tabArray[i - 1][0] = getDictionaryDataFromRow(i,ExcelWSheet);
				
			}
		} catch (FileNotFoundException e) {
			Log.error("Could not read the Excel file: [" + FilePath + "] - sheet: [" + SheetName + "]");
			Log.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("Could not read the Excel file: [" + FilePath + "] - sheet: [" + SheetName + "]");
			Log.error(e.toString());
			e.printStackTrace();
		}
		return (tabArray);
	}

}
