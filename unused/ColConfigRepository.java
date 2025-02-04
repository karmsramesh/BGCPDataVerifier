package com.dbs.bgcp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.bgcp.data.TBgcpColConfig;

@Repository
public interface ColConfigRepository extends JpaRepository<TBgcpColConfig, Long> {
    List<TBgcpColConfig> findByAppCode(String appCode);
}
