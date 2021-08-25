#!/bin/bash

echo "Building Publisher Server"
cd ./publisher
mvn package -DskipTests

echo "Starting Publisher Server on port 5000"
java -jar ./target/publisher-0.0.1.jar
echo "Publisher Server started on port 5000"

cd ..

echo "Building Subscriber Server"
cd ./pubscriber
mvn package -DskipTests

echo "Starting Subscriber Server on port 9000"
java -jar ./target/subscriber-0.0.1.jar
echo "Subscriber Server started on port 9000"

echo "Services Started"


