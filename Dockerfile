# java version 17
FROM amazoncorretto:17
#set working directory
WORKDIR /app
#copy from source to destination
COPY target/spacecraft-0.0.1-SNAPSHOT.jar spacecraft.jar
#expose
EXPOSE 9000
ENTRYPOINT ["java","-jar","spacecraft.jar"]
