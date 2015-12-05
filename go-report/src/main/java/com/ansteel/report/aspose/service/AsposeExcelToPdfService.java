package com.ansteel.report.aspose.service;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2015/12/3.
 */
public interface AsposeExcelToPdfService {
    boolean convert2PDF(String excelFile, String pdfFile);

    boolean convert2PDF(File excelFile, File pdfFile);
}
