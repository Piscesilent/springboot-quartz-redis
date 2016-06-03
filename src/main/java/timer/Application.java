package timer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application启动类
 * 
 * @author SilenT
 *
 */
@ComponentScan(basePackages = { "timer" })
@EnableAutoConfiguration
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	/**
	 * spring boot 配置文件
	 */
	private static final String SPRING_BOOT_PROP = "application.properties";

	/**
	 * quartz 配置文件
	 */
	private static final String QUARTZ_PROP = "quartz.properties";

	/**
	 * log4j 配置文件
	 */
	private static final String LOG4J_PROP = "log4j.properties";

	/**
	 * main函数
	 * 
	 * @author SilenT
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		/**
		 * 初始化配置文件
		 */
		initProperties(SPRING_BOOT_PROP, QUARTZ_PROP, LOG4J_PROP);
		
		/**
		 * 运行spring boot
		 */
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 读取配置文件
	 * 
	 * @author SilenT
	 * @param fileName
	 */
	private static void initProperties(String... fileNames) {
		if (null == fileNames || 0 == fileNames.length) {
			return;
		}
		for (String fileName : fileNames) {
			
			String file = null;

			file = Application.class.getClassLoader().getResource(fileName).getFile();
			if (new File(file).exists()) {
				PropertyConfigurator.configure(file);
				logger.info("在IDE中运行，读取配置文件:{}", file);
				continue;
			}

			file = System.getProperty("user.dir") + File.separator + fileName;
			if (new File(file).exists()) {
				PropertyConfigurator.configure(file);
				logger.info("打包运行，读取配置文件:{}", file);
				continue;
			}
			
			InputStream in = Application.class.getClassLoader().getResourceAsStream(fileName);
			Properties p = new Properties();
			try {
				p.load(in);
				PropertyConfigurator.configure(p);
				logger.info("读取外部配置文件失败，读取jar内置文件");
			} catch (IOException e) {
				System.exit(1);
			}
		}
	}
}
