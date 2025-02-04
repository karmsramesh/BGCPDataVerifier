package com.dbs.bgcp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.bgcp.service.DataProcessor;
import com.dbs.bgcp.service.ExcelReportGenerator;

@RestController
@RequestMapping("/bgcpapi")
public class DataProcessorController {
    @Autowired
    private final DataProcessor dataProcessor;
    
    @Autowired
    private ExcelReportGenerator excelReportGenerator;

    public DataProcessorController(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }
    
    @GetMapping("/testingapp")
    public ResponseEntity<String> testing() 
    {
        return new ResponseEntity<>("TESTING OK", HttpStatus.OK);
    }

    @GetMapping("/processBGCPFiles")
    public ResponseEntity<String> processFiles(@RequestParam String applCode, @RequestParam String startingFrom) 
    {
        return new ResponseEntity<>(dataProcessor.processFilesData(applCode, startingFrom), HttpStatus.OK);
    }
    
    @GetMapping("/verifyAllColumns")
    public ResponseEntity<String> verifyAllColumns(@RequestParam String applCode, @RequestParam String startingFrom) 
    {
        return new ResponseEntity<>(dataProcessor.verifyAllColumns(applCode, startingFrom), HttpStatus.OK);
    }
    
    
    @GetMapping("/generateSummaryReport")
    public String generateSummaryReport(
            @RequestParam String fileName,
            @RequestParam String runDate) {
        try {
            List<Map<String, Object>> summaryData = dataProcessor.generateDetailSummaryByRunDateAndFileName(runDate, fileName );

            String filePath = excelReportGenerator.generateExcelReport(summaryData, runDate, fileName);
            
            return "Summary report generated successfully! File stored at: " + filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating summary report: " + e.getMessage();
        }
    }
    
    
    
    
    @GetMapping("/storeToDBBGCPFiles")
    public ResponseEntity<String> storeToDBBGCPFiles(@RequestParam String applCode, @RequestParam String startingFrom) 
    {
        return new ResponseEntity<>(dataProcessor.storeToBDBGCPFiles(applCode, startingFrom), HttpStatus.OK);
    }
    
    @GetMapping("/getconfigcolumnheaders")
    public ResponseEntity<List<String>> getconfigcolumnheaders(@RequestParam String applCode) 
    {
    	return new ResponseEntity<>(dataProcessor.getconfigcolumnheaders(applCode), HttpStatus.OK);
    }
    
    @GetMapping("/configtableandcolumns")
    public ResponseEntity<String> configtableandcolumns(@RequestParam String applCode) 
    {
    	return new ResponseEntity<>(dataProcessor.configtableandcolumns(applCode), HttpStatus.OK);
    }
}
