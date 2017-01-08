package com.srini.quartz.scheduler.chapter2;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author srini
 *
 */
public class JobDataMapInstance implements Job {

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	String jobSays;
	int count;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("JobSays : "+ jobSays +" JobCount : "+count);
		JobDataMapExample.executionCount++;
	}

	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	

	
}
