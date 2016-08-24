FROM microservicesteam/nutaxi-java:v0.1
VOLUME /tmp
ENV JAVA_OPTS -Dspring.profiles.active=docker-aws
ADD @artifact@ app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]