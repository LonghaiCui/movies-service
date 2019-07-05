#!/bin/bash

docker ps | grep mongodb-container

if [ $? -eq 0 ] ; then
    docker container stop mongodb-container
    docker container rm mongodb-container
    docker image prune mongodb-run
fi

docker build -t mongodb-run .
docker run --name mongodb-container -d mongodb-run
