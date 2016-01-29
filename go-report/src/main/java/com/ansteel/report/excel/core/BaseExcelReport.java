package com.ansteel.report.excel.core;

import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-07
 * 修 改 人：
 * 修改日 期：
 * 描   述：普通Excel报表。
 */
public class BaseExcelReport {

    Logger logger = Logger.getLogger(BaseExcelReport.class);

    public Excel getExcel(Excel excel, Map<ExcelReportSQL, List> mapExcel) {
        for (Entry<ExcelReportSQL, List> entry : mapExcel.entrySet()) {
            List list = entry.getValue();
            if (list.size() > 0) {
                ExcelReportSQL excelReportSQL = entry.getKey();
                String sheetName = ExcelMakeUtils.getSheetName(excelReportSQL.getSheetName());
                Sheet sheet = excel.getSheet(sheetName);
                this.setSQLData(sheet, excelReportSQL, list);
            } else {
                logger.error(entry.getKey().getAlias() + ",没有记录！");
            }
        }

        return excel;
    }

    /**
     * 数据填充
     *
     * @param
     * @param excelReportSQL
     * @param list
     */
    public void setSQLData(Sheet sheet, ExcelReportSQL excelReportSQL, List list) {


        // 字段对照excel单元格
        Map<String, String> mapping = StringUtils
                .getOptionsSplit(excelReportSQL.getRecode());

        //获取合并单元格
        String mergerRegionRecode = excelReportSQL.getMergerRegionRecode();
        Map<String, MergerRegion> mergerMap = this.getMergerMap(mergerRegionRecode, mapping);
        /**
         * 最大行数
         */
        int maxNumber = excelReportSQL.getLoopMaxNumber() == null ? 0 : excelReportSQL.getLoopMaxNumber();
        /**
         * 当前记录行数
         */
        int currentNum = list.size();
        if (maxNumber > 0) {
            if (list.size() > maxNumber) {
                currentNum = maxNumber;
            }
        }

        /**
         * 是否循环
         */
        Integer isLoop = excelReportSQL.getIsLoop() == null ? 0 : excelReportSQL.getIsLoop();
        Integer insertRow = 0;
        if (isLoop == 1) {
            Integer cellend = excelReportSQL.getCellEnd() == null ? 0 : excelReportSQL
                    .getCellEnd();
            Integer cellStatr = excelReportSQL.getCellStart() == null ? 0
                    : excelReportSQL.getCellStart();
            if (cellend > 0) {
                if(cellend>cellStatr){
                    Row sourceRow = sheet.getRow(cellend-1);
                    boolean isFormula=Excel.checkIsFormula(sourceRow);
                    if(isFormula) {
                        Excel.replaceFormula(sourceRow);
                    }
                }

                Integer num = cellend - cellStatr + 1;

                // excel模板的结束换到开始行之间插入行
                if (currentNum > num) {
                    insertRow = currentNum - num;
                    Excel.insertRow(sheet, cellStatr, insertRow);
                }else{
                    //解决没有插入行的公式替换
                    int sourceRowNum = cellStatr-1;
                    Row sourceRow = sheet.getRow(sourceRowNum);
                    boolean isFormula=Excel.checkIsFormula(sourceRow);
                    if(isFormula) {
                        Excel.replaceFormula(sourceRow);
                    }
                }
            }
        }

        //获取跟随合并
        String mergerRegionFollow=excelReportSQL.getMergerRegionFollow();
        List<int[]> mergerFollowList=this.getMergerRegionFollow(mergerRegionFollow, mapping);

        //获取合并单元格固定插入值编码
        String mergerRegionFixed = excelReportSQL.getMergerRegionFixed();
        Map<String, Integer> mergerFixedMap = this.getMergerFixedMap(mergerRegionFixed, mapping, sheet, insertRow);


        if(mergerFixedMap.size()>0) {
            InsertFixedMerger insertFixed = new InsertFixedMerger();
            insertFixed.run(list, mapping,currentNum, mergerMap, mergerFixedMap, sheet, mergerFollowList);
        }else{
            for (int i = 0;i < currentNum; i++) {
                Map dataMap = (Map) list.get(i);
                //加入记录
                ExcelMakeUtils.mappingCellValue(i, mapping, dataMap, sheet);
                ExcelMakeUtils.mergerRegion(i, currentNum - 1, dataMap, mergerMap, sheet, mergerFollowList);
            }
        }

        /*for (int i = 0, iData = 0; i < currentNum; i++, iData++) {
            Map dataMap = (Map) list.get(iData);
            //加入记录
            ExcelMakeUtils.mappingCellValue(i, mapping, dataMap, sheet);

            if (mergerFixedMap.size() > 0) {
                int addNum=insertFixed.run(i,currentNum, dataMap, mergerMap, mergerFixedMap, sheet);
            }
        }*/


       /* MergerInfo mergerInfo = new MergerInfo();
        for (int i = 0, iData = 0; i < currentNum; i++, iData++) {
            Map dataMap = (Map) list.get(iData);
            ExcelMakeUtils.mappingCellValue(i, mapping, dataMap, sheet);
            if (mergerMap.size() > 0) {
                mergerInfo = ExcelMakeUtils.mergerRegion(i, currentNum - 1, dataMap, mergerMap, mergerFixedMap, sheet, mergerInfo,mergerFollowList);
                //固定插入行编码相加
                if (mergerInfo!=null&&mergerInfo.getAddRowNumber() > 0) {
                    i += mergerInfo.getAddRowNumber();
                    currentNum += mergerInfo.getAddRowNumber();
                    for (Entry entry : mergerFixedMap.entrySet()) {
                        String key = (String) entry.getKey();
                        int value = (Integer) entry.getValue() + mergerInfo.getAddRowNumber();
                        mergerFixedMap.put(key, value);
                    }
                }
            }
        }

        int remove=0;
        for(Entry<String,Integer> entry:mergerFixedMap.entrySet()){
            if(remove>0){
                Excel.removeRow(sheet,entry.getValue()-1);
            }else{
                Excel.removeRow(sheet,entry.getValue());
            }
            remove++;
        }*/
        sheet.setForceFormulaRecalculation(true);

    }

