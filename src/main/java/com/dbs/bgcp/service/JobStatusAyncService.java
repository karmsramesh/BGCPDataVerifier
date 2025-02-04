package com.dbs.bgcp.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dbs.bgcp.entity.JobStatus;
import com.dbs.bgcp.repository.JobStatusRepository;
import com.dbs.bgcp.util.ApplicationUtil;
import com.dbs.bgcp.util.JobStatusMap;
import com.dbs.bgcp.util.Status;
import com.dbs.bgcp.util.StatusMessageType;

/**
 * This class is used to store the detail data into {@link JobStatus}
 */
@Service
public class JobStatusAyncService {

    private final JobStatusRepository jobStatusRepository;

    public JobStatusAyncService(JobStatusRepository jobStatusRepository) {
        this.jobStatusRepository = jobStatusRepository;
    }

    private void insertOrUpdateJobStatus(JobStatus inputJobsStatus) {
        jobStatusRepository.save(inputJobsStatus);
    }

    @Async
    public void updateJobStatusMapAndInsertIntoDB(String appCode, Status status, JobStatus jobStatus) {
        JobStatusMap.updateValue(appCode, ApplicationUtil.currentMethodName(), new Status(StatusMessageType.IN_PROGRESS.getValue(), 10.0));
        insertOrUpdateJobStatus(jobStatus);
    }

    public boolean checkJobCompletedStatus(String appCode) {
        Status status = JobStatusMap.getPercentage(appCode);
        return status.getMessageType().equals(StatusMessageType.COMPLETED.getValue());
    }
}
