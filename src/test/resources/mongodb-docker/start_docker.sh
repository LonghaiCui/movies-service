#!/bin/bash

docker container stop mongodb-container
docker container rm mongodb-container

docker build -t mongodb-run .
docker run -d -p 27017-27019:27017-27019 --name mongodb-container mongodb-run


