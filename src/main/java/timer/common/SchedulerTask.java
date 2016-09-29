package timer.common;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask implements ApplicationListener<ContextRefreshedEvent>{
	
	private Scheduler scheduler;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			System.exit(1);
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
	
}
