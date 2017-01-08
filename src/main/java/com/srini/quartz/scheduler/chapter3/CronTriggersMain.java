package com.srini.quartz.scheduler.chapter3;

import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggersMain {
	
	public static void main(String[] args) {
		try {
			Scheduler schedule = StdSchedulerFactory.getDefaultScheduler();
			schedule.start();
			JobDetail cronJob = JobBuilder.newJob(SimpleJobInstance.class).withIdentity("cronTriggerJob", "cron").build();
//-------------------------------------------------------------------------------------------------------------------------//
	//Build a trigger that will fire every other minute, between 8am and 5pm, every day:
			Trigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger1", "cron")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?"))
					.forJob(cronJob)
					.build();
//			schedule.scheduleJob(cronJob,trigger1);
//------------------------------------------------------------------------------------------------------------------------//
//			Build a trigger that will fire daily at 2:35 pm:
			Trigger trigger2 = TriggerBuilder.newTrigger()
					    .withIdentity("cronTrigger2", "cron")
					    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(14, 36))
					    // or   .withSchedule(cronSchedule("0 36 14 * * ?"))
					    .forJob(cronJob)
					    .build();
//			schedule.scheduleJob(cronJob,trigger2);
//-------------------------------------------------------------------------------------------------------------------------//
//			Build a trigger that will fire on Wednesdays at 10:42 am, in a TimeZone other than the system’s default:
			Trigger trigger3 =TriggerBuilder.newTrigger()
					    .withIdentity("trigger3", "group1")
					    .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 10, 42)
					    		.inTimeZone(TimeZone.getTimeZone("America/Los_Angeles")))
					    //.withSchedule(cronSchedule("0 42 10 ? * WED"))
					    .forJob(cronJob)
					    .build();
			schedule.scheduleJob(cronJob,trigger3);
			
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
