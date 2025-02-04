package com.dbs.bgcp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dbs.bgcp.entity.JobStatus;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {

    @Query("SELECT j FROM JobStatus j WHERE j.SYSTEM_ID = :systemId")
    Optional<JobStatus> findBySystemId(@Param("systemId") String systemId);
}
