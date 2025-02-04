package com.dbs.bgcp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StagingBGCPService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> fetchRecordsByRunDate(String runDateValue, String tableName, String dateColumn) {
        String sql = "SELECT * FROM "+tableName+" WHERE "+dateColumn+" = ?";
        System.out.println("fetchRecordsByRunDate dateColumn "+dateColumn);
        System.out.println("fetchRecordsByRunDate runDateValue "+runDateValue);
        
        return jdbcTemplate.queryForList(sql, runDateValue);
    }

    public Map<String, Object> getStagingRecord(String whereCondtion) {
        String sql = "select * from staging_gcsp where " + whereCondtion;
        return jdbcTemplate.queryForMap(sql);
    }

}
