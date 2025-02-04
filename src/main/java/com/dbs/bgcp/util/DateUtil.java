package com.dbs.bgcp.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtil {

	
	public static void main(String args[]) {
		//getFormattedDate("''20240319''");
		getconvertDateFormatDLCL("''20240319''");
		
	}
	
	public static String getFormattedDate(String dateStr)
	{

        // Define input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the date and format it
        LocalDate date = LocalDate.parse(dateStr.replace("'", ""), inputFormatter);
        String formattedDate = date.format(outputFormatter);

        // Print the result
       // System.out.println("Formatted Date: " + formattedDate);
        return formattedDate;
	}
	
	//Detail and Control Generation Report
	
	public static String getconvertDateFormatDLCL(String applnDate) {
		// Define input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("ddMMyy");


        // Parse the date and format it
        LocalDate localDate = LocalDate.parse(applnDate.replace("'", ""), inputFormatter);
        String formattedDate = localDate.format(outputFormatter);
        
       // Date sqlDate = Date.valueOf(localDate);
      
        return formattedDate;
		
			
		
    }
	
	public static String getDD_MON_YYYYDate(String dateStr)
	{

        // Define input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        // Parse the date and format it
        LocalDate date = LocalDate.parse(dateStr.replace("'", ""), inputFormatter);
        String formattedDate = date.format(outputFormatter);

        // Print the result
       // System.out.println("Formatted Date: " + formattedDate);
        return formattedDate;
	}
	
		
	public static  Date convertSQLDate(String dateStr)
	{

        // Define input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the date and format it
        LocalDate localDate = LocalDate.parse(dateStr.replace("'", ""), inputFormatter);
        String formattedDate = localDate.format(outputFormatter);
        
        Date sqlDate = Date.valueOf(localDate);
        // Print the result
       // System.out.println("Formatted Date: " + formattedDate);
        return sqlDate;
	}

    public static String getCurrentDD_MON_YYYYDate() {
          LocalDate currentDate = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
         return currentDate.format(formatter);
    }

    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
       return currentDate.format(formatter);
       }

    
	
    public static String getYYYY_MM_DD(Date parsedDate) {
        if (parsedDate == null) {
            return "NULL"; // Or return a default value if necessary
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(parsedDate);
    }


   


}
