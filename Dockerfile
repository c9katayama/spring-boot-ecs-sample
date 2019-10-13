FROM amazoncorretto:8u222
RUN yum install -y awscli
ADD build/libs/spring-boot-ecs-sample-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-ecs-sample-0.0.1-SNAPSHOT.jar"]

