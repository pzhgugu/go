package com.ansteel.report.dc.service;

import com.ansteel.dhtmlx.jsonclass.UDataSet;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gugu on 2016/11/30.
 */
public interface DataCollectionService {
    Boolean run(String name, HttpServletRequest request, Map requestMap);

    UDataSet setTestPath(UDataSet dataSet, HttpServletRequest request);
}
