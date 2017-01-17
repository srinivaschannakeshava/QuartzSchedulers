package com.srini.quartz.scheduler.chapter5;

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
public class SchedulerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * Note : The Sql tables need to be imported first to your sql database 
		 * and the sql-connector jar you use should be checked you will get some exception on SQL syntax.
		 * You can find the SQL schema in the downloads ->documents-> dbtables 
		 * **/
		
		/*
		 * If you use MySql
		 * */
//		System.setProperty("org.quartz.properties", "quartzMySql.properties");
		
		//for mongoDB
		System.setProperty("org.quartz.properties", "quartzMongo.properties");
		
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
