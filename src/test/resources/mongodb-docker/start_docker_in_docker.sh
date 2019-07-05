#!/bin/bash

docker container stop mongodb-container
docker container rm mongodb-container

docker build -t mongodb-run src/test/resources/mongodb-docker/

docker create --name mongodb-container --network bridge mongodb-run
docker network connect bridge mongodb-container
docker container start  mongodb-container


