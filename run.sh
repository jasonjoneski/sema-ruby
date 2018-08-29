#!/bin/sh

./gradlew assemble
java -Dsema.code.folder=$PWD/web/code -jar web/build/libs/web-all.jar
