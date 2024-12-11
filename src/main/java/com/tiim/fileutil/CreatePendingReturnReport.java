package com.tiim.fileutil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tiim.model.ToolingIssueNote;

public class CreatePendingReturnReport {


	 
	private CellStyle cs = null;
	 private CellStyle csBold = null;
	 private CellStyle csTop = null;
	 private CellStyle csRight = null;
	 private CellStyle csBottom = null;
	 private CellStyle csLeft = null;
	 private CellStyle csTopLeft = null;
	 private CellStyle csTopRight = null;
	 private CellStyle csBottomLeft = null;
	 private CellStyle csBottomRight = null;
	 
	public void writeProductExcel(List<ToolingIssueNote> lstIssue, String excelFilePath)
	{
		try{
				Workbook workbook = getWorkbook(excelFilePath);
				setCellStyles(workbook);
				
				Sheet sheet = workbook.createSheet();
				 sheet.setColumnWidth(0, 2500); 
				   sheet.setColumnWidth(1, 8000);
				   sheet.setColumnWidth(2, 8000);
				   sheet.setColumnWidth(3, 8000);
				   sheet.setColumnWidth(4, 8000);
				  
				int rowCount = 0;
				Row headerRow = sheet.createRow(++rowCount);
				createHeader(headerRow);
				for (ToolingIssueNote product : lstIssue) {
					Row row = sheet.createRow(++rowCount);
					writeProductReportBook(product, row);
				}
				
				FileOutputStream outputStream = new FileOutputStream(excelFilePath);
				workbook.write(outputStream);
		}catch(Exception exception)
		{
			System.out.println("Exception when writing the CreatePendingReturnReport: "+exception.getMessage());
		}
	}
	
	private void createHeader(Row row)
	{
		
		
		Cell cell = row.createCell(1);
		cell.setCellValue("Issue Number");
		cell.setCellStyle(csBold);

		cell = row.createCell(2);
		cell.setCellValue("ProductName");
		cell.setCellStyle(csBold);
		
		cell = row.createCell(3);
		cell.setCellValue("Machine Type");
		cell.setCellStyle(csBold);
		
		cell = row.createCell(4);
		cell.setCellValue("Type of Tool");
		cell.setCellStyle(csBold);
		
		cell = row.createCell(5);
		cell.setCellValue("Drawing Number");
		cell.setCellStyle(csBold);
		
	}
	private void writeProductReportBook(ToolingIssueNote product, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(product.getIssueId());

		cell = row.createCell(2);
		cell.setCellValue(product.getProductName1());
		
		cell = row.createCell(3);
		cell.setCellValue(product.getMachineName1());
		
		cell = row.createCell(4);
		cell.setCellValue(product.getTypeOfTooling1());

		cell = row.createCell(5);
		cell.setCellValue(product.getDrawingNo1());
	
	}

	public void writeExcel(List<Book> listBook, String excelFilePath) throws IOException {
		Workbook workbook = getWorkbook(excelFilePath);
		Sheet sheet = workbook.createSheet();
		
		int rowCount = 0;
		
		for (Book aBook : listBook) {
			Row row = sheet.createRow(++rowCount);
			writeBook(aBook, row);
		}
		
		try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
			workbook.write(outputStream);
		}		
	}
	
	private void writeBook(Book aBook, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(aBook.getTitle());

		cell = row.createCell(2);
		cell.setCellValue(aBook.getAuthor());
		
		cell = row.createCell(3);
		cell.setCellValue(aBook.getPrice());
	}
	
	
	private Workbook getWorkbook(String excelFilePath) 
			throws IOException {
		Workbook workbook = null;
		
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		
		return workbook;
	}

	private void setCellStyles(Workbook wb) {

		  //font size 10
		  Font f = wb.createFont();
		  f.setFontHeightInPoints((short) 10);

		  //Simple style 
		  cs = wb.createCellStyle();
		  cs.setFont(f);

		  //Bold Fond
		  Font bold = wb.createFont();
		  bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		  bold.setFontHeightInPoints((short) 10);

		  //Bold style 
		  csBold = wb.createCellStyle();
		  csBold.setBorderBottom(CellStyle.BORDER_THIN);
		  csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBold.setFont(bold);

		  //Setup style for Top Border Line
		  csTop = wb.createCellStyle();
		  csTop.setBorderTop(CellStyle.BORDER_THIN);
		  csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTop.setFont(f);

		  //Setup style for Right Border Line
		  csRight = wb.createCellStyle();
		  csRight.setBorderRight(CellStyle.BORDER_THIN);
		  csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csRight.setFont(f);

		  //Setup style for Bottom Border Line
		  csBottom = wb.createCellStyle();
		  csBottom.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottom.setFont(f);

		  //Setup style for Left Border Line
		  csLeft = wb.createCellStyle();
		  csLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csLeft.setFont(f);

		  //Setup style for Top/Left corner cell Border Lines
		  csTopLeft = wb.createCellStyle();
		  csTopLeft.setBorderTop(CellStyle.BORDER_THIN);
		  csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setFont(f);

		  //Setup style for Top/Right corner cell Border Lines
		  csTopRight = wb.createCellStyle();
		  csTopRight.setBorderTop(CellStyle.BORDER_THIN);
		  csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setBorderRight(CellStyle.BORDER_THIN);
		  csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setFont(f);

		  //Setup style for Bottom/Left corner cell Border Lines
		  csBottomLeft = wb.createCellStyle();
		  csBottomLeft.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setFont(f);

		  //Setup style for Bottom/Right corner cell Border Lines
		  csBottomRight = wb.createCellStyle();
		  csBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setBorderRight(CellStyle.BORDER_THIN);
		  csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setFont(f);

		 }
	
	
}
