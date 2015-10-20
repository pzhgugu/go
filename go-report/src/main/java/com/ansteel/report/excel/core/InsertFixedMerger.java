package com.ansteel.report.excel.core;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/11.
 */
public class InsertFixedMerger {

    //当前行
    private int currenRow = 0;

    //被改变，插入一行的值
    Map<String, CurrentInsertInfo> currentInsertInfoMap = new HashMap<>();

    public void run(List list, Map<String, String> mapping,int currentNum, Map<String, MergerRegion> mergerMap, Map<String, Integer> mergerFixedMap, Sheet sheet,List<int[]> mergerFollowList) {
        this.initInsertAlterMap(mergerMap);
        for (int i = 0, iData = 0; i < currentNum; i++, iData++) {
            Map dataMap = (Map) list.get(iData);
            //加入记录
            ExcelMakeUtils.mappingCellValue(i, mapping, dataMap, sheet);
            Map nextDataMap=null;
            int nextData=iData+1;
            if(!(nextData>=list.size())){
                nextDataMap= (Map) list.get(nextData);
            }
            int addNum=insertAndMerger(i, dataMap, nextDataMap, mergerMap, mergerFixedMap, sheet, mergerFollowList,mapping);
            currentNum+=addNum;
            i+=addNum;
        }

        //删除模板行
        int remove=0;
        for (Map.Entry<String, Integer> entry : mergerFixedMap.entrySet()) {
            if(remove>0){
                Excel.removeRow(sheet,entry.getValue()-1);
            }else{
                Excel.removeRow(sheet,entry.getValue());
            }
            remove++;
        }
    }

    private void initInsertAlterMap(Map<String, MergerRegion> mergerMap) {
        for (Map.Entry<String, MergerRegion> entry : mergerMap.entrySet()) {
            CurrentInsertInfo currentInsertInfo= new  CurrentInsertInfo();
            currentInsertInfoMap.put(entry.getKey(),currentInsertInfo);
        }
    }

    /**
     *
     * @param dataMap 当前记录集
     * @param mergerMap 单元格映射
     * @param mergerFixedMap 固定插入格
     * @param sheet
     * @return
     */
    public int insertAndMerger(int currnRowNum,Map dataMap,Map nextDataMap, Map<String, MergerRegion> mergerMap, Map<String, Integer> mergerFixedMap, Sheet sheet,List<int[]> mergerFollowList, Map<String, String> mapping) {

        Map<String, Integer> newLineMap = this.getNewLineMap(currnRowNum, dataMap, nextDataMap, mergerMap, mergerFixedMap);

        if(newLineMap.size()>0) {
            //插入新行
            this.insertAndMerger(newLineMap,mergerFixedMap,sheet,mergerFollowList,mapping);
            return newLineMap.size();
        }
        return 0;
    }


    private void insertAndMerger(Map<String, Integer> newLineMap,Map<String, Integer> mergerFixedMap, Sheet sheet,List<int[]> mergerFollowList, Map<String, String> mapping) {
        int num = 1;
        for (Map.Entry<String, Integer> entry : mergerFixedMap.entrySet()) {
            String key=entry.getKey();
            if(newLineMap.containsKey(key)) {
                int insertNum = newLineMap.get(key);
                CurrentInsertInfo currentInsertInfo=currentInsertInfoMap.get(key);
                //插入行
                Excel.copyInsertRow(sheet, insertNum + num, currenRow+num, currentInsertInfo.getBeginRow(), currentInsertInfo.getEndRow() );
                //合并行
                this.merger(key,mapping,currentInsertInfo,sheet,mergerFollowList,num);
                num++;
                //结束行+插入行+下一行=新的开始行
                currentInsertInfo.setBeginRow(currentInsertInfo.getEndRow()+newLineMap.size()+1);
            }
        }
        for (Map.Entry<String, Integer> entry : mergerFixedMap.entrySet()) {
            mergerFixedMap.put(entry.getKey(),entry.getValue()+newLineMap.size());
        }
    }

    /**
     * 合并单元格
     * @param key
     * @param mapping
     * @param currentInsertInfo
     * @param sheet
     * @param mergerFollowList
     */
    private void merger(String key, Map<String, String> mapping, CurrentInsertInfo currentInsertInfo, Sheet sheet, List<int[]> mergerFollowList,int num) {
        Integer[] iArray = Excel.getCellFigure(mapping.get(key));
        int column = iArray[1];
        int endRow=currentInsertInfo.getEndRow();
        int newRow=endRow+num;
        String value = sheet.getRow(newRow).getCell(column).getStringCellValue();
        if(!StringUtils.hasText(value)){
            endRow=newRow;
        }
        ExcelMakeUtils.addMergedRegion(currentInsertInfo.getBeginRow(),endRow, column, column, sheet, mergerFollowList);
    }


    public Map<String, Integer> getNewLineMap(int currnRowNum,Map dataMap,Map nextDataMap, Map<String, MergerRegion> mergerMap, Map<String, Integer> mergerFixedMap) {
        Map<String, Integer> newLineMap = new HashMap<>();
        //合并开始行，合并结束行
        for (Map.Entry<String, MergerRegion> entry : mergerMap.entrySet()) {
            String key = entry.getKey();

            CurrentInsertInfo currentInsertInfo= currentInsertInfoMap.get(key);

            MergerRegion mergerRegion = entry.getValue();

            Integer[] iArray = Excel.getCellFigure(mergerRegion.getMapping());
            currenRow = iArray[0] + currnRowNum;

            if(currentInsertInfo.getBeginRow()==0){
                currentInsertInfo.setBeginRow(currenRow);
            }
            if(nextDataMap==null){
                currentInsertInfo.setEndRow(currenRow);
                newLineMap.put(key,mergerFixedMap.get(key));
                currentInsertInfoMap.put(key,currentInsertInfo );
            }else {
                String oldValue = (String) dataMap.get(key);
                String newValue = (String) nextDataMap.get(key);
                if (oldValue == null) {
                    continue;
                }
                if (newValue == null) {
                    continue;
                }
                if (!oldValue.equals(newValue)) {
                    currentInsertInfo.setEndRow(currenRow);
                    newLineMap.put(key, mergerFixedMap.get(key));
                    currentInsertInfoMap.put(key,currentInsertInfo );
                }
            }

        }
        return newLineMap;
    }
}
