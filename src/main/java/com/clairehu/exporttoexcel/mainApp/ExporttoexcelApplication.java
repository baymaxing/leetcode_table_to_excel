package com.clairehu.exporttoexcel.mainApp;

import com.clairehu.exporttoexcel.Components.ExcelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("com.clairehu.exporttoexcel")
public class ExporttoexcelApplication {

	@Autowired
	ExcelConverter converter;

	public static void main(String[] args) throws IOException {
		//SpringApplication.run(ExporttoexcelApplication.class, args);
		ApplicationContext applicationContext =
				SpringApplication.run(ExporttoexcelApplication.class, args);
		ExporttoexcelApplication converterApp = applicationContext.getBean(ExporttoexcelApplication.class);

		converterApp.converter.getHtmlTableWithJsoup();
		converterApp.converter.parseHtml();
		converterApp.converter.convertToExcel();
	}
}
