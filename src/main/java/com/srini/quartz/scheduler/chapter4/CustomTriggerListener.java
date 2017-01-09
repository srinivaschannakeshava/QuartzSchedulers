package com.srini.quartz.scheduler.chapter4;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

public class CustomTriggerListener implements TriggerListener {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CustomTriggerListener";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("Trigger Fired........TriggerKey : " + trigger.getKey() + " JobKey : "
				+ context.getJobDetail().getKey());
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// TODO Auto-generated method stub
		System.out.println("Trigger MisFired........TriggerKey : " + trigger.getKey());
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		// TODO Auto-generated method stub
		System.out.println("Trigger Complete........TriggerKey : " + trigger.getKey() + " JobKey : "
				+ context.getJobDetail().getKey()+" triggerInstructionCode : "+triggerInstructionCode);
	}

}
