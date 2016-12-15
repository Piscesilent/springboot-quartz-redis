FROM java

ADD spring-boot-timer.jar /opt/spring-boot-timer/
ADD application.properties /opt/spring-boot-timer/
ADD quartz.properties /opt/spring-boot-timer/
ADD log4j2.properties /opt/spring-boot-timer/

EXPOSE 8011

WORKDIR /opt/spring-boot-timer/

CMD ["java", "-jar", "spring-boot-timer.jar"]