#!/bin/bash

WORK_HOME=$(pwd)

LOCALCLASSPATH=.:$WORK_HOME/lib/*

JAVA=`where java`
$JAVA -Dfile.encoding=UTF-8 -classpath $LOCALCLASSPATH -XX:PermSize=256m -XX:MaxPermSize=512m -Xms1024m -Xmx1024m com.combat.core.Engine
