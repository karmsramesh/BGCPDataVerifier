package com.dbs.bgcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dbs.bgcp.repository.DynamicDataRepository;


@Service
public class DynamicDataService 
{
    private static final Logger log = LoggerFactory.getLogger(DynamicDataService.class);
    private final DynamicDataRepository dynamicDataRepository;
    private final AuditLogService auditLogService;

    public DynamicDataService(DynamicDataRepository dynamicDataRepository, AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
        this.dynamicDataRepository = dynamicDataRepository;
    }

 

}










