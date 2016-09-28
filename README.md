mvn clean install
cd target/
docker build -t spring-boot-timer
docker run -d -p 8011:8011 spring-boot-timer
