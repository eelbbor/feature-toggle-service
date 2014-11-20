# Feature Toggle Service

Java based REST service enabling Feature Toggle use from an external service.  The initial build is focused on being
able to deploy to Heroku.

## Build - builds war etc in target directory

```$ mvn clean package```

## Run Locally - starts an embedded Jetty server at localhost:8080

```$ mvn clean package jetty:run```

###In order to debug it is necessary to export maven options

```$ export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"```

## Run Tests

```$ mvn test```
