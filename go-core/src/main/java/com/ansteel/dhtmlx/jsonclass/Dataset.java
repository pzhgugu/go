package com.ansteel.dhtmlx.jsonclass;

/**
 * Created by gugu on 2016/12/1.
 */
public class Dataset {
    private String beanName;
    private String[] propNames;
    private String[] propChineseNames;
    private String[] propTypes;
    private Object[][] propValues;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String[] getPropNames() {
        return propNames;
    }

    public void setPropNames(String[] propNames) {
        this.propNames = propNames;
    }

    public String[] getPropChineseNames() {
        return propChineseNames;
    }

    public void setPropChineseNames(String[] propChineseNames) {
        this.propChineseNames = propChineseNames;
    }

    public String[] getPropTypes() {
        return propTypes;
    }

    public void setPropTypes(String[] propTypes) {
        this.propTypes = propTypes;
    }

    public Object[][] getPropValues() {
        return propValues;
    }

    public void setPropValues(Object[][] propValues) {
        this.propValues = propValues;
    }
}
