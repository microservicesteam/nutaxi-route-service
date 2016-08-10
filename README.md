# nutaxi-route-service

[![Build Status](https://travis-ci.org/microservicesteam/nutaxi-route-service.svg?branch=master)](https://travis-ci.org/microservicesteam/nutaxi-route-service)

Routing service is responsible to provide a route for origin-destination pair

## How to run locally
Since Google Maps needs an API key, which comes from a property source, you have to create a property repo first.
 - Create a folder named `/tmp/local-config`
 - Initialise a Git repo there, with `git init`
 - Create a file in it, named `nutaxi-route-service.yml`
 - Enter the API key with `google-maps.api-key: <whateveryourapikeyis>` format
 - Commit the change
 - Start the server locally