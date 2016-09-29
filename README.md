# Springboot-Quartz-Redis #

Base on Springboot with Quartz and Redis.

## Use ##

**1.Search all jobs:**

curl -XGET http://127.0.0.1:8011/quartz


**2.Delete one job:**

curl -XDELETE http://127.0.0.1:8011/quartz?job_name=:jobName


**3.Add job without callback content (method in callback is POST):**

curl -XPOST "http://127.0.0.1:8011/quartz" -H 'Content-Type:application/json' -d '{"job_name":"one job","cron_expression":"0 0 12 * * ?","callback_address":"http://url.com/","callback_content":""}'


**4.Add job with callback content (method in callback is POST):**

'callback_content' could write JSON which would add in request body when callback in target date time.

## Run ##

**Begin:**

mvn clean install


**If put it in Docker, then:**

cd target/

docker build -t spring-boot-timer .

docker run -d -p 8080:8011 spring-boot-timer

