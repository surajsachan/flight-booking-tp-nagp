FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/flight-booking-0.0.1-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["--spring.profiles.active=gcp"]
ENTRYPOINT ["java", "-jar", "flight-booking-0.0.1-SNAPSHOT.jar"]