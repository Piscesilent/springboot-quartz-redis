package timer.controller;

import java.util.List;

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

import timer.common.SchedulerTask;
import timer.service.QuartzManager;
import timer.service.Task;
import timer.utils.NewResp;

/**
 * Controller
 * 
 * @author SilenT
 */
@RestController
@RequestMapping("/quartz")
public class TimerController {

	private final Logger logger = LoggerFactory.getLogger(TimerController.class);

	@Autowired
	private QuartzManager quartzManager;
	
	@Autowired
	private SchedulerTask SchedulerTask;

	/**
	 * Add job
	 * 
	 * @author SilenT
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public NewResp add(@RequestBody Task task) {
		try {
			if (StringUtils.isEmpty(task.getJob_name()) || StringUtils.isEmpty(task.getCron_expression())
					|| StringUtils.isEmpty(task.getCallback_address())) {
				return NewResp.fail("param lack");
			}
			quartzManager.AddJob(SchedulerTask.getScheduler(), task);
			return NewResp.OK();
		} catch (Exception e) {
			logger.error("add failed", e);
			return NewResp.fail("add failed," + e.getMessage());
		}
	}

	/**
	 * Del job
	 * 
	 * @author SilenT
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	@ResponseBody
	public NewResp del(@RequestParam(value = "job_name") String name) {
		try {
			if (StringUtils.isEmpty(name)) {
				return NewResp.fail("param lack");
			}
			Task task = new Task(name, null, null);
			quartzManager.RemoveJob(SchedulerTask.getScheduler(), task);
			return NewResp.OK();
		} catch (Exception e) {
			logger.error("del failed", e);
			return NewResp.fail("del failed," + e.getMessage());
		}
	}

	/**
	 * List the jobs
	 * 
	 * @author SilenT
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public NewResp list() {
		try {
			List<String> findAllJobs = quartzManager.findAllJobs(SchedulerTask.getScheduler());
			return NewResp.OK(findAllJobs);
		} catch (Exception e) {
			logger.error("list failed", e);
			return NewResp.fail("list failed," + e.getMessage());
		}
	}
}
