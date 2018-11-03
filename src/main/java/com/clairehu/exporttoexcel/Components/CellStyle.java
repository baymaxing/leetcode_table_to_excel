package com.clairehu.exporttoexcel.Components;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class CellStyle {

    private XSSFWorkbook xwork;

    private XSSFCellStyle hardStyle;
    private XSSFCellStyle mediumStyle;
    private XSSFCellStyle easyStyle;
    private XSSFCellStyle headerStyle;
    private XSSFCellStyle hyperLinkStyle;
    private XSSFCellStyle heartStyle;

    private XSSFFont whiteFont;
    private XSSFFont hardfont;
    private XSSFFont mediumfont;
    private XSSFFont easyfont;


    public void init(){

        XSSFFont whitefont = xwork.createFont();
        whitefont.setBoldweight(whitefont.BOLDWEIGHT_BOLD);
        whitefont.setColor(IndexedColors.WHITE.index);

        XSSFFont hardfont = xwork.createFont();
        hardfont.setBoldweight(hardfont.BOLDWEIGHT_BOLD);
        hardfont.setColor(IndexedColors.DARK_RED.index);

        XSSFFont mediumfont = xwork.createFont();
        mediumfont.setBoldweight(mediumfont.BOLDWEIGHT_BOLD);
        mediumfont.setColor(IndexedColors.DARK_YELLOW.index);

        XSSFFont easyfont = xwork.createFont();
        easyfont.setBoldweight(easyfont.BOLDWEIGHT_BOLD);
        easyfont.setColor(IndexedColors.DARK_GREEN.index);

        hardStyle = xwork.createCellStyle();
        hardStyle.setFont(hardfont);
        hardStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        hardStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        mediumStyle = xwork.createCellStyle();
        mediumStyle.setFont(mediumfont);
        mediumStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        mediumStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        easyStyle = xwork.createCellStyle();
        easyStyle.setFont(easyfont);
        easyStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        easyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        XSSFFont xfont = xwork.createFont();
        xfont.setBoldweight(xfont.BOLDWEIGHT_BOLD);
        xfont.setColor(IndexedColors.WHITE.getIndex());

        headerStyle = xwork.createCellStyle();
        headerStyle.setFont(xfont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        hyperLinkStyle = xwork.createCellStyle();
        XSSFFont hlinkfont = xwork.createFont();
        hlinkfont.setUnderline(XSSFFont.U_SINGLE);
        hlinkfont.setColor(IndexedColors.LIGHT_BLUE.index);
        hyperLinkStyle.setFont(hlinkfont);

        heartStyle = xwork.createCellStyle();
        XSSFFont heartFont = xwork.createFont();
        heartFont.setColor(IndexedColors.GREEN.index);
        heartStyle.setFont(heartFont);

    }

    public XSSFFont getWhiteFont() {
        return whiteFont;
    }

    public void setWhiteFont(XSSFFont whiteFont) {
        this.whiteFont = whiteFont;
    }

    public XSSFFont getHardfont() {
        return hardfont;
    }

    public void setHardfont(XSSFFont hardfont) {
        this.hardfont = hardfont;
    }

    public XSSFFont getMediumfont() {
        return mediumfont;
    }

    public void setMediumfont(XSSFFont mediumfont) {
        this.mediumfont = mediumfont;
    }

    public XSSFFont getEasyfont() {
        return easyfont;
    }

    public void setEasyfont(XSSFFont easyfont) {
        this.easyfont = easyfont;
    }

    public XSSFWorkbook getXwork() {
        return xwork;
    }

    public void setXwork(XSSFWorkbook xwork) {
        this.xwork = xwork;
    }


    public XSSFCellStyle getHardStyle() {
        return hardStyle;
    }

    public void setHardStyle(XSSFCellStyle hardStyle) {
        this.hardStyle = hardStyle;
    }

    public XSSFCellStyle getMediumStyle() {
        return mediumStyle;
    }

    public void setMediumStyle(XSSFCellStyle mediumStyle) {
        this.mediumStyle = mediumStyle;
    }

    public XSSFCellStyle getEasyStyle() {
        return easyStyle;
    }

    public void setEasyStyle(XSSFCellStyle easyStyle) {
        this.easyStyle = easyStyle;
    }

    public XSSFCellStyle getHeaderStyle(){
        return headerStyle;
    }

    public void setHeaderStyle(XSSFCellStyle headerStyle) {
        this.headerStyle = headerStyle;
    }


    public XSSFCellStyle getHyperLinkStyle() {
        return hyperLinkStyle;
    }

    public void setHyperLinkStyle(XSSFCellStyle hyperLinkStyle) {
        this.hyperLinkStyle = hyperLinkStyle;
    }

    public void setHeartStyle(XSSFCellStyle heartStyle) {
        this.heartStyle = heartStyle;
    }

    public XSSFCellStyle getHeartStyle() {
        return heartStyle;
    }

    public XSSFCellStyle getLevelStyle(DifficultyLevel level){

        switch(level){
            case EASY : return easyStyle;
            case MEDIUM: return mediumStyle;
            case HARD: return hardStyle;
            default: return null;
        }
    }

}
