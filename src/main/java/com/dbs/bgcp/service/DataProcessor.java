package com.dbs.bgcp.service;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dbs.bgcp.config.FileConfig;
import com.dbs.bgcp.data.TBgcpColConfig;
import com.dbs.bgcp.data.TBgcpSystems;
import com.dbs.bgcp.entity.JobStatus;
import com.dbs.bgcp.exceptions.NoDataException;
import com.dbs.bgcp.repository.DynamicDataRepository;
import com.dbs.bgcp.repository.SystemConfigRepository;
import com.dbs.bgcp.util.ApplicationUtil;
import com.dbs.bgcp.util.JobStatusMap;
import com.dbs.bgcp.util.Status;
import com.dbs.bgcp.util.StatusMessageType;

@Service
public class DataProcessor {

	Logger log = LoggerFactory.getLogger(DataProcessor.class);

	private final FileConfig fileConfig;

	private final SystemConfigRepository systemConfigRepository;

	private final DynamicDataService fileDataService;

	private final AuditLogService auditLogService;

	private JobStatusAyncService jobStatusAyncService;

	private DynamicDataRepository dynamicDataRepository;
	
	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	 
	
	 @Autowired
	 private  SummaryProcessingService  summaryProcessingService;
	 
	

	public DataProcessor(FileConfig fileConfig, SystemConfigRepository systemConfigRepository,
			AuditLogService auditLogService, DynamicDataService fileDataService,
			JobStatusAyncService jobStatusAyncService, DynamicDataRepository dynamicDataRepository) {
		this.fileConfig = fileConfig;
		this.systemConfigRepository = systemConfigRepository;
		this.fileDataService = fileDataService;
		this.auditLogService = auditLogService;
		this.jobStatusAyncService = jobStatusAyncService;
		this.dynamicDataRepository = dynamicDataRepository;
	}

	public TBgcpSystems fetchBaseSystem(String appCode) {
		TBgcpSystems baseSystem = systemConfigRepository.fetchBaseSystem(appCode);
		return baseSystem;
	}

	public String storeToBDBGCPFiles(String appCode, String startingFrom) 
	{
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long durationInSeconds = 0;
		JobStatusMap.createSystemCodeMap(appCode);
		String response = "Files Records Stored Successfully";
		TBgcpSystems basesystem = fetchBaseSystem(appCode);
		
		System.out.println(basesystem);
		Map<String, Integer> recordCountMap = new HashMap<String, Integer>();

		if (basesystem == null) 
		{
			jobStatusAyncService.updateJobStatusMapAndInsertIntoDB(appCode,
					new Status(StatusMessageType.FAILED.getValue(), 10.0),
					new JobStatus(ApplicationUtil.currentMethodName(), appCode, "CONFIG", 0,
							ApplicationUtil.currentMethodName(), "System configuration fetched successfully", null));

			auditLogService.log(ApplicationUtil.currentMethodName(), "Fetching System Configuration ",
					"SYS_ID " + appCode, "BASE System Configuration Not Found",
					"System configuration for application code " + appCode + "not found in T_EGL_BASE_SYSTEMS Table");
			throw new NoDataException(
					" System configuration for application code - '" + appCode + " not found in BASE_SYSTEM table");
		}else
		{

			switch (startingFrom) 
			{
	
			case "HEADER":
	
			case "DETAIL":
				ArrayList<TBgcpColConfig> colconfiglist =  fetchColConfigConfig(appCode);
				
				List<Map<String, TBgcpColConfig>> recordswithvalue= processInboundDetail(basesystem, appCode, "DETAIL", colconfiglist);
				
				storeToDBBGCPFileValues(basesystem.getDETAIL_TABLE(),  colconfiglist,  recordswithvalue);
				
				
				
				
				endTime = System.currentTimeMillis();
				durationInSeconds = (endTime - startTime) / 1000;
				System.out.println("In Bound Detail Execution time: " + durationInSeconds + " seconds");
				
				break;
				
			case "TRAILER":
				
				break;
			default:
				System.out.println("No Execution for >" + startingFrom
						+ "< There is no such case available. Please check your parameter value!");
			}
		}
		endTime = System.currentTimeMillis();
		durationInSeconds = (endTime - startTime) / 1000;
		System.out.println("Full Execution time: " + durationInSeconds + " seconds");
		return response;
	}
	
	
	
