package com.ansteel.report.poi.utils;

import java.util.HashMap;
import java.util.Map;
 
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
 
/**
 * POI工具类 功能点： 
 * 1、实现excel的sheet复制，复制的内容包括单元的内容、样式、注释
 * 2、setMForeColor修改HSSFColor.YELLOW的色值，setMBorderColor修改PINK的色值
 * 
 * @author Administrator
 */
public final class PoiSheetCopy {
 
    /**
     * 功能：拷贝sheet
     * 实际调用     copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true)
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork                                                                   
     */
    public static void copySheet(Sheet sourceSheet,Sheet targetSheet, 
    		Workbook sourceWork,Workbook targetWork) throws Exception{
        if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
            throw new IllegalArgumentException("调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
        copySheet(sourceSheet, targetSheet, sourceWork,targetWork,  true);
    }

 
    /**
     * 功能：拷贝sheet
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork
     * @param copyStyle                 boolean 是否拷贝样式
     */
    public static void copySheet(Sheet sourceSheet,Sheet targetSheet, 
            Workbook sourceWork, Workbook targetWork, boolean copyStyle)throws Exception {
         
        if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
            throw new IllegalArgumentException("调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
         
        //复制源表中的行
        int maxColumnNum = 0;
 
        Map styleMap = (copyStyle) ? new HashMap() : null;
         
        Drawing patriarch = targetSheet.createDrawingPatriarch(); //用于复制注释
        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row targetRow = targetSheet.createRow(i);
             
            if (sourceRow != null) {
                copyRow(sourceRow,targetRow, 
                		sourceWork,targetWork, patriarch, styleMap);
                if (sourceRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = sourceRow.getLastCellNum();
                }
            }
        }
         
        //复制源表中的合并单元格
        mergerRegion(sourceSheet,targetSheet);
         
        //设置目标sheet的列宽
        for (int i = 0; i <= maxColumnNum; i++) {
            targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
        }
    }
     
    /**
     * 功能：拷贝row
     * @param targetRow
     * @param sourceRow
     * @param styleMap
     * @param targetWork
     * @param sourceWork
     * @param targetPatriarch
     */
    public static void copyRow(Row sourceRow,Row targetRow, 
    		 Workbook sourceWork,Workbook targetWork,Drawing targetPatriarch, Map styleMap) throws Exception {
        if(targetRow == null || sourceRow == null || targetWork == null || sourceWork == null || targetPatriarch == null){
            throw new IllegalArgumentException("调用PoiUtil.copyRow()方法时，targetRow、sourceRow、targetWork、sourceWork、targetPatriarch都不能为空，故抛出该异常！");
        }
         
        //设置行高
        targetRow.setHeight(sourceRow.getHeight());
         
        for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            Cell targetCell = targetRow.getCell(i);
             
            if (sourceCell != null) {
                if (targetCell == null) {
                    targetCell = targetRow.createCell(i);
                }
                 
                //拷贝单元格，包括内容和样式
                copyCell(sourceCell,targetCell,sourceWork,targetWork, styleMap);
                
                //拷贝单元格注释
                copyComment(sourceCell,targetCell,targetPatriarch,targetWork);
            }
        }
    }
    
    /**
     * 功能：拷贝comment
     * @param targetCell
     * @param sourceCell
     * @param targetPatriarch
     */
    public static void copyComment(Cell sourceCell,Cell targetCell,Drawing targetPatriarch,Workbook targetWork)throws Exception{
        if(targetCell == null || sourceCell == null || targetPatriarch == null|| targetWork == null){
            throw new IllegalArgumentException("调用PoiUtil.copyCommentr()方法时，targetCell、sourceCell、targetPatriarch都不能为空，故抛出该异常！");
        }
         
        //处理单元格注释
        CreationHelper factory = targetWork.getCreationHelper(); 
        ClientAnchor anchor = factory.createClientAnchor();  
        
        Comment comment = sourceCell.getCellComment();
        if(comment != null){
            Comment newComment = targetPatriarch.createCellComment(anchor);
            newComment.setAuthor(comment.getAuthor());
            newComment.setColumn(comment.getColumn());
            newComment.setRow(comment.getRow());
            newComment.setString(comment.getString());
            newComment.setVisible(comment.isVisible());
            targetCell.setCellComment(newComment);
        }
    }
     
    /**
     * 功能：拷贝cell，依据styleMap是否为空判断是否拷贝单元格样式
     * @param targetCell            不能为空
     * @param sourceCell            不能为空
     * @param targetWork            不能为空
     * @param sourceWork            不能为空
     * @param styleMap              可以为空                
     */
    public static void copyCell(Cell sourceCell, Cell targetCell, Workbook sourceWork,Workbook targetWork, Map styleMap) {
        if(targetCell == null || sourceCell == null || targetWork == null || sourceWork == null ){
            throw new IllegalArgumentException("调用PoiUtil.copyCell()方法时，targetCell、sourceCell、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
         
        //处理单元格样式
        if(styleMap != null){
            if (targetWork == sourceWork) {
                targetCell.setCellStyle(sourceCell.getCellStyle());
            } else {
                String stHashCode = "" + sourceCell.getCellStyle().hashCode();
                CellStyle targetCellStyle = (CellStyle) styleMap
                        .get(stHashCode);
                if (targetCellStyle == null) {
                    targetCellStyle = targetWork.createCellStyle();
                    targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
                    styleMap.put(stHashCode, targetCellStyle);
                }
                 
                targetCell.setCellStyle(targetCellStyle);
            }
        }
         
        //处理单元格内容
        switch (sourceCell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            targetCell.setCellValue(sourceCell.getRichStringCellValue());
            break;
        case Cell.CELL_TYPE_NUMERIC:
            targetCell.setCellValue(sourceCell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            targetCell.setCellType(Cell.CELL_TYPE_BLANK);
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            targetCell.setCellValue(sourceCell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
            targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            targetCell.setCellFormula(sourceCell.getCellFormula());
            break;
        default:
            break;
        }
    }
     
    /**
     * 功能：复制原有sheet的合并单元格到新创建的sheet
     * 
     * @param sheetCreat
     * @param sourceSheet
     */
    public static void mergerRegion(Sheet sourceSheet,Sheet targetSheet)throws Exception {
        if(targetSheet == null || sourceSheet == null){
            throw new IllegalArgumentException("调用PoiUtil.mergerRegion()方法时，targetSheet或者sourceSheet不能为空，故抛出该异常！");
        }
         
        int sheetMergerCount = sourceSheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
        	 CellRangeAddress mergedRegionAt = sourceSheet.getMergedRegion(i);
        	 if ((mergedRegionAt.getFirstColumn() >= sourceSheet.getFirstRowNum())
 					&& (mergedRegionAt.getLastRow() <= sourceSheet.getLastRowNum())) {
            	 targetSheet.addMergedRegion(mergedRegionAt);
 			}
        }

    }

}