#!/bin/bash

mvn clean package -DskipTests=true -X 1>build.log 2>&1

#1>build.log 2>&1
