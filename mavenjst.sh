#!/bin/bash

mvn install:install-file -Dfile=jst-sdk.jar -DgroupId=com.jst.sdk -DartifactId=jst-api -Dversion=0.0.1 -Dpackaging=jar
