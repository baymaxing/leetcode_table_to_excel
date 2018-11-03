package com.clairehu.exporttoexcel.Components;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Component
@PropertySource("application.properties")
public class ExcelConverter {
    private Document doc;
    private ArrayList<String> rowList = new ArrayList<>();
    private HashMap<String, String> linkMap = new HashMap<>();

    @Autowired
    private Environment env;

    @Autowired
    private PageRenderUtils utils;

    @Autowired
    private CellStyle xstyle;



    public  void getHtmlTableWithJsoup() {
        String link = env.getProperty("target.link");
        doc = utils.renderPage(link);
    }

    public void  getHtmlTableFromLocalhtml() throws IOException {
        File input = new File(env.getProperty("html.input.file"));
        doc = Jsoup.parse(input, "UTF-8", "");

    }

    public void  parseHtml() {
        StringBuilder rowContent = new StringBuilder();
        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        Element header = rows.first();
        Elements thCols = header.select("th");

        boolean isFirstCol = true;
        for(Element col : thCols){
            if(isFirstCol){
                rowContent.append("Complete Status");
            } else {
                rowContent.append(col.text());
            }
            isFirstCol = false;
            rowContent.append("$");
        }

        if(rowContent.length() != 0) {
            rowList.add(rowContent.toString().substring(0, rowContent.length()-1));
        }

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements tdCols = row.select("td");

            rowContent = new StringBuilder();
            isFirstCol = true;
            for(Element col : tdCols){
                if(isFirstCol && !col.getElementsByTag("span").isEmpty()){
                    rowContent.append("❤");
                } else {
                    rowContent.append(col.text());
                }
                isFirstCol = false;

                if(!col.getElementsByTag("a").isEmpty()){
                    String anchorHref = col.getElementsByTag("a").attr("href");
                    linkMap.put(col.text(), env.getProperty("leetcode.link") + anchorHref);
                }

                rowContent.append("$");
            }
            rowList.add(rowContent.toString().substring(0, rowContent.length()-2));
        }
    }

   public void setHeaderRow(String[] columns, XSSFSheet xsheet, XSSFRow xrow){

       Cell cell;

       for (int i = 0; i < columns.length; i++) {
           cell = xrow.createCell(i);
           cell.setCellStyle(xstyle.getHeaderStyle());
           cell.setCellValue(columns[i].toUpperCase().equals("FREQUENCY") ? "Comments" : columns[i]);
       }

       for(int colNum = 0; colNum < columns.length;colNum++)
           xsheet.autoSizeColumn(colNum);
   }




    public void convertToExcel(){
        try {

            // Create Work book
            XSSFWorkbook xwork = new XSSFWorkbook();

            // Create Spread Sheet
            XSSFSheet xsheet = xwork.createSheet("leetcode_questions");
            CreationHelper createHelper = xwork.getCreationHelper();

            //Create Row (Row is inside spread sheet)
            XSSFRow xrow  = null;

            xstyle.setXwork(xwork);
            xstyle.init();

            for (int rowid = 0; rowid < rowList.size(); rowid++) {

                String row = rowList.get(rowid);

                xrow = xsheet.createRow(rowid);

                String[] columns = row.split("\\$");

                if(rowid == 0) {
                    setHeaderRow(columns, xsheet, xrow);
                    continue;
                }


                Cell cell;
                for (int i = 0; i < columns.length; i++) {
                    cell = xrow.createCell(i);

                    if(i == 0){
                        cell.setCellValue(new XSSFRichTextString(columns[i]));
                        cell.setCellStyle(xstyle.getHeartStyle());
                    } else {
                        cell.setCellValue(columns[i]);
                    }

                    //Add hyper link to problem name for easy navigation
                    if(i == 2){
                        XSSFHyperlink link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);
                        link.setAddress(linkMap.get(columns[i]));
                        cell.setHyperlink(link);
                        cell.setCellStyle(xstyle.getHyperLinkStyle());
                    }
                    //Add style to column that contains level : easy -> green, medium -> yellow, hard -> red
                    if(i == columns.length - 1)
                        cell.setCellStyle(xstyle.getLevelStyle(DifficultyLevel.valueOf(columns[i].toUpperCase())));
                }

                for(int colNum = 0; colNum < row.length();colNum++)
                    xsheet.autoSizeColumn(colNum);
            }


            // create date for adding this to our workbook name like workbookname_date
            Date d1 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
            String todaysDate = sdf.format(d1);
            System.out.println(sdf.format(d1));

            //Create file system using specific name
            FileOutputStream fout = new FileOutputStream(new File(env.getProperty("excel.output.file")+todaysDate+".xlsx"));

            xwork.write(fout);
            fout.close();
            System.out.println("myquestions_"+todaysDate+".xlsx written successfully" );
            utils.quit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
