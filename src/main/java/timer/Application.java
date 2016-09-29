package timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import timer.utils.PropUtils;

/**
 * Application bootstrap
 * @author SilenT
 */
@ComponentScan(basePackages = { "timer" })
@EnableAutoConfiguration
public class Application {

	/**
	 * spring boot properties
	 */
	private static final String SPRING_BOOT_PROP = "application.properties";

	/**
	 * quartz properties
	 */
	private static final String QUARTZ_PROP = "quartz.properties";

	/**
	 * log4j properties
	 */
	private static final String LOG4J_PROP = "log4j.properties";

	/**
	 * main method
	 * @author SilenT
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		/**
		 * init props
		 */
		PropUtils.initProperties(SPRING_BOOT_PROP, QUARTZ_PROP, LOG4J_PROP);
		
		/**
		 * running serive
		 */
		SpringApplication.run(Application.class, args);
	}
	
}
