# Running 

## From the command line
to run the application, you can use this command line from the projects root directory.
The `tpximpact-task` module needs to be run first as this is an API that will listen for requests.
```shell
mvn --projects tpximpact-task --also-make spring-boot:run
```

Once the APi is running, you can run the UI module with the following command.
```shell
mvn --projects tpximpact-ui --also-make spring-boot:run
```

## Docker Image
If you have built the docker image, you can run it with the following command.
```shell
docker run -d -p 8080:8080 tpximpact-task-api:0.0.1-SNAPSHOT
```