	public String verifyAllColumns(String appCode, String startingFrom) 
	{
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long durationInSeconds = 0;
		JobStatusMap.createSystemCodeMap(appCode);
		String response = "Files verified successfully";
		TBgcpSystems basesystem = fetchBaseSystem(appCode);
		
		System.out.println("appCode >"+appCode+"< "+basesystem);
		//Map<String, Integer> recordCountMap = new HashMap<String, Integer>();

		if (basesystem == null) 
		{
			jobStatusAyncService.updateJobStatusMapAndInsertIntoDB(appCode,
					new Status(StatusMessageType.FAILED.getValue(), 10.0),
					new JobStatus(ApplicationUtil.currentMethodName(), appCode, "CONFIG", 0,
							ApplicationUtil.currentMethodName(), "System configuration fetched successfully", null));

			auditLogService.log(ApplicationUtil.currentMethodName(), "Fetching System Configuration ",
					"SYS_ID " + appCode, "BASE System Configuration Not Found",
					"System configuration for application code " + appCode + "not found in T_EGL_BASE_SYSTEMS Table");
			throw new NoDataException(
					" System configuration for application code - '" + appCode + " not found in BASE_SYSTEM table");
		}else
		{

			switch (startingFrom) 
			{
	
			case "HEADER":
	
			case "DETAIL":
				ArrayList<TBgcpColConfig> colconfiglist =  fetchColConfigConfig(appCode);
				
				List<Map<String, String>> recordswithvalue= fetchFileColumnsValues(basesystem, appCode, "DETAIL", colconfiglist);
				String run_date_value="";
				System.out.println("recordswithvalue SIZE : " + recordswithvalue.size());
				
				if(recordswithvalue !=null && recordswithvalue.size()>0)
				{
					run_date_value=recordswithvalue.get(0).get("run_date");
					
					System.out.println("run_date_value: " + run_date_value);
					
					boolean result=verifyAllColumns(basesystem.getDETAIL_TABLE(), colconfiglist, recordswithvalue, run_date_value, appCode) ;
					
					System.out.println("result: " + result);
				}
				endTime = System.currentTimeMillis();
				durationInSeconds = (endTime - startTime) / 1000;
				System.out.println("In Bound Detail Execution time: " + durationInSeconds + " seconds");
				
				break;
				
			case "TRAILER":
				
				break;
			default:
				System.out.println("No Execution for >" + startingFrom
						+ "< There is no such case available. Please check your parameter value!");
			}
		}
		endTime = System.currentTimeMillis();
		durationInSeconds = (endTime - startTime) / 1000;
		System.out.println("Full Execution time: " + durationInSeconds + " seconds");
		return response;
	}
	
	
	
	public List<Map<String, Object>> generateDetailSummaryByRunDateAndFileName(String runDate, String file_name) 
	{
		 return summaryProcessingService.fetchDetailSummaryByRunDateAndFileName( runDate,  file_name) ;
	}
	
	public void deleteExistingDetailSummaryByRunDateAndFileName(String runDate, String file_name) 
	{
		  summaryProcessingService.deleteExistingDetailSummaryByRunDateAndFileName( runDate,  file_name) ;
	}
	
	/**

	 * @return
	 */
	public String processFilesData(String appCode, String startingFrom) 
	{
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long durationInSeconds = 0;
		JobStatusMap.createSystemCodeMap(appCode);
		String response = "Files processed successfully";
		TBgcpSystems basesystem = fetchBaseSystem(appCode);
		
		System.out.println(basesystem);
		//Map<String, Integer> recordCountMap = new HashMap<String, Integer>();

		if (basesystem == null) 
		{
			jobStatusAyncService.updateJobStatusMapAndInsertIntoDB(appCode,
					new Status(StatusMessageType.FAILED.getValue(), 10.0),
					new JobStatus(ApplicationUtil.currentMethodName(), appCode, "CONFIG", 0,
							ApplicationUtil.currentMethodName(), "System configuration fetched successfully", null));

			auditLogService.log(ApplicationUtil.currentMethodName(), "Fetching System Configuration ",
					"SYS_ID " + appCode, "BASE System Configuration Not Found",
					"System configuration for application code " + appCode + "not found in T_EGL_BASE_SYSTEMS Table");
			throw new NoDataException(
					" System configuration for application code - '" + appCode + " not found in BASE_SYSTEM table");
		}else
		{

			switch (startingFrom) 
			{
	
			case "HEADER":
	
			case "DETAIL":
				ArrayList<TBgcpColConfig> colconfiglist =  fetchColConfigConfig(appCode);
				
				List<Map<String, TBgcpColConfig>> recordswithvalue= processInboundDetail(basesystem, appCode, "DETAIL", colconfiglist);
				
				ArrayList<TBgcpColConfig> uniqueColumnList = colconfiglist.stream()
		                .filter(config -> "Y".equalsIgnoreCase(config.getUnique_Key_Column()))
		                .collect(Collectors.toCollection(ArrayList::new));
				
				System.out.println("uniqueColumnList: " + uniqueColumnList.size());
				
				boolean uniquevalue=verifyUniqueValues(basesystem.getDETAIL_TABLE(), uniqueColumnList, recordswithvalue) ;
				
				System.out.println("uniquevalue: " + uniquevalue);
				
				
				endTime = System.currentTimeMillis();
				durationInSeconds = (endTime - startTime) / 1000;
				System.out.println("In Bound Detail Execution time: " + durationInSeconds + " seconds");
				
				break;
				
			case "TRAILER":
				
				break;
			default:
				System.out.println("No Execution for >" + startingFrom
						+ "< There is no such case available. Please check your parameter value!");
			}
		}
		endTime = System.currentTimeMillis();
		durationInSeconds = (endTime - startTime) / 1000;
		System.out.println("Full Execution time: " + durationInSeconds + " seconds");
		return response;
	}
	
