# Building

## Command Line

to build this application from the command line you can use the following command from the root directory of the
project.
This command will compile both sub-modules, `tpximpact-task` and `tpximpact-ui`, and ensure that all dependencies are
resolved.
```shell
mvn clean compile
```

the following commands show how to build the individual sub-modules, `tpximpact-task` and `tpximpact-ui`, if you want to
build them separately.
```shell
mvn --projects tpximpact-task --also-make clean compile
mvn --projects tpximpact-ui --also-make clean compile
```

this command first cleans out any old files created by a previous build, compiles the code.

### Running Tests

to run the tests from the command line you can use the following command
```shell
mvn --projects tpximpact-task --also-make clean test
```

it is good practise to always clean first to ensure that all changes are picked up.
If clean is not run first, then some changes to generated files may be missed.

### Docker Image

to build the docker image for the `tpximpact-task` module, you can use the following command
```shell
cd ./tpximpact-task
docker build -t tpximpact-task-api:0.0.1-SNAPSHOT .
```