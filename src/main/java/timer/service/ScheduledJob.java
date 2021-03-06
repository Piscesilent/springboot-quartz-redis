package timer.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import timer.utils.GsonUtils;

/**
 * ScheduledJob
 * @author SilenT
 *
 */
public class ScheduledJob implements Job {
	
    private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);

    public ScheduledJob() {}

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        
        CompletableFuture.runAsync(()->{
        	try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        		if (CollectionUtils.isEmpty(dataMap) || StringUtils.isEmpty(dataMap.getString("callback_address"))) {
        			logger.error("callback url error, group={}, name={}",jobKey.getGroup(),jobKey.getName());
        		}
        		HttpPost httpPost = new HttpPost(dataMap.getString("callback_address"));
        		String callbackContent = dataMap.getString("callback_content");
        		if (!StringUtils.isEmpty(callbackContent)) {
        			HttpEntity entity = new StringEntity(callbackContent, ContentType.APPLICATION_JSON);
        			httpPost.setEntity(entity);
        		}
        		CloseableHttpResponse response = httpClient.execute(httpPost);
        		HttpEntity entity = response.getEntity();
        		String json = "";
        		if (entity != null) {
        			InputStream content = entity.getContent();
        			InputStreamReader isr = new InputStreamReader(content);
        			BufferedReader br = new BufferedReader(isr);
        			String str = null;
        			StringBuffer sb = new StringBuffer();
        			if ((str = br.readLine()) != null) {
        				sb.append(str);
        			}
        			json = GsonUtils.toJson(sb.toString());
        		}
        		logger.info("success, url={}, entity={}, group={}, name={}",
        				dataMap.getString("callback_address"),json,jobKey.getGroup(),jobKey.getName());
        	} catch (Exception e) {
        		logger.error("request failed, url={}, group={}, name={}",
        				dataMap.getString("callback_address"),jobKey.getGroup(),jobKey.getName(),e);
        	}
        });
    }
}