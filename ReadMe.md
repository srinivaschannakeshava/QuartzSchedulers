Quartz Scheduler :

Scheduler is mainly used for scheduling a task triggers based on certain criteria such as time or logic or data

Quartz API :
  The key interface of Quartz API :
    -> Scheduler - Main api interacting with API
    -> Job - An interface to be implemented by components that you wish to have executed by the Scheduler
    -> JobDetail - used to define Instance of Jobs.
    -> Trigger - a component that defines the schedule upon which a given Job will be executed
    -> JobBuilder - Used to define/build job detail instance
    -> Trigger Builder -Used to define/build Trigger instance

Scheduler life cycle is bounded by its creation via SchedulerFactory and shutdown.
Once scheduler interface is created you can add , remove and list jobs and triggers and also perform other operations

              // define the job and tie it to our HelloJob class
                JobDetail job = newJob(HelloJob.class)
                    .withIdentity("myJob", "group1") // name "myJob", group "group1"
                    .build();

                // Trigger the job to run now, and then every 40 seconds
                Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())            
                    .build();

                // Tell quartz to schedule the job using our trigger
                sched.scheduleJob(job, trigger);

-> The various “ScheduleBuilder” classes have methods relating to defining different types of schedules.
-> The DateBuilder class contains various methods for easily constructing java.util.Date instances for particular points in time
(such as a date that represents the next even hour - or in other words 10:00:00 if it is currently 9:43:27)

Jobs and triggers
  -> Triggers objects are used to trigger the execution of jobs.
  -> You can tune the properties of the triggers to provide the scheduling you wish to have
  ->Most popular trigger types simple trigger and Cron trigger
  -> Simple trigger -> oneshot or execution @ given delay of T between executions.
  -> Cron triggerring is based on calendar-like schedules every friday @10:15 etc

  Here is flow of Scheduler
    -> We give scheduler a jobdetail instance and the type of job to be executed by simply providing the job's class as we build the job detail.
    ->Each time the scheduler executes the job it creates a new instance of class before calling its execute() method .
    -> Once execution is done , reference to the job class is dropped and teh instance is garbage collected.

