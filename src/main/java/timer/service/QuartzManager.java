package timer.service;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import timer.utils.CustomException;

/**
 * Quartz Manager
 * 
 * WARNING: When using redis to save, ONLY submit mission which job, trigger, group name UNIQUE.
 * 
 * @author SilenT
 */
@Service
public class QuartzManager {

	/**
	 * Find jobs
	 * @param scheduler
	 * @return
	 * @throws SchedulerException
	 */
	public List<String> findAllJobs(Scheduler scheduler) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		List<String> jobGroupNames = scheduler.getJobGroupNames();
		if (CollectionUtils.isEmpty(jobGroupNames)) {
			return new ArrayList<String>(0);
		}
		return jobGroupNames;
	}

	/**
	 * Add jobs
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	public void AddJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		if (hasTheTrigger(scheduler, task)) {
			throw new CustomException("Job exists");
		}
		addOneJob(scheduler, task);
	}

	/**
	 * del jobs
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	public void RemoveJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		if (!hasTheJob(scheduler, task)) {
			throw new CustomException("Job is not exists");
		}
		deleteOneJob(scheduler, task);
	}

	/**
	 * Is there the trigger?
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private boolean hasTheTrigger(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		boolean result = true;
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getJob_name(), task.getJob_name());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (null == trigger) {
			result = false;
		}
		return result;
	}

	/**
	 * Is there the job?
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private boolean hasTheJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		boolean result = true;
		JobKey jobKey = JobKey.jobKey(task.getJob_name(), task.getJob_name());
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (null == jobDetail) {
			result = false;
		}
		return result;
	}

	/**
	 * add job
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private void addOneJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).withIdentity(task.getJob_name(), task.getJob_name())
				.usingJobData("callback_address", task.getCallback_address())
				.usingJobData("callback_content", task.getCallback_content())
				.build();
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJob_name(), task.getJob_name())
				.startNow().withSchedule(CronScheduleBuilder.cronSchedule(task.getCron_expression())).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * del the job
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private void deleteOneJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("Scheduler is not init");
		}
		JobKey jobKey = JobKey.jobKey(task.getJob_name(), task.getJob_name());
		scheduler.deleteJob(jobKey);
	}

}