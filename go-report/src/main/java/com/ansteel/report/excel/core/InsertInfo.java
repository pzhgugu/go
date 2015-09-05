package com.ansteel.report.excel.core;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/4.
 */
public class InsertInfo {
    /**
     * 插入行改变的集合
     */
    private Map<String, Integer> insertAlterMap = new HashMap<>();
    /**
     * 合并单元格集合
     */
    private List<MergerRegion[]> mergerList= new ArrayList();;
    /**
     * 当前行
     */
    private int curreniRow= 0;
    /**
     * 增加行数
     */
    private int addRow= 0;
    /**
     * 是否为改变值最后行
     */
    /*private Boolean isAlterlastRow=false;

    private String insertKey;*/

    public Map<String, Integer> getInsertAlterMap() {
        return insertAlterMap;
    }

    public void setInsertAlterMap(Map<String, Integer> insertAlterMap) {
        this.insertAlterMap = insertAlterMap;
    }

    public List<MergerRegion[]> getMergerList() {
        return mergerList;
    }

    public void setMergerList(List<MergerRegion[]> mergerList) {
        this.mergerList = mergerList;
    }

    public int getCurreniRow() {
        return curreniRow;
    }

    public void setCurreniRow(int curreniRow) {
        this.curreniRow = curreniRow;
    }

    public int getAddRow() {
        return addRow;
    }

    public void setAddRow(int addRow) {
        this.addRow = addRow;
    }

}
