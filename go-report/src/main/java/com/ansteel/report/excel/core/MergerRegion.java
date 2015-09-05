package com.ansteel.report.excel.core;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-09
 * 修 改 人：
 * 修改日 期：
 * 描   述：合并单元格工具类。
 */
public class MergerRegion {

	private String value;
	
	private String mapping;
	
	private int firstRow;
	
	private int lastRow;

    private int column;

	/**
	 * 字段名称
	 */
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
