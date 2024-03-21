#!/bin/bash
nohup java -jar /home/baipiao/target/baipiao-0.0.1-SNAPSHOT.jar > /home/logs/baipiao/service_out.log 2>&1 &

echo $! > /home/logs/baipiao/pid.file
