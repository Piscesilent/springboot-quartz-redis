package timer.service;

import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import timer.utils.NewResp;

/**
 * 接口controller
 * @author SilenT
 */
@RestController
@RequestMapping("/quartz")
public class TimerController {
	
	private Logger logger = LoggerFactory.getLogger(TimerController.class);
	
	/**
	 * 调度器
	 */
	private static Scheduler scheduler = null;
	
	/**
	 * 初始化调度器
	 */
	TimerController() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			System.exit(1);//如果调度器启动失败，直接退出
		}
	}
	
	@Autowired
	private QuartzManager quartzManager;
	
	/**
	 * 添加任务
	 * @author SilenT
	 * @param task
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public NewResp add(@RequestBody Task task) {
		try {
			if (StringUtils.isEmpty(task.getJob_name())
					||StringUtils.isEmpty(task.getCron_expression())
					||StringUtils.isEmpty(task.getApp_address())) {
				return NewResp.fail("参数缺失");
			}
			quartzManager.AddJob(scheduler, task);
			return NewResp.OK();
		} catch (Exception e) {
			logger.error("添加失败",e);
			return NewResp.fail("添加失败,"+e.getMessage());
		}
	}
	
	/**
	 * 删除任务
	 * @author SilenT
	 * @param task
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.DELETE)
	@ResponseBody
	public NewResp del(@RequestParam(value="job_name") String name) {
		try {
			if (StringUtils.isEmpty(name)) {
				return NewResp.fail("参数缺失");
			}
			Task task = new Task(name,null,null);
			quartzManager.RemoveJob(scheduler, task);
			return NewResp.OK();
		} catch (Exception e) {
			logger.error("删除失败",e);
			return NewResp.fail("删除失败,"+e.getMessage());
		}
	}
	
	/**
	 * 查询任务
	 * @author SilenT
	 * @param task
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET)
	@ResponseBody
	public NewResp list() {
		try {
			List<String> findAllJobs = quartzManager.findAllJobs(scheduler);
			return NewResp.OK(findAllJobs);
		} catch (Exception e) {
			logger.error("查询失败",e);
			return NewResp.fail("查询失败,"+e.getMessage());
		}
	}
}
