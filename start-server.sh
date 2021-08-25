#!/bin/bash

echo "Building Publisher Server"
cd ./publisher
mvn package -DskipTests

cd ..

echo "Building Subscriber Server"
cd ./subscriber
mvn package -DskipTests

echo "Starting Services"

& java -jar ./target/subscriber-0.0.1.jar
cd .. & cd ./publisher
& java -jar ./target/publisher-0.0.1.jar 


echo "Services Started"


