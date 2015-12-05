package com.ansteel.report.aspose.service;

import com.ansteel.core.exception.PageException;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import org.springframework.stereotype.Service;

import java.io.*;


/**
 * Created by Administrator on 2015/12/3.
 */
@Service
public class AsposeExcelToPdfServiceImpl implements AsposeExcelToPdfService {
    @Override
    public boolean convert2PDF(String excelFile, String pdfFile) {

        Workbook workbook = null;
        try {
            workbook = new Workbook(excelFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException("读取excel错误：" + e.getMessage());
        }
        try {
            workbook.save(pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException("写入PDF错误：" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean convert2PDF(File excelFile, File pdfFile) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(excelFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PageException("读取excel文件错误：" + e.getMessage());
        }
        Workbook workbook = null;
        try {
            workbook = new Workbook(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException("读取excel错误：" + e.getMessage());
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PageException("读取PDF文件错误：" + e.getMessage());
        }
        try {
            workbook.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException("写入PDF错误：" + e.getMessage());
        }
        return true;
    }
}
