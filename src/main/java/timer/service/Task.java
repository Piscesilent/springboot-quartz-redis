package timer.service;

import java.io.Serializable;

/**
 * Task
 * @author SilenT
 *
 */
public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * job name
	 */
	private String job_name;
	
	/**
	 * cron
	 */
	private String cron_expression;
	
	/**
	 * callback url
	 */
	private String callback_address;
	
	/**
	 * callback content
	 */
	private String callback_content;


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

	public String getCallback_address() {
		return callback_address;
	}

	public void setCallback_address(String callback_address) {
		this.callback_address = callback_address;
	}
	
	public String getCallback_content() {
		return callback_content;
	}

	public void setCallback_content(String callback_content) {
		this.callback_content = callback_content;
	}

	public Task() {
	}

	public Task(String job_name,String cron_expression, String callback_address) {
		this.job_name = job_name;
		this.cron_expression = cron_expression;
		this.callback_address = callback_address;
	}

}
