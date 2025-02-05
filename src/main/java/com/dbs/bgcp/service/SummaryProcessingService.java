package com.dbs.bgcp.service;

import org.springframework.beans.factory.annotation.Autowired;
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
            for ( Map<String, String>   fileRecord  : recordsWithValue)
            {
                boolean fullymatchFound = false;
                List<String> finalnonmatchcolumnsList = null;
                int globalnonmatchcount=0;
                for (Map<String, Object> dbRecord: dbRecords)
                {
                	if("FULL_MATCH".equals(dbRecord.get("FULL_MATCH")) ) continue;
                	boolean fullymatch =true;
                	List<String> localnonmatchcolumnsList = new ArrayList<>();
                	int notmatchCount=0;
                	boolean hasnomatch=false;
                    for(TBgcpColConfig colconfig:allColumnList)
                    {
                    	colconfig.setNonMatchColumnInfo("");
                    	System.out.println(dbRecord.get(colconfig.getTarget_Attribute()).toString() +" "+fileRecord.get(colconfig.getSource_Attribute()));

                    	boolean colmatch= dbRecord.get(colconfig.getTarget_Attribute()).toString().equals(fileRecord.get(colconfig.getSource_Attribute()));

                    	fullymatch = fullymatch && colmatch;
                    	if(colmatch)
                    	{
                    		colconfig.setMatchNonMatch("M");
                    	}
                    	else
                    	{
                    		hasnomatch=true;
                    		colconfig.setMatchNonMatch("N");
                    		colconfig.setNonMatchColumnInfo(dbRecord.get(colconfig.getTarget_Attribute()).toString() +" != "+fileRecord.get(colconfig.getSource_Attribute() ) );
                    	}
                    	if(!fullymatch)
                    	{
                    		notmatchCount++;
                    		localnonmatchcolumnsList.add(dbRecord.get(colconfig.getTarget_Attribute()).toString());
                    		System.out.println("COLUMN NOT MATCH >"+notmatchCount+"<");
                    	}
                    }

                    int nonmatchcount=0;
                    if(hasnomatch)
                    {
	                    for(TBgcpColConfig colconfig:allColumnList)
	                    {
	                    	if("N".equals(colconfig.getMatchNonMatch()) )
	                    	{
	                    		nonmatchcount++;
	                    		localnonmatchcolumnsList.add(colconfig.getNonMatchColumnInfo());
	                    	}
	                    }

	                    if(nonmatchcount>0 )
	                    {
	                    	if(globalnonmatchcount==0 || nonmatchcount < globalnonmatchcount)
	                    	{
	                    		globalnonmatchcount=nonmatchcount;
	                    		finalnonmatchcolumnsList=localnonmatchcolumnsList;
	                    	}
	                    }
                    }

                    if (fullymatch)
                    {
                    	dbRecord.put("FULL_MATCH", "FULL_MATCH");
                        fullymatchFound = true;
                        break;
                    }
                }

                if (fullymatchFound)
                {
                	fileRecord.put("FULL_MATCH", "FULL_MATCH");
                    matchedRecords++;
                    insertDetailSummaryIntoTable(appCode, runDateValue, "M", lineNumber, fileRecord.get("FULL_LINE"));
                }
                else
                {
                    unmatchedRecords++;
                    insertDetailSummaryIntoTable(appCode, runDateValue, "N",  lineNumber, globalnonmatchcount+" "+finalnonmatchcolumnsList.toString());
                }
                lineNumber++;
            }

            insertSummaryIntoTable(appCode, runDateValue, totalLinesInFile, totalLinesInTable, matchedRecords, unmatchedRecords);

        }
        catch (Exception e)
        {
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
