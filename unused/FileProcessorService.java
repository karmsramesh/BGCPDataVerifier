package com.dbs.bgcp.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.dbs.bgcp.data.TBgcpColConfig;
import com.dbs.bgcp.data.TBgcpSystems;
import com.dbs.bgcp.repository.ColConfigRepository;
import com.dbs.bgcp.repository.SystemsRepository;

@Service
public class FileProcessorService 
{
    private final ColConfigRepository colConfigRepository;
    private final SystemsRepository systemsRepository;

    public FileProcessorService(ColConfigRepository colConfigRepository, SystemsRepository systemsRepository) {
        this.colConfigRepository = colConfigRepository;
        this.systemsRepository = systemsRepository;
    }

    public List<TBgcpColConfig> getColConfigByAppCode(String appCode) {
        return colConfigRepository.findByAppCode(appCode);
    }

    public TBgcpSystems getSystemByAppCode(String appCode) {
        return systemsRepository.findByAppCode(appCode);
    }
}
