## SpaceCraft Application Challenge

- Java + Spring Boot Application

### Application Structure
- A controller as entry point and service layer with some utilities
- Generic exception handling for some I/O methods

### Extras
- App runs on port `9000`
- Swagger documentation available at: 
    > http://localhost:9000/swagger-ui/index.html
- Postman collection available in the repository
- HealthCheck endpoint available at:
    > http://localhost:9000/actuator/health
- Postman collection available in the repository

### How to run
- Download locally, import maven projects & just run and have fun
<br>

From the command line:
> mvn spring-boot:run

The application also contains a dockerfile. To build the image:
> docker build -t spacecraft-app .

After building the image, run it with:
> docker run -p 9000:9000 spacecraft-app

### How to test
- All tests are in the test folder with 100% coverage

### How to build
> mvn clean install

### Challenge Description
Create a Spring Boot application in Java that uses 3 provided .json files as input, to combine 
spacecraft onboard events with latitude and longitude positions to display the accurate geographic positions of each
event.
<br>
The .json files are:
- `events.json` - contains a list of events that occurred onboard a spacecraft
- `latitudes.json` - contains a list of latitudes that correspond to the events in the latitudes.json file
- `longitudes.json` - contains a list of longitudes that correspond to the events in the longitudes.json file
<br>

Requirements:
- Use the Spring Boot framework
- Json files should not be modified. They contain time-stamped latitude and longitude positions that change over time,
and spacecraft onboard events with `occurence_time`, `event_name` and `severity`
- The application should provide a REST interface offering its users with two features:
  - Deliver the latitude & longitude for a given `event_id`
  - Deliver the whole list of events and associated positions
- The `latitude` and `longitude` positions for events should be determined by matching the event's `occurrence_time`
with the closest available positions in the _latitudes.json_ and _longitudes.json_ files. Indicate the potential error
for each position.