	public List<String>  getconfigcolumnheaders(String appCode) 
	{
		 List<String> headers = new ArrayList<>();
		 String columnConfigFilePath = fileConfig.getBaseFilePath() + "/EXCELCONFIG/BGCPColumnConfig.xlsx";
		 
	        try (FileInputStream fis = new FileInputStream(new File(columnConfigFilePath));
	        		XSSFWorkbook workbook = new XSSFWorkbook(fis)) 
	        {
	            XSSFSheet sheet = workbook.getSheet(appCode);
	            if(sheet!=null)
	            {
		            XSSFRow headerRow = sheet.getRow(0);
		            if(headerRow!=null)
		            {
			            for (Cell cell : headerRow) 
			            {
			                headers.add(cell.getStringCellValue());
			            }
		            }
		            else
		            {
		            	System.out.println("There is no Header Row!");
		            }
		        }
	            else
	            {
	            	System.out.println("There is no config for application "+appCode);
	            }
	        }
	        catch (Exception e) 
	        {
	        	System.out.println("/EXCELCONFIG/BGCPColumnConfig.xlsx Config excel not found for "+appCode+" !");
	            e.printStackTrace();
	            throw new RuntimeException("Failed to read Excel file");
	        }
	        return headers;
	}
	
	
	
	public String configtableandcolumns(String appCode) 
	{
		String message="";
		String columnConfigFilePath = fileConfig.getBaseFilePath() + "/EXCELCONFIG/BGCPColumnConfig.xlsx";
		HashMap<String,String> target_tables=new HashMap<String,String>();
		HashMap<String,String> target_attributes=new HashMap<String,String>();
		 List<String> target_attributesList = new ArrayList<>();
		
		deleteRecordsByAppCode("T_BGCP_COL_CONFIG",  appCode);
		
        try (FileInputStream fis = new FileInputStream(new File(columnConfigFilePath));
        		XSSFWorkbook workbook = new XSSFWorkbook(fis)) 
        {

        	XSSFSheet sheet = workbook.getSheet(appCode);
            if(sheet!=null)
            {
	            XSSFRow headerRow = sheet.getRow(0);
	
	            if(headerRow!=null)
	            {
		            List<String> columnHeaders = new ArrayList<>();
		            for (Cell cell : headerRow) 
		            {
		                columnHeaders.add(cell.getStringCellValue());
		            }
		            
		            if(!doesTableExist("T_BGCP_COL_CONFIG") )
		            {
		            	createTable("T_BGCP_COL_CONFIG", columnHeaders);
		            }
		
		            for (int i = 1; i <= sheet.getLastRowNum(); i++) 
		            { 
		                XSSFRow row = sheet.getRow(i);
		                List<Object> rowData = new ArrayList<>();
		                for(int j=0;j<columnHeaders.size();j++)
		                {
		                	Cell cell = row.getCell(j);
		                	if(cell!=null)
		                	{
			                	Object cellValue=getCellValue(cell);
			                	System.out.println(columnHeaders.get(j)+" >"+cellValue+"<");
			                	if("Target_Table".equals(columnHeaders.get(j)))
			                	{
			                		target_tables.put("Target_Table", (String)cellValue);
			                	}
			                	
			                	if("Target_Attribute".equals(columnHeaders.get(j)))
			                	{
			                		if(target_attributes.get((String)cellValue) ==null  )
			                		{
			                			target_attributesList.add((String)cellValue);
			                			target_attributes.put( (String)cellValue , (String)cellValue);
			                		}
			                		else
			                		{
			                			System.out.println(columnHeaders.get(j)+" >"+cellValue+"< ALREADY EXISTS");
			                		}
			                	}
			                    rowData.add(cellValue);
		                	}
		                	else
		                	{
		                		System.out.println(columnHeaders.get(j)+" >NULL<");
		                		rowData.add("");
		                	}
		                }
		                insertRow("T_BGCP_COL_CONFIG", columnHeaders, rowData);
		            }
		            
		            
		            if(target_tables.get("Target_Table")!=null && !doesTableExist( target_tables.get("Target_Table") )  )
		            {
		            	createTable(target_tables.get("Target_Table"), target_attributesList);
		            }
		            
		            
		            System.out.println("Excel data successfully stored in the database!");
		            message="Excel data successfully stored in the database!";
	            }
	            else
	            {
	            	System.out.println("There is no Header Row!");
	            }
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            message="Failed to process Excel file";
            throw new RuntimeException("Failed to process Excel file");
        }
        
        return message;
    }

    private void createTable(String tableName, List<String> columnHeaders) 
    {
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (String column : columnHeaders) {
            createTableSQL.append(column).append(" NVARCHAR(MAX),");
        }
        createTableSQL.setLength(createTableSQL.length() - 1); 
        createTableSQL.append(")");
        jdbcTemplate.execute(createTableSQL.toString());
        System.out.println("Table '" + tableName + "' created successfully.");
    }
    
    public boolean doesTableExist(String tableName) {
        String sql = "SELECT CASE WHEN EXISTS (" +
                     "SELECT 1 FROM INFORMATION_SCHEMA.TABLES " +
                     "WHERE TABLE_NAME = ? AND TABLE_TYPE = 'BASE TABLE') THEN 1 ELSE 0 END";

        Integer result = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        return result != null && result == 1;
    }
    

    public int deleteRecordsByAppCode(String tableName, String appCode) {
        String sql = "DELETE FROM " + tableName + " WHERE app_code = ?";
        return jdbcTemplate.update(sql, appCode);
    }
    
    private void insertRow(String tableName, List<String> columnHeaders, List<Object> rowData) {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " (");
        insertSQL.append(String.join(",", columnHeaders));
        insertSQL.append(") VALUES (");
        insertSQL.append("?,".repeat(columnHeaders.size()));
        insertSQL.setLength(insertSQL.length() - 1); 
        insertSQL.append(")");
        
        System.out.println(insertSQL.toString());
        jdbcTemplate.update(conn -> 
        {
            PreparedStatement ps = conn.prepareStatement(insertSQL.toString());
            for (int i = 0; i < rowData.size(); i++) 
            {
                ps.setObject(i + 1, rowData.get(i));
            }
            return ps;
        });
    }

