package timer.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import timer.Application;

/**
 * Properties Utils
 * @author SilenT
 *
 */
public class PropUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(PropUtils.class);
	
	/**
	 * Load props
	 * @author SilenT
	 * @param fileNames
	 */
	public static void initProperties(String... fileNames) {
		if (null == fileNames || 0 == fileNames.length) {
			return;
		}
		for (String fileName : fileNames) {
			
			String file = null;

			file = Application.class.getClassLoader().getResource(fileName).getFile();
			if (new File(file).exists()) {
				PropertyConfigurator.configure(file);
				LOG.info("Running in IDE, props:{}", file);
				continue;
			}

			file = System.getProperty("user.dir") + File.separator + fileName;
			if (new File(file).exists()) {
				PropertyConfigurator.configure(file);
				LOG.info("Running in product, props:{}", file);
				continue;
			}
			
			InputStream in = Application.class.getClassLoader().getResourceAsStream(fileName);
			Properties p = new Properties();
			try {
				p.load(in);
				PropertyConfigurator.configure(p);
				LOG.info("Fail loading props, loading inner props");
			} catch (IOException e) {
				System.exit(1);
			}
		}
	}
}
