package com.ansteel.modules.poi.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import com.ansteel.report.poi.utils.ToHtml;

public class ToHtmlTest {

	@Test
	public void test() throws IOException {
		ToHtml toHtml = ToHtml.create("D:\\test\\poi\\w1.xls", new PrintWriter(
				new FileWriter("D:\\test\\poi\\w1.html")));
		toHtml.setCompleteHTML(true);
		toHtml.printPage();
	}

}
