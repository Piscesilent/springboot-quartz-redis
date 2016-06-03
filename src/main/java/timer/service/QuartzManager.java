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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import timer.utils.CustomException;

/**
 * Quartz增删改查
 * 
 * 注意：当使用redis作为持久化方案时，仅支持提交job、trigger、group都唯一的任务
 * 
 * @author SilenT
 *
 */
@Service
public class QuartzManager {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

	/**
	 * 查询任务
	 * 
	 * @param scheduler
	 * @return
	 * @throws SchedulerException
	 */
	public List<String> findAllJobs(Scheduler scheduler) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
		}
		List<String> jobGroupNames = scheduler.getJobGroupNames();
		if (CollectionUtils.isEmpty(jobGroupNames)) {
			return new ArrayList<String>();
		}
		return jobGroupNames;
	}

	/**
	 * 添加任务
	 * 
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	public void AddJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
		}
		if (hasTheTrigger(scheduler, task)) {
			throw new CustomException("已有该任务");
		}
		addOneJob(scheduler, task);
	}

	/**
	 * 删除任务
	 * 
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	public void RemoveJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
		}
		if (!hasTheJob(scheduler, task)) {
			throw new CustomException("没有该任务");
		}
		deleteOneJob(scheduler, task);
	}

	/**
	 * 判断调度器中是否有该触发器
	 * 
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private boolean hasTheTrigger(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
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
	 * 判断调度器中是否有该任务
	 * 
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private boolean hasTheJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
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
	 * 添加任务
	 * 
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private void addOneJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
		}
		JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).withIdentity(task.getJob_name(), task.getJob_name())
				.usingJobData("app_address", task.getApp_address()).build();
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJob_name(), task.getJob_name())
				.startNow().withSchedule(CronScheduleBuilder.cronSchedule(task.getCron_expression())).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 删除任务
	 * 
	 * @author SilenT
	 * @param scheduler
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	private void deleteOneJob(Scheduler scheduler, Task task) throws SchedulerException {
		if (!scheduler.isStarted()) {
			throw new CustomException("调度器未启动");
		}
		JobKey jobKey = JobKey.jobKey(task.getJob_name(), task.getJob_name());
		scheduler.deleteJob(jobKey);
	}

}