    private List<int[]> getMergerRegionFollow(String mergerRegionFollow,Map<String, String> mapping) {
        //Integer[] iArray=Excel.getCellFigure(strArray[1]);
        List<int[]> followList = new ArrayList<>();
        if (StringUtils.hasText(mergerRegionFollow)) {
            String[] mrArray = mergerRegionFollow.split(";");
            for (String s : mrArray) {
                int[] iArray = new int[3];
                String[] rowArray = s.split("=");
                if (rowArray.length == 2) {
                    String ir=mapping.get(rowArray[0]);
                    Integer[] iR=Excel.getCellFigure(ir);
                    iArray[0]=iR[1];

                    String[] array2=rowArray[1].split("&");
                    Integer[] iRow=Excel.getCellFigure(array2[0]);
                    iArray[1]=iRow[1];
                    if(array2.length==2){
                        try{
                            iArray[2]=Integer.valueOf(array2[1]);
                        }catch (Exception e){
                            throw new PageException(e.getMessage());
                        }
                    }else{
                        iArray[2]=0;
                    }
                    followList.add(iArray);
                }
            }
        }
        return followList;
    }

    private Map<String, Integer> getMergerFixedMap(String mergerRegionFixed, Map<String, String> mapping, Sheet sheet, Integer insertRow) {
        Map<String, Integer> mergerMap = new LinkedHashMap<>();

        if (StringUtils.hasText(mergerRegionFixed)) {
            String[] mrArray = mergerRegionFixed.split(";");
            for (String s : mrArray) {
                String[] rowArray = s.split("=");
                if (rowArray.length == 2) {
                    try {
                        int iRow = Integer.valueOf(rowArray[1]) - 1;
                        mergerMap.put(rowArray[0], iRow + insertRow);
                    } catch (Exception e) {
                        throw new PageException(s + ",合并固定单元格设置错误！" + e.getMessage());
                    }
                }
            }
            //删除固定格
           /* for (String s : mrArray) {
                String[] rowArray = s.split("=");
                if (rowArray.length == 2) {
                    int iRow=Integer.valueOf(rowArray[1]);
                    Excel.removeRow(sheet,iRow);
                }
            }*/
        }
        return mergerMap;
    }

    /**
     * 获取合并单元格map
     *
     * @param mergerRegionRecode
     * @param mapping
     * @return
     */
    private Map<String, MergerRegion> getMergerMap(String mergerRegionRecode, Map<String, String> mapping) {
        Map<String, MergerRegion> mergerMap = new LinkedHashMap<String, MergerRegion>();

        if (StringUtils.hasText(mergerRegionRecode)) {
            String[] mrArray = mergerRegionRecode.split(";");
            for (String s : mrArray) {
                if (mapping.containsKey(s)) {
                    MergerRegion mergerRegion = new MergerRegion();
                    mergerRegion.setMapping(mapping.get(s));
                    mergerMap.put(s, mergerRegion);
                } else {
                    throw new PageException("合并单元格映射错误！");
                }
            }
        }
        return mergerMap;
    }
}
