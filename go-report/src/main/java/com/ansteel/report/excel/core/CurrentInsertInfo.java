package com.ansteel.report.excel.core;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CurrentInsertInfo {

    /**
     * 开始行
     */
    private int beginRow=0;
    /**
     * 结束行
     */
    private int endRow=0;


    public int getBeginRow() {
        return beginRow;
    }

    public void setBeginRow(int beginRow) {
        this.beginRow = beginRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }
}
