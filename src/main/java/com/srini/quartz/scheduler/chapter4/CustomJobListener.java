package com.srini.quartz.scheduler.chapter4;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class CustomJobListener implements JobListener {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CustomJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("-------------------------------------------------------");

		System.out.println("Job to be execute : "+context.getJobDetail().getKey());
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("Job jobExecutionVetoed : "+context.getJobDetail().getKey());


	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO Auto-generated method stub
		System.out.println("Job executed : "+context.getJobDetail().getKey());
		System.out.println("-------------------------------------------------------");

	}

}
