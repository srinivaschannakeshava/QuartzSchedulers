package com.srini.quartz.scheduler.chapter4;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.matchers.OrMatcher;

import com.srini.quartz.scheduler.chapter3.SimpleJobInstance;

public class ListenersMain {
	
	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			CustomJobListener custJob=new CustomJobListener();
			JobDetail job = JobBuilder.newJob(SimpleJobInstance.class).withIdentity("simpleTriggerJob", "Simple")
					.build();
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("SimpleTrigger2", "simple").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
					.forJob(job).build();
			//Simple Job listener 
			scheduler.getListenerManager()
			.addJobListener(custJob,KeyMatcher.keyEquals(JobKey.jobKey("simpleTriggerJob","Simple")));
	// ----------------------------------------------------------------------------------------------------------		
			//Adding a JobListener that is interested in all jobs of a particular group:
			
			//scheduler.getListenerManager().addJobListener(custJob, GroupMatcher.jobGroupEquals("Simple"));
	//-----------------------------------------------------------------------------------------------------------------
			//Adding a JobListener that is interested in all jobs of two particular groups:
			//scheduler.getListenerManager().addJobListener(custJob, OrMatcher.or(GroupMatcher.jobGroupEquals("myJobGroup1"), GroupMatcher.jobGroupEquals("yourGroup2")));
	//-----------------------------------------------------------------------------------------------------------------
			//Adding a JobListener that is interested in all jobs:

	//		scheduler.getListenerManager().addJobListener(custJob, EverythingMatcher.allJobs());
//------------------------------------------------------------------------------------------------------------------------------
		//…Registering TriggerListeners works in just the same way.

	
			
			scheduler.getListenerManager().addTriggerListener(new CustomTriggerListener());
			scheduler.scheduleJob(job,trigger2);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
