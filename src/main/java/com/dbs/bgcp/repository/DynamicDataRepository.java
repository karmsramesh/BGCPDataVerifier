package com.dbs.bgcp.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.dbs.bgcp.config.FileConfig;
import com.dbs.bgcp.service.AuditLogService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Repository
public class DynamicDataRepository {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataRepository.class);
    private final EntityManager entityManager;
    private final FileConfig fileConfig;
    private final AuditLogService auditLogService;


    public DynamicDataRepository(EntityManager entityManager, FileConfig fileConfig, AuditLogService auditLogService) {
        this.entityManager = entityManager;
        this.fileConfig = fileConfig;
        this.auditLogService = auditLogService;

    }

    /**
     * This method is used to insert the data into the database.
     * If the transaction is successful, the data will be stored in the database. Otherwise, the transaction will be rolled back.
     *
     * @param insertQueries
     */
    @Transactional
    public void insertHeaderQueries(List<String> insertQueries) 
    {
        int batchSize = fileConfig.getBatchSize();
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        int count = 0;
        log.debug("Inserting Header Queries: {}", insertQueries.size());
        System.out.println("Inserting Header Queries: " + insertQueries.size());
        for (String query : insertQueries) 
        {
            count++;
            try 
            {
            	System.out.println(" Count " + count + " Query " +query );
                entityManager.createNativeQuery(query).executeUpdate();
            } 
            catch (Exception e) 
            {
                log.debug("Failed Query : " + query + " " + e.getMessage());
//                auditLogService.log("insertQueries", "insertQueries", " APPLN_CODE: " + appCode, "tally the credit and debit amount", " The system " +e.getMessage());
            }
            if (count > 0 && count % batchSize == 0) 
            {
            	entityManager.flush();
            	entityManager.clear();
            	System.out.println("FLUSH AND CLEAR ENTITY MANAGER RECORD COUNT " +count +"");
                log.debug("Flushing and clearing the entity manager after processing {} queries", batchSize);
            }
        }
        log.debug("Flushing and clearing the entity manager after processing all queries");
    }
    
    
    @Transactional
    public void insertDetailQueries(List<String> insertQueries) 
    {
        int batchSize = fileConfig.getBatchSize();
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        int count = 0;
        log.debug("Inserting Detail Queries: {}", insertQueries.size());
        System.out.println("Inserting Detail Queries: " + insertQueries.size());
        for (String query : insertQueries) 
        {
            count++;
            try 
            {
            	//System.out.println(" Count " + count + " Query " +query );
                entityManager.createNativeQuery(query).executeUpdate();
            } 
            catch (Exception e) 
            {
            	System.out.println("insertDetailQueries ERROR "+count +" Record ");
            	e.printStackTrace();
            	System.out.println("Failed Query : " + query + " " + e.getMessage());
                log.debug("Failed Query : " + query + " " + e.getMessage());
//                auditLogService.log("insertQueries", "insertQueries", " APPLN_CODE: " + appCode, "tally the credit and debit amount", " The system " +e.getMessage());
            }
            if (count > 0 && count % batchSize == 0) 
            {
            	entityManager.flush();
            	entityManager.clear();
            	System.out.println("FLUSH AND CLEAR ENTITY MANAGER RECORD COUNT " +count +"");
                log.debug("Flushing and clearing the entity manager after processing {} queries", batchSize);
            }
        }
        log.debug("Flushing and clearing the entity manager after processing all queries");
    }
    
    
    /**
     * This method is used to insert the data into the database.
     * If the transaction is successful, the data will be stored in the database. Otherwise, the transaction will be rolled back.
     *
     * @param insertQueries
     */
    @Transactional
    public void insertTrailerQueries(List<String> insertQueries) 
    {
        int batchSize = fileConfig.getBatchSize();
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        int count = 0;
        log.debug("Inserting Trailer Queries: {}", insertQueries.size());
        System.out.println("Inserting Trailer Queries: " + insertQueries.size());
        for (String query : insertQueries) 
        {
            count++;
            try 
            {
                entityManager.createNativeQuery(query).executeUpdate();
            } 
            catch (Exception e) 
            {
                log.debug("Failed Query : " + query + " " + e.getMessage());
//                auditLogService.log("insertQueries", "insertQueries", " APPLN_CODE: " + appCode, "tally the credit and debit amount", " The system " +e.getMessage());
            }
            if (count > 0 && count % batchSize == 0) 
            {
                log.debug("Flushing and clearing the entity manager after processing {} queries", batchSize);
            }
        }
        log.debug("Flushing and clearing the entity manager after processing all queries");
    }

    @Transactional
    public void insertSummaryReport(List<String> allQueries) {
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        log.debug("Inserting Summary Report Queries: {}", allQueries.size());
        System.out.println("Inserting Summary Report Queries: " + allQueries.size());
        for (String query : allQueries) {
            try {
                System.out.println("query: " + query);
                entityManager.createNativeQuery(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("EXCEPTION QUERY: " + query);
                log.debug("Failed Query : " + query + " " + e.getMessage());
                e.printStackTrace();
            }
        }
        log.debug("Flushing and clearing the entity manager after processing all queries");
    }

    

}
