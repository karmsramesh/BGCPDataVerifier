package com.dbs.bgcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.bgcp.entity.AuditLog;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
