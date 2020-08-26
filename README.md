[![Build Status](https://travis-ci.org/Mr-Steel/performance_ruiner.png?branch=master)](https://travis-ci.org/Mr-Steel/performance_ruiner)
# A showcase for the talk about "How to ruin your Application Performance"
This project contains in several packages samples which show the different runtime performance mostly as JMH benchmarks.

## Requirements
* JDK 11 or newer (Project can be easily downgraded to JDK 8)
* Latest stable [Maven](http://maven.apache.org/)

## JMH Benchmarks
How to build:
* run _mvn clean package_

How to run:
* run _java -jar target\benchmarks.jar_

## Memory Measure
How to build:
* run _mvn clean package_

How to run:
* CLI: start with: _java -javaagent:lib\jamm-0.3.3.jar -jar target\measure-me-1.0.0-SNAPSHOT.jar_
* IDE: run main class (MemoryMeasure.java) with vm arguments: "-javaagent:lib\jamm-0.3.3.jar"