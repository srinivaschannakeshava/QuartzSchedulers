package com.srini.quartz.scheduler.chapter5;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJobInstance implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Executing Job ..... Executed : "+new Date());
	}

}
