package com.ansteel.report.poi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ansteel.core.utils.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.NumberValidationUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel工具类。
 */
public class Excel {
	/**
	 * 工作薄
	 */
	Workbook wb;
	/**
	 * excel类型xls，xlsx
	 */
	String type;

	private static final String XLS = "xls";

	private static final String XLSX = "xlsx";


	public String getType() {
		return type;
	}

	/**
	 * 设置工作簿
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public Excel(String path) throws InvalidFormatException, IOException {
		// File file = new File(path);
		// wb = WorkbookFactory.create(file);
		InputStream inp = new FileInputStream(path);
		wb = this.create(inp);
	}
	
	public Excel(InputStream inp) throws InvalidFormatException, IOException{
		wb = this.create(inp);
	}
	
	public Excel(Workbook wb,String type) {
		this.wb = wb;
		this.type=type;
	}
	
	public Excel() {
		this.type = XLS;
		this.wb=new HSSFWorkbook();
	}

	public Sheet getSheet(String sheetName) {
		Sheet sheet = wb.getSheet(sheetName);
		if (sheet == null) {
			try {
				sheet = wb.createSheet(sheetName);
			}catch (Exception e){
				throw new PageException(sheetName+",名称不能创建！原因："+e.getMessage());
			}
		}
		return sheet;
	}

	public Workbook create(InputStream inp) throws IOException,
			InvalidFormatException {
		// If clearly doesn't do mark/reset, wrap up
		if (!inp.markSupported()) {
			inp = new PushbackInputStream(inp, 8);
		}

		if (POIFSFileSystem.hasPOIFSHeader(inp)) {
			this.type = XLS;
			return new HSSFWorkbook(inp);
		}
		if (POIXMLDocument.hasOOXMLHeader(inp)) {
			this.type = XLSX;
			return new XSSFWorkbook(OPCPackage.open(inp));
		}
		throw new PageException(
				"这不是一个OLE2 InputStream流，也不是OOXML流");
	}

	public Workbook getWorkbook() {
		return wb;
	}

	/**
	 * 将此对象的excel写入文件
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void write(String path) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		wb.write(fos);
		fos.close();
	}

	/**
	 * 获取单元格，如果单元格为空，则创建
	 * 
	 * @param rownum 行
	 * @param cellnum 列
	 * @return
	 */
	public static Cell getCell(Sheet sheet, int rownum, int cellnum) {
		Row row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		Cell c = row.getCell(cellnum);
		if (c == null) {
			c = row.createCell(cellnum);
		}
		return c;
	}

