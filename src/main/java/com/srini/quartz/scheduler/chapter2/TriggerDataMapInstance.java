package com.srini.quartz.scheduler.chapter2;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class TriggerDataMapInstance implements Job {
	String jobSays;
	int count;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobKey key = context.getJobDetail().getKey();
		org.quartz.JobDataMap dataMap = context.getMergedJobDataMap();
		System.out.println("Instance Key : "+key+" JobSays : " + jobSays + " JobCount : " + TriggerDataMap.executionCount);
		TriggerDataMap.executionCount++;
	}

	/**
	 * @param jobSays
	 */
	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
