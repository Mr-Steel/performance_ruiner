![alt text] (https://travis-ci.org/Mr-Steel/performance_ruiner.png?branch=master)
# A showcase for the talk about "How to ruin your Application Performance"
This project contains in several packages samples which show the different runtime performance mostly as JMH benchmarks.


# Memory Measure
start with: java -javaagent:lib\jamm-0.3.3.jar -jar target\measure-me-1.0.0-SNAPSHOT.jar
or run main class with vm arguments: "-javaagent:lib\jamm-0.3.3.jar"