package com.srini.quartz.scheduler.chapter3;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTriggersMain {
	public static void main(String[] args) {
		try {
			Scheduler schedule = StdSchedulerFactory.getDefaultScheduler();
			schedule.start();
			JobDetail job = JobBuilder.newJob(SimpleJobInstance.class).withIdentity("simpleTriggerJob", "Simple").build();
//------------------------------------------------------------------------------------------//
//			Build a trigger for a specific moment in time, with no repeats:
			Date startTime=new Date(1483852495769l);
			Trigger trigger=TriggerBuilder.newTrigger()
					.withIdentity("SimpleTrigger", "simple")
					.startAt(startTime)
					.forJob("simpleTriggerJob", "Simple")
					.build();
//			schedule.scheduleJob(job,trigger);

//-------------------------------------------------------------------------------------------//
//Build a trigger for a specific moment in time, then repeating every ten seconds ten times:
			Trigger trigger2=TriggerBuilder.newTrigger()
					.withIdentity("SimpleTrigger2", "simple")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
					.forJob(job)
					.build();
//			schedule.scheduleJob(job,trigger2);

//------------------------------------------------------------------------------------------//
//Build a trigger that will fire once, five minutes in the future:
			Trigger trigger3=TriggerBuilder.newTrigger()
					.withIdentity("SimpleTrigger3", "simple")
					.startAt(DateBuilder.futureDate(5, IntervalUnit.SECOND))
					.forJob(job)
					.build();
//			schedule.scheduleJob(job,trigger3);
//----------------------------------------------------------------------------------------//
//Build a trigger that will fire now, then repeat every five minutes, until the hour 22:00:
			Trigger trigger4=TriggerBuilder.newTrigger()
					.withIdentity("SimpleTrigger4", "simple")
					.startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever())
					.endAt(DateBuilder.dateOf(22, 0, 0))
					.build();
//			schedule.scheduleJob(job,trigger4);
			
//-------------------------------------------------------------------------------------//
//			Build a trigger that will fire at the top of the next hour, then repeat every 2 hours, forever:
			Trigger trigger5 = TriggerBuilder.newTrigger()
					.withIdentity("SimpleTigger5", "simple")
					.startAt(DateBuilder.evenHourDate(null))
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(2).repeatForever())
					.build();
//		    schedule.scheduleJob(job,trigger5);


			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
