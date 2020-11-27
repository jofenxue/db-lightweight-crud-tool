#!/bin/bash
pid=`ps -ef|grep "ZJMain" |awk '{if($0!~/grep/)print $2}'`
if [ "$pid" != "" ]; then
    echo $pid
    echo "server is already started"
	exit 1

else

	ZJCK_HOME=~/BSDayEndChecker-1.1

	LOCALCLASSPATH=.:$ZJCK_HOME/classes/:$ZJCK_HOME/bin:$ZJCK_HOME/lib/*
	
	$HOME/java/jdk/bin/java -Dfile.encoding=UTF-8 -classpath $LOCALCLASSPATH -XX:PermSize=256m -XX:MaxPermSize=512m -Xms1024m -Xmx1024m com.zjsys.engine.ZJMain &
	if [ $? -eq 0 ]; then
	    echo "server start successful"
		exit 0
	else
	    echo "server start failed"
		exit 1
	fi
fi
