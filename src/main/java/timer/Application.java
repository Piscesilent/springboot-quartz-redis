package timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application bootstrap
 * @author SilenT
 */
@ComponentScan(basePackages = { "timer" })
@EnableAutoConfiguration
public class Application {

	/**
	 * main method
	 * @author SilenT
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		/**
		 * running serive
		 */
		SpringApplication.run(Application.class, args);
	}
	
}
