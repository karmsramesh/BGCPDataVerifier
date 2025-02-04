package com.dbs.bgcp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.bgcp.data.TBgcpSystems;

@Repository
public interface SystemsRepository extends JpaRepository<TBgcpSystems, Long> {
    TBgcpSystems findByAppCode(String appCode);
}
