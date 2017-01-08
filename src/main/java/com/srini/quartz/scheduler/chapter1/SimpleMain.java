package com.srini.quartz.scheduler.chapter1;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author srini
 *
 */
public class SimpleMain {
	static int count = 0;

	public static void main(String[] args) {
		try {
			/* Create a scheduler using schedule factory */
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			scheduler.start();
			/* Build a job to be executed */
			JobDetail job = JobBuilder.newJob(SimpleJobInstance.class).withIdentity("job1", "group1").build();
			/* Build a trigger setting when to run the job */
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();
			/*Schdulet the job with trigger*/
			scheduler.scheduleJob(job, trigger);
			/*Shutdown the Scheduler*/
			// scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
