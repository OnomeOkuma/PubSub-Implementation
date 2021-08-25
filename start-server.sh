#!/bin/bash

echo "Building Publisher Server"
cd ./publisher
mvn package -DskipTests
mv ./target/publisher-0.0.1.jar ..
cd ..

echo "Building Subscriber Server"
cd ./subscriber
mvn package -DskipTests
mv ./target/subscriber-0.0.1.jar ..
cd ..

echo "Starting Services"
java -jar subscriber-0.0.1.jar &
sleep 20s;
java -jar publisher-0.0.1.jar
echo "Services Started"


