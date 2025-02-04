package com.dbs.bgcp.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.dbs.bgcp.config.FileConfig;
import com.dbs.bgcp.config.QueryConfig;
import com.dbs.bgcp.data.TBgcpColConfig;
import com.dbs.bgcp.data.TBgcpSystems;
import com.dbs.bgcp.service.AuditLogService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class SystemConfigRepository {


    private final EntityManager entityManager;

   // private final QueryConfig queryConfig;
    //private final FileConfig fileConfig;
    private final AuditLogService auditLogService;
    private static final Logger log = LoggerFactory.getLogger(SystemConfigRepository.class);
    public SystemConfigRepository(EntityManager entityManager, QueryConfig queryConfig, FileConfig fileConfig,AuditLogService auditLogService) {
        this.entityManager = entityManager;
        //this.queryConfig = queryConfig;
        //this.fileConfig = fileConfig;
       this.auditLogService = auditLogService;
    }

    public TBgcpSystems fetchBaseSystem(String applCode)
    {
    	System.out.println("fetchBaseSystem applCode >"+applCode+"<");
    	TBgcpSystems basesystem = new TBgcpSystems();
        StringBuffer sqlbuffer =new StringBuffer(" SELECT *  FROM T_BGCP_SYSTEMS1 WHERE SYSTEM_CODE=:applCode ");
        try 
        {
            Query query = entityManager.createNativeQuery(sqlbuffer.toString(), Map.class);
            query.setParameter("applCode", applCode);
            
            Map<String, Object> result = (Map<String, Object>) query.getSingleResult();
            result.forEach((key,val)->
            {
            	if(key.equals("file_folder") && (val+"").length()>0)
            	{
            		basesystem.setFileFolder(val+"");
            	}
            	if(key.equals("detail_file") && (val+"").length()>0)
            	{
            		basesystem.setDetailFile(val+"");
            	}
            	
            	if(key.equals("detail_table") && (val+"").length()>0)
            	{
            		basesystem.setDETAIL_TABLE(val+"");
            	}
            	
            	//[DETAIL_TABLE]
            	
            	
            });
            System.out.println("fetchBaseSystem basesystem >"+basesystem+"<");
        } 
        catch (Exception e) 
        {
            log.error("Error while fetching system configuration for application code: {}", applCode, e);
            auditLogService.log("fetchSystemConfig", "SystemConfigResponse", " APPLN_CODE: " + applCode, "processing","Error while fetching system configuration for application code" +e.getMessage());
        }
        return basesystem;
    }

    private String buildFilePath(String folderPath, String folder,String fileName) {
        if (folderPath != null && fileName != null&& folder!=null) {
            return folderPath + File.separator + folder+ File.separator+fileName;
        }
        return null; // Return null if folder or file name is missing
    }


    public ArrayList<TBgcpColConfig> fetchColConfigConfig(String systemId) {
        ArrayList<TBgcpColConfig> alList = new ArrayList<TBgcpColConfig>();

        String sql = "select *  from T_BGCP_COL_CONFIG where APP_CODE=:appcode order by START_POSITION";

        System.out.println("fetchColConfigConfig sql "+sql +" systemId:"+systemId);
        try 
        {
            Query query = entityManager.createNativeQuery(sql, Map.class);
            query.setParameter("appcode", systemId);
            
            List<Map<String, Object>> queryResult = query.getResultList();
            for (Map<String, Object> result : queryResult) 
            {
            	TBgcpColConfig bgcpcolconfig = new TBgcpColConfig();
            	//Source_Table	Direct_Or_Derived	Transformation_Logic	Source_Attribute	Start_Position	End_Position	Unique_Key_Column	Data_Validation	Is_Active	is_Sum_Amt_Col
            	
            	result.forEach((k,r)-> System.out.println(k+" "+r));
            	
            	bgcpcolconfig.setSource_Attribute((String) result.get("source_attribute"));
            	bgcpcolconfig.setStart_Position( (String) result.get("start_position"));
            	System.out.println("bgcpcolconfig.getStart_Position() >"+bgcpcolconfig.getStart_Position()+"<");
            	
            	bgcpcolconfig.setEnd_Position((String) result.get("end_position"));
            	System.out.println("bgcpcolconfig.getEnd_Position() >"+bgcpcolconfig.getEnd_Position()+"<");
            	bgcpcolconfig.setTarget_Attribute((String) result.get("target_attribute"));
            	
            	//bgcpcolconfig.setUnique_Key_Column((Character) result.get("unique_key_column")+"");
                alList.add(bgcpcolconfig);
            }
        } 
        catch (Exception e) 
        {
        	System.err.println("fetchColConfigConfig sql "+sql +" systemId:"+systemId);
        	//e.printStackTrace();
            auditLogService.log("fetchColConfigConfig", "", " APPLN_CODE: " + systemId, "processing","Error while fetching configuration for systemId"+systemId+" " +e.getMessage());
            log.error("Error while fetching configuration for systemId: {} ", systemId,  e);

        }
        return alList;
    }

}
