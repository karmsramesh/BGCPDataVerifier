package com.dbs.bgcp.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dbs.bgcp.data.TBgcpColConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SummaryProcessingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StagingBGCPService StagingbGCPService;

    @Value("${app.GCXPSTR.targetAttributes}")
    private String systemAttribute;

    String[] systemAttributeList = null;
    @PostConstruct
    public void init() {
        systemAttributeList = systemAttribute.split(",");
    }


    /**
     * Processes the file and stores the summary in the bgcp_summary table.
     */
    public boolean verifyDataAndStoreSummary(String tableName, ArrayList<TBgcpColConfig> allColumnList, List<Map<String, String>> recordsWithValue,
    		 String runDateValue, String dateColumn, String appCode) 
    {
    	System.out.println("verifyDataAndStoreSummary STARTS ");
    	boolean processingError=false;
        try 
        {
            List<Map<String, Object>> dbRecords = StagingbGCPService.fetchRecordsByRunDate(runDateValue, tableName,  dateColumn);


            int totalLinesInFile = recordsWithValue.size();
            int totalLinesInTable = dbRecords.size();

            int matchedRecords = 0;
            int unmatchedRecords = 0;
            int lineNumber=1;
            StringBuilder uniqueKey = new StringBuilder();
            StringBuilder whereCondition = new StringBuilder();
            for ( Map<String, String>   fileRecord  : recordsWithValue)
            {
                for(int i=0; i<systemAttributeList.length;i++) {
                    whereCondition.append(systemAttributeList[i]).append("=").append(fileRecord.get(systemAttributeList[i])).append(" AND ");
                    uniqueKey.append(fileRecord.get(systemAttributeList[i])) ;
                    if(i!=systemAttributeList.length-1) {
                        uniqueKey.append("/");
                    }
                    whereCondition.append(dateColumn).append("=").append(runDateValue);
                }
                boolean matchFound = false;
                Map<String, Object> stagingGcsp = StagingbGCPService.getStagingRecord(whereCondition.toString());
                List<String> unMatchedColumns = new ArrayList<>();
                for(TBgcpColConfig colconfig:allColumnList)
                {
                    if(!stagingGcsp.get(colconfig.getTarget_Attribute()).toString().equals(fileRecord.get(colconfig.getSource_Attribute()))) {
                        matchFound = false;
                        unMatchedColumns.add(colconfig.getTarget_Attribute());
                    }

                }
                if (matchFound)
                {
                    matchedRecords++;
                    insertDetailSummaryIntoTable(appCode, runDateValue, "M", lineNumber, fileRecord.get("FULL_LINE"));
                } else
                {
                    unmatchedRecords++;
                    System.out.println(String.join(",", unMatchedColumns));
                    System.out.println(uniqueKey);
                    insertDetailSummaryIntoTable(appCode, runDateValue, "N",  lineNumber,fileRecord.get("FULL_LINE"));
                }
                lineNumber++;
                
            }

            insertSummaryIntoTable(appCode, runDateValue, totalLinesInFile, totalLinesInTable, matchedRecords, unmatchedRecords);

        } catch (Exception e) {
        	processingError=true;
            e.printStackTrace();
            throw new RuntimeException("Error processing file and storing summary", e);
        }
        
        System.out.println("processFileAndStoreSummary ENDS ");
        return processingError;
    }

    private void insertSummaryIntoTable(String fileName, String runDate, int totalLinesInFile, int totalLinesInTable, int matchedRecords, int unmatchedRecords) {
        String sql = "INSERT INTO T_bgcp_summary (file_name, run_date, total_lines_in_file, total_lines_in_table, matched_records, unmatched_records) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, fileName, runDate, totalLinesInFile, totalLinesInTable, matchedRecords, unmatchedRecords);

        System.out.println("Summary inserted successfully for file: " + fileName);
    }
    
    private void insertDetailSummaryIntoTable(String fileName, String runDate, String MATCH_OR_UNMATCH, int LINE_NUMBER, String RECORD_LINE) {
        String sql = "INSERT INTO T_BGCP_DETAIL_SUMMARY (file_name, run_date, MATCH_OR_UNMATCH, LINE_NUMBER, RECORD_LINE, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, GETDATE())";
        jdbcTemplate.update(sql, fileName, runDate, MATCH_OR_UNMATCH, LINE_NUMBER, RECORD_LINE);

        System.out.println("Detail Summary inserted successfully for file: " + fileName);
    }
    
    
    public List<Map<String, Object>> fetchDetailSummaryByRunDateAndFileName(String runDate, String file_name) {
        String sql = "SELECT id, file_name, run_date, MATCH_OR_UNMATCH, LINE_NUMBER, RECORD_LINE, created_at " +
                     "FROM T_BGCP_DETAIL_SUMMARY " +
                     "WHERE run_date = ? AND file_name=? ";
        return jdbcTemplate.queryForList(sql, runDate, file_name);
    }
    
    public void deleteExistingDetailSummaryByRunDateAndFileName(String runDate, String file_name) {
        String sql = "delete  " +
                     "FROM T_BGCP_DETAIL_SUMMARY " +
                     "WHERE run_date = ? AND file_name=? ";
        
        System.out.println("T_BGCP_DETAIL_SUMMARY runDate >"+runDate+" file_name>"+file_name+"<");
        
        int recordsdeleted= jdbcTemplate.update(sql, runDate, file_name);
        System.out.println("T_BGCP_DETAIL_SUMMARY recordsdeleted >"+recordsdeleted);
    }
    
    
    
    
}
