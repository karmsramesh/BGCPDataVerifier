package com.dbs.bgcp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationUtil {
    private static final Logger log = LoggerFactory.getLogger(ApplicationUtil.class);

    /**
     * This method converts {@link java.util.Date} to {@link java.sql.Date}
     * @return
     */
    public static Date utilDateToSqlDate() {
        return new Date(currentDate().getTime());
    }

    /**
     * This method returns the current date
     * @return
     */
    public static java.util.Date currentDate() {
        return new java.util.Date();
    }

    /**
     * This method converts {@link java.util.Date} to {@link java.sql.Timestamp}
     * @return
     */
    public static Timestamp utilDateToSqlTimestamp() {
        return new Timestamp(currentDate().getTime());
    }

    /**
     * This method returns the current method name
     * @return
     */
    public static String currentMethodName() {
        return StackWalker.getInstance()
                .walk(s -> s.skip(1).findFirst())
                .get()
                .getMethodName();
    }

    /**
     * This method reads the file and returns the lines in the file
     *
     * @param filePath
     * @return
     */
    public static List<String> readFileLines(String filePath)
    {
        List<String> lines = new LinkedList<>();
        System.out.println("filePath >"+filePath+"<");
        int firstLine = 0 ;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
            	if(firstLine > 0)
            	{
            		lines.add(line);
            	}
            	firstLine++;
            }
        } catch (IOException e) {
        	e.printStackTrace();
            log.debug("Error reading file: {}", e.getMessage());
        }
        return lines;
    }

    public static List<String> readHeaderLines(String filePath)
    {
        List<String> lines = new LinkedList<>();
        System.out.println("filePath >"+filePath+"<");
        int firstLine = 0 ;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
            	if(firstLine ==0)
            	{
            		lines.add(line);
            	}
            	firstLine++;
            }
        } catch (IOException e) {
        	e.printStackTrace();
            log.debug("Error reading file: {}", e.getMessage());
        }
        return lines;
    }
    

    public static String findFullFileName(String filePath) 
    {
    	System.out.println("filePath >"+filePath+"<");
        String getFileNameeWithouExtension = filePath.substring(filePath.lastIndexOf("/") + 1);
        String fulleFilePath = filePath.substring(0, filePath.lastIndexOf("/"));
        
        //Find all the file names in the folder
        File folder = new File(fulleFilePath);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";
        assert listOfFiles != null;
        for (File file : listOfFiles) 
        {
            if (file.isFile()) 
            {
                if (file.getName().contains(getFileNameeWithouExtension)) 
                {
                    fileName = file.getAbsolutePath();
                }
            }
        }
        log.debug("File Name: {}", fileName);
        return fileName;
    }
//Dat File Path
    public static List<String> datFilePath(String filePath) {
        List<String> datFiles = new ArrayList<>();
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getAbsolutePath().endsWith(".dat")) {
                    fileName = file.getAbsolutePath();
                    datFiles.add(fileName);
                }
            }
        }
        log.debug("File Name: {}", fileName);
        return datFiles;
    }

//Control File Path
    
    public static List<String> ctlFilePath(String filePath) {
        List<String> ctlFiles = new ArrayList<>();
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getAbsolutePath().endsWith(".ctl")) {
                    fileName = file.getAbsolutePath();
                    ctlFiles.add(fileName);
                }
            }
        }
        log.debug("File Name: {}", fileName);
        return ctlFiles;
    }
}