	public String getCellValue(Cell c) {
		String o = null;
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_BLANK:// 空白
			o = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:// 布尔
			o = String.valueOf(c.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:// 公式
			o = String.valueOf(c.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:// 数字格式
			o = String.valueOf(c.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:// 字符串
			o = c.getStringCellValue();
			break;
		default:
			o = null;
			break;
		}
		return o;
	}

	/**
	 * 设置单元格的值 根据excl模板单元格格式设置值
	 * 
	 * @param c
	 * @param o
	 */
	public static void setCellValue(Cell c, String o) {
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_BLANK:// 空白
			if (StringUtils.hasText(o)) {
				try {
					if (NumberValidationUtils.isWholeNumber(o)) {
						c.setCellValue(Integer.valueOf(o));
					} else if (NumberValidationUtils.isDecimal(o)) {
						c.setCellValue(Double.valueOf(o));
					} else {
						c.setCellValue(String.valueOf(o));
					}
				} catch (Exception e) {
					c.setCellValue(String.valueOf(o));
				}
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:// 布尔
			c.setCellValue(Boolean.valueOf(o));
			break;
		case Cell.CELL_TYPE_FORMULA:// 公式
			c.setCellValue(String.valueOf(o));
			break;
		case Cell.CELL_TYPE_NUMERIC:// 数字格式
			if (StringUtils.hasText(o)) {
				try {
					if (NumberValidationUtils.isWholeNumber(o)) {
						c.setCellValue(Integer.valueOf(o));
					} else if (NumberValidationUtils.isDecimal(o)) {
						c.setCellValue(Double.valueOf(o));
					} else {
						c.setCellValue(String.valueOf(o));
					}
				} catch (Exception e) {
					c.setCellValue(String.valueOf(o));
				}
			}
			break;
		case Cell.CELL_TYPE_STRING:// 字符串
			c.setCellValue(String.valueOf(o));
			break;
		default:
			c.setCellValue(String.valueOf(o));
			break;
		}
	}

	/**
	 * 验证是否符合要求，true为正确
	 * 
	 * @param cell
	 * @return
	 */
	public static Boolean validateCell(String cell) {
		Assert.hasText(cell, "单元格不能为空！");
		String reg = "^[A-Z]{1,3}[0-9]{1,3}$";
		Pattern pc = Pattern.compile(reg);
		Matcher mc = pc.matcher(cell);
		if (!mc.find()) {
			return false;
		}
		return true;
	}

	/**
	 * 获得行、列队数值
	 * 比如：A2 [0]行为1 [1]列为0
	 * @param cell
	 * @return
	 */
	public static Integer[] getCellFigure(String cell) {
		Integer[] iArray = new Integer[2];
		int si = 0;
		String ii = "";
		int sValue = 0;
		int iValue = 0;
		for (int i = 0; i < cell.length(); i++) {
			char tmp = cell.charAt(i);
			if (tmp >= 48 & tmp <= 57) {
				ii += tmp;
			}
			if (tmp >= 65 & tmp <= 90) {

				if (0 == si) {
					sValue += (tmp - 65);
				} else {
					int iA = (sValue + 1);
					sValue = (tmp - 65) + iA * 26;
				}
				si++;
			}

		}
		iValue = Integer.valueOf(ii) - 1;
		iArray[0] = iValue;
		iArray[1] = sValue;
		return iArray;
	}

	/**
	 * 获得行、列队数值
	 * 比如：A2 [0]行为1 [1]列为0cell
	 * @param
	 * @return
	 */
	public static String getCellFigure(int iRow ,int iCell) {
		StringBuffer cell= new StringBuffer();
		if (iCell >25&&iCell<625) {
			char one = (char)(iCell/25+64);
			char two = (char)(iCell%25+64);
			cell.append(one);
			cell.append(two);
		}else{
			char c1 = (char) (iCell+65);
			cell.append(c1);
		}
		cell.append(iRow);
		return cell.toString();
	}
	
	public static void insertRow(Sheet sheet, int startRow, int rows) {
		int sourceRowNum = startRow-1;
		Row sourceRow = sheet.getRow(sourceRowNum);
		List<CellRangeAddress> cellRangeList = getCombineCell(sheet);
		List<CellRangeAddress> rowCellRangeList = new ArrayList<CellRangeAddress>();
		for (CellRangeAddress ca : cellRangeList) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			int firstR = ca.getFirstRow();
			if(sourceRowNum==firstR){
				rowCellRangeList.add(ca);
			}
		}
		// 移动后位置
		int lastRow = sheet.getLastRowNum();
		if(startRow<lastRow){
			sheet.shiftRows(startRow, lastRow, rows, true, false);
		}else {
			sheet.shiftRows(startRow, startRow, rows, true, false);
		}
		//此处修改效率大幅提升，还解决了2003以前的bug（跨行不停增加，最后越界）
		//此处不能放在循环体内
        boolean isFormula=checkIsFormula(sourceRow);
		
		for (int i = 0; i < rows; i++) {
			Row targetRow = sheet.createRow(startRow);
			Cell targetCell ,sourceCell;
			targetRow.setHeight(sourceRow.getHeight()); 
			for (int m = 0/*sourceRow.getFirstCellNum()*/; m <= sourceRow.getPhysicalNumberOfCells(); m++) { 
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);  
                if (sourceCell == null) {
                    continue;
                }
                targetCell.setCellStyle(sourceCell.getCellStyle());
				int ct = sourceCell.getCellType();
                targetCell.setCellType(ct);
                //替换公式
                if(isFormula) {
					int cellType = sourceCell.getCellType();
					if (Cell.CELL_TYPE_STRING == cellType) {
						String sourceCellValue = sourceCell.getStringCellValue();
						if (sourceCell == null) {
							continue;
						}
						if (sourceCell.getCellType() != Cell.CELL_TYPE_STRING) {
							continue;
						}
						if (sourceCellValue.indexOf("${ROW}") > -1) {
							//targetCell.setCellType(Cell.CELL_TYPE_FORMULA);
							String formula = sourceCellValue.replaceAll("\\$\\{ROW\\}", String.valueOf(targetCell.getRowIndex() + 1));
							formula = formula.replace("=", "");
							targetCell.setCellFormula(formula);
						}
					}
				}
            }

			for (CellRangeAddress ca : rowCellRangeList) {
				CellRangeAddress range = new CellRangeAddress(startRow,startRow,ca.getFirstColumn(),ca.getLastColumn());
				sheet.addMergedRegion(range); 
			}
			startRow++;
		}

        //替换源公式，就是替换第一行公式
        if(isFormula) {
			replaceFormula(sourceRow);
        }
	}

	/**
	 * 替换合计公式
	 * @param sourceRow
	 */
	public static void replaceFormula(Row sourceRow){
		for (int m = 0; m <= sourceRow.getPhysicalNumberOfCells(); m++) {
			Cell sourceCell = sourceRow.getCell(m);
			if(sourceCell==null) {
				continue;
			}
			if(sourceCell.getCellType()!=Cell.CELL_TYPE_STRING) {
				continue;
			}
			String sourceCellValue = sourceCell.getStringCellValue();
			if (sourceCellValue.indexOf("${ROW}") > -1) {
				String formula = sourceCellValue.replaceAll("\\$\\{ROW\\}", String.valueOf(sourceCell.getRowIndex() + 1));
				formula = formula.replace("=", "");
				sourceCell.setCellFormula(formula);
				sourceCell.setCellType(Cell.CELL_TYPE_FORMULA);
			}
		}
	}