    private Object getCellValue(Cell cell) {
        return switch (cell.getCellType()) 
        		{
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case BLANK ->  "";
            default -> null;
        };
    }
	
	
	private String getDoubleAmountValue(String input)
	{
		input = input.trim();
		if (input.length() < 2)
		{
			return "";
		}
		int position = input.length() - 2;
		StringBuilder sb = new StringBuilder(input);
		sb.insert(position, '.');
		String result = sb.toString();
		return result;
	}

	private void copyFields(Object source, Object target)
	{
		try {
			Class<?> sourceClass = source.getClass();
			Class<?> targetClass = target.getClass();
			for (java.lang.reflect.Field sourceField : sourceClass.getDeclaredFields()) {
				if (java.lang.reflect.Modifier.isStatic(sourceField.getModifiers())
						|| java.lang.reflect.Modifier.isFinal(sourceField.getModifiers())) {
					continue;
				}

				sourceField.setAccessible(true); // Allow access to private fields
				Object value = sourceField.get(source); // Get the field value from source

				try {
					java.lang.reflect.Field targetField = targetClass.getDeclaredField(sourceField.getName());
					targetField.setAccessible(true); // Allow access to private fields
					targetField.set(target, value); // Set the field value to target
				} catch (NoSuchFieldException ignored) {
					ignored.printStackTrace();
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error copying fields", e);
		}
	}

	// Utility method to create a distinct filter
	public static <T> java.util.function.Predicate<T> distinctByKey(
			java.util.function.Function<? super T, Object> keyExtractor)
	{
		java.util.Set<Object> seen = new java.util.HashSet<>();
		return t -> seen.add(keyExtractor.apply(t));
	}
	
	
	public List<Map<String, String>> fetchFileColumnsValues(TBgcpSystems basesystem, String appCode, String startingFrom,
			ArrayList<TBgcpColConfig> colconfiglist)
	{
		
		System.out.println("fetchFileColumnsValues STARTS");
		String detailFilePath = fileConfig.getBaseFilePath() + "/" + basesystem.getFileFolder() + "/"
				+ basesystem.getDetailFile();

		List<String> detailLines = ApplicationUtil.readFileLines(detailFilePath);
		List<String> headerLines = ApplicationUtil.readFileLines(detailFilePath);
		System.out.println("detailLines >" + detailLines.size());
		System.out.println("headerLines >" + headerLines.size());
		System.out.println("colconfiglist.size() >" + colconfiglist.size());
		
		List<Map<String, String>> records = new ArrayList<Map<String, String>>();
		detailLines.forEach(line -> 
		{
			System.out.println(line);
            Map<String, String> record = new HashMap<>();
            record.put("FULL_LINE", line);
            
            for (int i = 0; i < colconfiglist.size(); i++) 
            {
            	TBgcpColConfig colConfig = colconfiglist.get(i);
            	String value="";
            	try
            	{
            		//System.out.println("colConfig.get Start_Position() >"+colConfig.getStart_Position()+"< colConfig.get End_Position()>"+colConfig.getEnd_Position()+"<");
            		value = line.substring(Integer.parseInt(colConfig.getStart_Position() )- 1, Integer.parseInt(colConfig.getEnd_Position()));
            		System.out.println("value >"+value+"<");
            		 record.put(colConfig.getSource_Attribute(), value);
            	}
            	catch(Exception e)
            	{
            		System.err.println(" fetchFileColumnsValues ERROR colConfig.get Start_Position() >"+colConfig.getStart_Position()+"<");
            		colConfig.setHasError(true);
            		colConfig.setErrorMessage(e.getMessage());
            	}
            }
            records.add(record);
		});
		System.out.println("Records Processed "+records.size() );
		return records;
	}
	
	

	public List<Map<String, TBgcpColConfig>> processInboundDetail(TBgcpSystems basesystem, String appCode, String startingFrom,
			ArrayList<TBgcpColConfig> colconfiglist)
	{
		String detailFilePath = fileConfig.getBaseFilePath() + "/" + basesystem.getFileFolder() + "/"
				+ basesystem.getDetailFile();

		List<String> detailLines = ApplicationUtil.readFileLines(detailFilePath);
		System.out.println("detailLines >" + detailLines.size());
		
		List<Map<String, TBgcpColConfig>> records = new ArrayList<>();
		detailLines.forEach(line -> 
		{
			System.out.println(line);
            Map<String, TBgcpColConfig> record = new HashMap<>();
            for (int i = 0; i < colconfiglist.size(); i++) 
            {
            	TBgcpColConfig colConfig = colconfiglist.get(i);
            	String value="";
            	try
            	{
            		value = line.substring(Integer.parseInt(colConfig.getStart_Position() )- 1, Integer.parseInt(colConfig.getEnd_Position()));
            		System.out.println("value >"+value+"<");
            		colConfig.setColValue(value);
            	}
            	catch(Exception e)
            	{
            		colConfig.setHasError(true);
            		colConfig.setErrorMessage(e.getMessage());
            	}
                record.put(colConfig.getSource_Attribute(), colConfig);
            }
            records.add(record);
		});
		System.out.println("Records Processed "+records.size() );
		return records;
	}

	 public void storeToDBBGCPFileValues(String tableName, ArrayList<TBgcpColConfig> colconfiglist, List<Map<String, TBgcpColConfig>> recordsWithValue)
	 {
		 try 
		 { 
	            StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
	            StringBuilder valuePlaceholders = new StringBuilder();

	            for (TBgcpColConfig config : colconfiglist) 
	            {
	                sql.append(config.getTarget_Attribute()).append(", ");
	                valuePlaceholders.append("?, ");
	            }

	            sql.setLength(sql.length() - 2);
	            valuePlaceholders.setLength(valuePlaceholders.length() - 2);
	            sql.append(") VALUES (").append(valuePlaceholders).append(")");
	            
	            System.out.println(" INSERT SQL "+sql);
	            for (Map<String, TBgcpColConfig> record : recordsWithValue) 
	            {
	                List<Object> values = new ArrayList<>();
	                for (TBgcpColConfig config : colconfiglist) 
	                {
	                    String columnName = config.getTarget_Attribute();
	                    TBgcpColConfig columnData = record.get(columnName);
	                    values.add(columnData != null ? columnData.getColValue() : null);
	                }
	                jdbcTemplate.update(sql.toString(), values.toArray());
	            }
	            System.out.println("All records inserted successfully into table: " + tableName);
	     } 
		 catch (Exception e)
		 {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to insert records into database", e);
	     }       
	}
		
	public boolean verifyUniqueValues(String tableName, ArrayList<TBgcpColConfig> uniqueColumnList, List<Map<String, TBgcpColConfig>> recordsWithValue) 
	{
        try 
        {
        	 System.out.println("Verify Unique Values STARTS " );
        	 
        	boolean hasError=false;
        	for (Map<String, TBgcpColConfig> record : recordsWithValue) 
            {
            	StringBuffer sql = new StringBuffer ("SELECT COUNT(*) FROM " + tableName + " WHERE ");
            	List<Object> queryParams = new ArrayList<>();
                for (TBgcpColConfig config : uniqueColumnList) 
                {
                    String columnName = config.getTarget_Attribute(); 
                    String columnValue = record.get(columnName).getColValue(); 
                    sql.append(columnName).append(" = ? AND ");
                    queryParams.add(columnValue);
                }
                
                sql.setLength(sql.length() - 4);
                int count = jdbcTemplate.queryForObject(sql.toString(), queryParams.toArray(), Integer.class);

                if (count == 0) 
                {
                    System.out.println("Value not found in DB for column value: " + count);
                    hasError=true;
                }
                else
                {
                	System.out.println("Value found in DB for column value: " + count);
                }
            }
        	System.out.println("Verify Unique Values ENDS " +hasError);
        	 return hasError;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error verifying unique values in database", e);
        }
    }
	
	
	public boolean verifyAllColumns(String tableName, ArrayList<TBgcpColConfig> allColumnList, List<Map<String, String>> recordsWithValue, String run_date_value, String appCode) 
	{
		boolean hasError=false;
        try 
        {
        	System.out.println("Verify ALL COLUMNS STARTS " );
        	deleteExistingDetailSummaryByRunDateAndFileName(run_date_value , appCode);
        	
        	hasError= summaryProcessingService.verifyDataAndStoreSummary(tableName, allColumnList, recordsWithValue, run_date_value, "run_date", appCode );
        } catch (Exception e) {
        	hasError=true;
            e.printStackTrace();
            throw new RuntimeException("Error verifying unique values in database", e);
        }
        return hasError;
    }
	
	
	public boolean allColumnsCheck(String tableName, ArrayList<TBgcpColConfig> uniqueColumnList, List<Map<String, TBgcpColConfig>> recordsWithValue) 
	{
        try 
        {
        	 System.out.println("Verify Unique Values STARTS " );
        	boolean hasError=false;
        	for (Map<String, TBgcpColConfig> record : recordsWithValue) 
            {
            	StringBuffer sql = new StringBuffer ("SELECT COUNT(*) FROM " + tableName + " WHERE ");
            	List<Object> queryParams = new ArrayList<>();
                for (TBgcpColConfig config : uniqueColumnList) 
                {
                    String columnName = config.getTarget_Attribute(); 
                    String columnValue = record.get(columnName).getColValue(); 
                    sql.append(columnName).append(" = ? AND ");
                    queryParams.add(columnValue);
                }
                
                sql.setLength(sql.length() - 4);
                int count = jdbcTemplate.queryForObject(sql.toString(), queryParams.toArray(), Integer.class);

                if (count == 0) 
                {
                    System.out.println("Value not found in DB for column value: " + count);
                    hasError=true;
                }
                else
                {
                	System.out.println("Value found in DB for column value: " + count);
                }
            }
        	System.out.println("Verify Unique Values ENDS " +hasError);
        	 return hasError;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error verifying unique values in database", e);
        }
    }
	
	public ArrayList<TBgcpColConfig> fetchColConfigConfig(String systemId) {
		System.out.println("fetchColConfigConfig STARTS "+systemId);
		return systemConfigRepository.fetchColConfigConfig(systemId);
	}

}