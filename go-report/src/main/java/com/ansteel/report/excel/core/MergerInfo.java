package com.ansteel.report.excel.core;

/**
 * Created by Administrator on 2015/9/2.
 */
public class MergerInfo {
    /**
     * 增加行数量
     */
    private int addRowNumber;
    /**
     * 第一次插入行名称
     */
    private String oneInsertRowName;
    /**
     * 当前插入行名称
     */
    private String currentInsertRowName;

    public int getAddRowNumber() {
        return addRowNumber;
    }

    public void setAddRowNumber(int addRowNumber) {
        this.addRowNumber = addRowNumber;
    }

    public String getOneInsertRowName() {
        return oneInsertRowName;
    }

    public void setOneInsertRowName(String oneInsertRowName) {
        this.oneInsertRowName = oneInsertRowName;
    }

    public String getCurrentInsertRowName() {
        return currentInsertRowName;
    }

    public void setCurrentInsertRowName(String currentInsertRowName) {
        this.currentInsertRowName = currentInsertRowName;
    }
}
