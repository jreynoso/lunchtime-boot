# Lunchtime! Service

### About
This service handles the backend for the *Lunchtime!* app.

The goal is to demonstrate how to implement a simple Java Spring Boot service.

### Requirements
- JDK 8+

### Run Locally
- Configure `application.properties` with your Google API key
```
google.api.key: <api-key>
```

Then Run:

```
$ gradle bootRun
```

By default the service will run on port 8080

### API
```
GET /lunchtime?loc={latitude,longitude}&mode={walk|scoot|drive}
```

Return a list of dining options that match the request criteria.

### Roadmap
- deploy to google cloud
- separate configuration for integration tests
