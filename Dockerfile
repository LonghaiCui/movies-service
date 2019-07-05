FROM mherwig/alpine-java-mongo

RUN mongod --fork --logpath /var/log/mongod.log