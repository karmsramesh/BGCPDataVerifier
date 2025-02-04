package com.dbs.bgcp.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ExcelReportGenerator 
{

    public String generateExcelReport(List<Map<String, Object>> data, String runDate, String file_name) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Summary Report");

        CellStyle redCellStyle = workbook.createCellStyle();
        redCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        CellStyle greenCellStyle = workbook.createCellStyle();
        greenCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "File Name", "Run Date", "Match/Unmatch", "Line Number", "Record Line", "Created At"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        int rowNum = 1;
        for (Map<String, Object> record : data) 
        {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(record.get("id").toString());
            row.createCell(1).setCellValue(record.get("file_name").toString());
            row.createCell(2).setCellValue(record.get("run_date").toString());
            
            Cell matchCell = row.createCell(3);
            String matchStatus = record.get("MATCH_OR_UNMATCH").toString().trim();
            matchCell.setCellValue(matchStatus);
            if ("N".equalsIgnoreCase(matchStatus)) {
                matchCell.setCellStyle(redCellStyle);
            }
            else if("M".equalsIgnoreCase(matchStatus)) {
                matchCell.setCellStyle(greenCellStyle);
            }
            
            row.createCell(4).setCellValue(record.get("LINE_NUMBER").toString());
            row.createCell(5).setCellValue(record.get("RECORD_LINE").toString());
            row.createCell(6).setCellValue(record.get("created_at").toString());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String filePath = "C:\\Users\\S03\\Desktop\\DBS\\BGCPFILES\\SUMMARY\\BGCP_Summary_Report_"+file_name+"_" + runDate + ".xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) 
        {
            workbook.write(fileOut);
        }
        workbook.close();

        return filePath;
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
