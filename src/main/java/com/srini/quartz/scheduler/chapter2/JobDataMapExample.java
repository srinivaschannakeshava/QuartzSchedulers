package com.srini.quartz.scheduler.chapter2;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobDataMapExample {
	static int executionCount =0;
	public static void main(String[] args) {
		try {
			Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			/*Note here that the job is created once and executed many time so that the jobsays hello remains at 0 */
			JobDetail job=JobBuilder.newJob(JobDataMapInstance.class)
					.withIdentity("job1","group1").usingJobData("jobSays","Hello - "+executionCount)
					.usingJobData("count",executionCount).build();
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();
			scheduler.scheduleJob(job,trigger);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
