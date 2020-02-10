from openjdk:12-oracle
volume /tmp
copy build/libs/zuul-api-gateway-0.0.1-SNAPSHOT.jar zuul.jar
entrypoint ["java", "-jar", "eureka.jar"]