    /**
     * 检查是否有公式
     * @param sourceRow
     * @return
     */
    public static boolean checkIsFormula(Row sourceRow) {
        for (int m = 0; m <= sourceRow.getPhysicalNumberOfCells(); m++) {
            Cell sourceCell = sourceRow.getCell(m);
            if(sourceCell==null) {
                continue;
            }
            if(sourceCell.getCellType()!=Cell.CELL_TYPE_STRING) {
                continue;
            }
            if (sourceCell.getStringCellValue().indexOf("${ROW}") > -1) {
                return true;
            }
        }
        return false;
    }

    public static List<CellRangeAddress> getCombineCell(Sheet sheet) {
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		// 获得一个 sheet 中合并单元格的数量
		int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格加入list中
			CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}
	
	public void writeMkdir(String outPath) {
		FileOutputStream fileOut = null;
		try {
			// 关闭输入流
			File file = new File(outPath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			fileOut = new FileOutputStream(outPath);
			this.wb.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void removeRow(Sheet sheet,int rowIndex){
		int lastRowNum=sheet.getLastRowNum();
		if(rowIndex>=0&&rowIndex<lastRowNum)
			sheet.shiftRows(rowIndex+1,lastRowNum,-1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
		if(rowIndex==lastRowNum){
			Row removingRow=sheet.getRow(rowIndex);
			if(removingRow!=null)
				sheet.removeRow(removingRow);
		}
	}

	public static void copyInsertRow(Sheet sheet,int fromRow,int toRow,int mergerFirstRow,int mergerLastRow){
		//创建一行
		sheet.shiftRows(toRow, sheet.getLastRowNum(), 1, true, false);
		Row targetRow=sheet.createRow(toRow);
		Row sourceRow=sheet.getRow(fromRow);

		//复制高度
		short height=sourceRow.getHeight();
		targetRow.setHeight(height);


		//合并单元格
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();

			if(firstRow==sourceRow.getRowNum()&&lastColumn>firstColumn){
				sheet.addMergedRegion(new CellRangeAddress(
						targetRow.getRowNum(), //first row (0-based)
						targetRow.getRowNum(), //last row  (0-based)
						firstColumn, //first column (0-based)
						lastColumn  //last column  (0-based)
				));
			}
		}

			Cell targetCell ,sourceCell;
		int nc=sourceRow.getPhysicalNumberOfCells();
		for (int m = 0/*sourceRow.getFirstCellNum()*/; m <= nc; m++) {
			sourceCell = sourceRow.getCell(m);
			targetCell = targetRow.createCell(m);
			if (sourceCell == null) {
				continue;
			}
			//复制样式
			targetCell.setCellStyle(sourceCell.getCellStyle());

			//评论
			if (sourceCell.getCellComment() != null) {
				targetCell.setCellComment(sourceCell.getCellComment());
			}

			//复制类型
			int ct = sourceCell.getCellType();
			targetCell.setCellType(ct);

			switch (ct) {
				case Cell.CELL_TYPE_BLANK:// 空白
					break;
				case Cell.CELL_TYPE_BOOLEAN:// 布尔
					targetCell.setCellValue(sourceCell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:// 公式
					targetCell.setCellFormula(sourceCell.getCellFormula());
					break;
				case Cell.CELL_TYPE_NUMERIC:// 数字格式
					if (DateUtil.isCellDateFormatted(sourceCell)) {
						targetCell.setCellValue(sourceCell.getDateCellValue());
					} else {
						targetCell.setCellValue(sourceCell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_STRING:// 字符串
					setCellString(sourceCell,targetCell,mergerFirstRow,mergerLastRow);
					break;
				case Cell.CELL_TYPE_ERROR:
					targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
					break;
			}
		}

	}

	private static void setCellString(Cell sourceCell, Cell targetCell, int mergerFirstRow, int mergerLastRow) {

		RichTextString text=sourceCell.getRichStringCellValue();
		String formula=text.getString();
		if(formula.indexOf("${BEGIN}")>-1||formula.indexOf("${END}")>-1){
			formula = formula.replaceAll("\\$\\{BEGIN\\}", String.valueOf(mergerFirstRow+1));
			formula = formula.replaceAll("\\$\\{END\\}", String.valueOf(mergerLastRow+1));
			formula = formula.replace("=", "");
			targetCell.setCellFormula(formula);
			targetCell.setCellType(Cell.CELL_TYPE_FORMULA);

		}else {
			targetCell.setCellValue(sourceCell.getRichStringCellValue());
		}
	}


}
