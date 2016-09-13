# nutaxi-route-service

[![Build Status of nutaxi-route-service](https://travis-ci.org/microservicesteam/nutaxi-route-service.svg?branch=master)](https://travis-ci.org/microservicesteam/nutaxi-route-service) [![Code covarage of nutaxi-route-service](https://codecov.io/gh/microservicesteam/nutaxi-route-service/branch/master/graph/badge.svg)](https://codecov.io/gh/microservicesteam/nutaxi-route-service) [![Dependency Status](https://www.versioneye.com/user/projects/57d03f4d864739000ef97460/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57d03f4d864739000ef97460)


Routing service is responsible to provide a route for origin-destination pair

## Build locally
```
mvn clean install
```
This will execute all the checks and tests.

## Run locally
```
mvn clean spring-boot:run
```
Configuration will be fetched from the remove configuration server.

## Create local docker image
```
mvn clean install docker:build
```
The new docker image will be pushed to the local image repository named  `microservicesteam/nutaxi-route-service` labelled with `${project.version}` and `latest` tags.

Note that the docker plugin is not bound to any lifecycle events, therefore it should be executed alongside with `package` or `install` goals.

The created image will execute the application with `default` profile. In case you would like to use another profile, use `-Ddocker-spring-profile=<profile>` parameter as below:

```
mvn clean install docker:build -Ddocker-spring-profile=docker-aws
```

**Hint for Windows/Mac users**

`docker-machine` have to be accessible for the current bash/powershell session. In case the maven docker plugin cannot connect (`Connect to localhost:2375 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect`) execute the following command:
```
docker-machine env
```
It will generate an output, which can be used for configuration, e.g.:
```
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.99.100:2376"
export DOCKER_CERT_PATH="C:\Users\Zoltán\.docker\machine\machines\default"
export DOCKER_MACHINE_NAME="default"
# Run this command to configure your shell:
# eval $("C:\Program Files\Docker Toolbox\docker-machine.exe" env)
```
As the above example output says, the last `eval` command needs to be executed separately to expose `docker-machine` for this session.

## Publish docker image to remote repository
```
mvn clean install docker:build -DpushImageTag
```
Note that you have to logged in to push images to the remote repository using
```
docker login
```
**Hint for Windows users**
It seems that sometimes Cygwin cannot receive the stdout/stderr handles properly from the Win32 API, so docker won't consider it an interactive TTY. In this case you will get
```
$ docker login
Error: Cannot perform an interactive login from a non TTY device
```
If you face the above issue, you can supply the login/password details as command line parameters:
```
docker login --username <username> --password <password>
```
or alternatively execute login and build steps using the Docker Quickstart Terminal.