JobDataMap -
  ->The JobDataMap can be used to hold any amount of (serializable) data objects which you wish to have made available to the job instance when it executes.
  Ex:  
  // define the job and tie it to our DumbJob class
  JobDetail job = newJob(DumbJob.class)
      .withIdentity("myJob", "group1") // name "myJob", group "group1"
      .usingJobData("jobSays", "Hello World!")
      .usingJobData("myFloatValue", 3.141f)
      .build();

      public void execute(JobExecutionContext context)
            throws JobExecutionException
          {
            JobKey key = context.getJobDetail().getKey();
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            String jobSays = dataMap.getString("jobSays");
            float myFloatValue = dataMap.getFloat("myFloatValue");
            System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
          }

      ->Triggers can also have JobDataMaps associated with them. This can be useful in the case where you have a Job that is stored in the scheduler for regular/repeated use by multiple Triggers, yet with each independent triggering, you want to supply the Job with different data inputs.

      Job State and Concurrency:
        two annotations
        @DisallowConcurrentExecution
        @PersistJobDataAfterExecution   

        Triggers :-
          Common Properties of triggers -
            -> "JobKey" property - indicates the job to be executed when the trigger fires.
            -> "startTime" property - when the trigger's schedule first come into affect.
            -> "endTime" property - indicates when the trigger’s schedule should no longer be in effect
            ->  Priority of triggers - When you have multiple triggers @ same time and you are restricted by the number of quartz worker treads, you can set the priority property on a trigger.
            Note :- Priorities are only compared when triggers have the same fire time  
            -> MisFire Instructions - A misfire occurs if a persistent trigger misses its firing time because of scheduler being shutdown , or because there are no available thread in the quartz thread pool executing the job .
                -> different type of trigger have different misfire instruction available on them.
                -> by default they go on with smart policy instruction . i.e when the scheduler starts up it searches for any persistent triggers which have misfired and it then updates them based on there individually configured misfire instruction
            -> Calendars - Quartz calendar object can be associated with triggers at the time of the trigger is defined and stored in scheduler .
              ->Calendars are useful for excluding blocks of time from the the trigger’s firing schedule
                ex- you could create a trigger that fires a job every weekday at 9:30 am, but then add a Calendar that excludes all of the business’s holidays

                Simple Triggers:
                  -> Simple trigger is used when you what to execute a job exactly once at specific time or at a specific movement in time followed by repeats at specific interval.
                  ->Check out the examples for more info on configurations on simple triggers

                Cron Triggers:
                  -> Firing schedule that recurs based on calendar-like notions , rather than exactly specified intervals of simple trigger
                  -> With Cron you can specify firing schedules such as "every friday at noon" or “every weekday and 9:30 am”, or even “every 5 minutes between 9:00 am and 10:00 am on every Monday, Wednesday and Friday during January”
                  Cron Expressions :
                    -> Cron Expressions are used to configure the cron-trigger.
                    -> Cron-Expressions are strings that are actually made up of seven sub-expressions ,that describe individual details of schedule

                    -> 7 sub-expressions - seconds,minutes,hours,daysofmonth,month,daysofweek,year(optional)
                    example - “0 0 12 ? * WED” -> 12noon every wednesday
                              “0 0 12 ? * MON-FRI” -> 12 pm every mon to friday
                              note :- * indicates every month @month field
                              “0 0 12 ? * * ” -> everyday 12pm
                              note:- -> Each values can also be set with values for ex - SUNDAY - 1 Monday -2 ...
                                      "0 0 12 ? * 1-6 " everyday 12 pm expect saturday
                    ->  / can be used to specify increments to values ex:- 0/15 in minutes filed means every 15th minute of an hour
                    -> ? is used in days of months and days of weeks . It is used to specify no specific value.
                        This is useful when you need to specify something in one of the two fields, but not the other
                    ->The ‘L’ character is allowed for the day-of-month and day-of-week fields. This character is short-hand for “last”
                        -> “L” in the day-of-month field means “the last day of the month”
                        -> If used in the day-of-week field by itself, it simply means “7” or “SAT”
                        -> if used in the day-of-week field after another value, it means “the last xxx day of the month” - for example “6L” or “FRIL” both mean “the last friday of the month”.
                        ->offset from the last day of the month, such as “L-3” which would mean the third-to-last day of the calendar month.
                    ->The ‘W’ is used to specify the weekday (Monday-Friday) nearest the given day
                          example -if you were to specify “15W” as the value for the day-of-month field, the meaning is: “the nearest weekday to the 15th of the month”
                    ->The ‘#’ is used to specify “the nth” XXX weekday of the month. For example, the value of “6#3” or “FRI#3” in the day-of-week field means “the third Friday of the month”.

                    CronTrigger Example 1 - an expression to create a trigger that simply fires every 5 minutes
                                            “0 0/5 * * * ?”
                    CronTrigger Example 2 - an expression to create a trigger that fires every 5 minutes, at 10 seconds after the minute (i.e. 10:00:10 am, 10:05:10 am, etc.).
                                            “10 0/5 * * * ?”
                    CronTrigger Example 3 - an expression to create a trigger that fires at 10:30, 11:30, 12:30, and 13:30, on every Wednesday and Friday.
                                            “0 30 10-13 ? * WED,FRI”
                    CronTrigger Example 4 - an expression to create a trigger that fires every half hour between the hours of 8 am and 10 am on the 5th and 20th of every month. Note that the trigger will NOT fire at 10:00 am, just at 8:00, 8:30, 9:00 and 9:30
                                            “0 0/30 8-9 5,20 * ?”

              Trigger listeners and job listeners:
                Listeners are the objects that you create to perform certain action on events in scheduler . Trigger listeners perform actions on trigger events and job listeners on job events.

                Trigger related events :
                    -> trigger firing , trigger mis-firing ,trigger completion
                Job related events :
                      -> job is about to be executed and job has completed execution

            Scheduler Listener :
                  SchedulerListeners are much like TriggerListeners and JobListeners, except they receive notification of events within the Scheduler itself - not necessarily events related to a specific trigger or job.
                  Scheduler-related events include: the addition of a job/trigger, the removal of a job/trigger, a serious error within the scheduler, notification of the scheduler being shutdown, and others.

          JOB stores:
            -> JobStore’s are responsible for keeping track of all the “work data” that you give to the scheduler: jobs, triggers, calendars, etc.
            -> JobStore your scheduler should use (and it’s configuration settings) in the properties file

            RAMJobStore:
              -> RAMJobStore is the simplest JobStore to use, it is also the most performant (in terms of CPU time).
              -> It keeps all the data in RAM.
              -> The drawback is that when your application ends (or crashes) all of the scheduling information is lost - this means RAMJobStore cannot honor the setting of “non-volatility” on jobs and triggers.
              -> Configuring Quartz to use RAMJobStore
                 org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore

          JDBCJobStore:
                  ->It keeps all of its data in a database via JDBC.
                  ->JDBCJobStore works with nearly any database  Oracle, PostgreSQL, MySQL, MS SQLServer, HSQLDB, and DB2
                  ->You can find table-creation SQL scripts in the “docs/dbTables” directory of the Quartz distribution.
                  ->Tables start with the prefix “QRTZ_” (such as the tables “QRTZ_TRIGGERS”, and “QRTZ_JOB_DETAIL”).However its configurable

                  Configuring JDBC stores
                  -> To use JDBCJobStore (and assuming you’re using StdSchedulerFactory) you first need to set the JobStore class property of your Quartz configuration to be either org.quartz.impl.jdbcjobstore.JobStoreTX or org.quartz.impl.jdbcjobstore.JobStoreCMT
                  ->Configuring Quartz to use JobStoreTx
                                    org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
                  ->Configuring JDBCJobStore to use a DriverDelegate(responsible for all JDBC operation) there are different delegates for different databases check out
                                    org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
                  ->Configuring JDBCJobStore with the Table Prefix
                                    org.quartz.jobStore.tablePrefix = QRTZ_
                  ->Configuring JDBCJobStore with the name of the DataSource to use
                                    org.quartz.jobStore.dataSource = myDS
                  Note:- If your Scheduler is busy (i.e. nearly always executing the same number of jobs as the size of the thread pool, then you should probably set the number of connections in the DataSource to be the about the size of the thread pool + 2.

      Configuration, Resource Usage and SchedulerFactory:
        ->The architecture of Quartz is modular, and therefore to get it running several components need to be “snapped” together.
        ->The major components that need to be configured before Quartz can do its work are:
                            ThreadPool
                            JobStore
                            DataSources (if necessary)
                            The Scheduler itself

       ->The ThreadPool provides a set of Threads for Quartz to use when executing Jobs.The more threads in the pool, the greater number of Jobs that can run concurrently

    Clustering:
        Clustering currently works with the JDBC-Jobstore (JobStoreTX or JobStoreCMT) and the TerracottaJobStore. Features include load-balancing and job fail-over (if the JobDetail’s “request recovery” flag is set to true).
       Each instance in the cluster should use the same copy of the quartz.properties file. Exceptions of this would be to use properties files that are identical, with the following allowable exceptions: Different thread pool size, and different value for the “org.quartz.scheduler.instanceId” property. Each node in the cluster MUST have a unique instanceId, which is easily done (without needing different properties files) by placing “AUTO” as the value of this property.
