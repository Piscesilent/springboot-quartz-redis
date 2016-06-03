package timer.service;

import java.io.Serializable;

/**
 * 任务类
 * @author SilenT
 *
 */
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务名
	 */
	private String job_name;
	
	/**
	 * cron表达式
	 */
	private String cron_expression;
	
	/**
	 * 回调地址
	 */
	private String app_address;


	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}


	public String getCron_expression() {
		return cron_expression;
	}

	public void setCron_expression(String cron_expression) {
		this.cron_expression = cron_expression;
	}

	public String getApp_address() {
		return app_address;
	}

	public void setApp_address(String app_address) {
		this.app_address = app_address;
	}

	public Task() {
	}

	public Task(String job_name,String cron_expression, String app_address) {
		this.job_name = job_name;
		this.cron_expression = cron_expression;
		this.app_address = app_address;
	}

}
