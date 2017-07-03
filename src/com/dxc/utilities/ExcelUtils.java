package com.dxc.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;

	/*
	 * To get all the column values for all the row in the excel
	*/
	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
			
		String[][] tabArray = null;

		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			
			int startRow = 1;
			int startCol = 1;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();

			// you can write a function as well to get Column count
			int totalCols = 2;
			tabArray = new String[totalRows][totalCols];
			ci = 0;
			
			for (int i = startRow; i <= totalRows; i++, ci++) {

				cj = 0;				
				for (int j = startCol; j <= totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
					System.out.println(tabArray[ci][cj]);
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}
	
	/*
	 * To get the value of the entire column based on the specific row by specifying the TestCaseName
	*/
	public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow) throws Exception {
		
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
		
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			
			int startCol = 1;
			int ci = 0, cj = 0;
			int totalRows = 1;
			int totalCols = 2;
			
			tabArray = new String[totalRows][totalCols];
			
			for (int j = startCol; j <= totalCols; j++, cj++) {
				tabArray[ci][cj] = getCellData(iTestCaseRow, j);
				System.out.println(tabArray[ci][cj]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}
	
	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheetname as Arguments to this method
	public static void setExcelFile(String FilePath, String SheetName) throws Exception {

		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = Cell.getStringCellValue();

			return CellData;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters
	public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {
		try {
			Row = ExcelWSheet.getRow(RowNum);

			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);

			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}

			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(Constants.TESTDATA_PATH + Constants.TESTDATA_FILENAME);

			ExcelWBook.write(fileOut);

			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);
		}
	}
	
	public static String getTestCaseName(String sTestCase) throws Exception {

		String value = sTestCase;
		try {
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");
			value = value.substring(posi + 1);
			return value;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowContains(String sTestCaseName, int colNum) throws Exception {

		int i;
		try {
			int rowCount = ExcelUtils.getRowUsed();
			for (i = 0; i < rowCount; i++) {
				if (ExcelUtils.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
			return i;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowUsed() throws Exception {

		try {
			int RowCount = ExcelWSheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}
}