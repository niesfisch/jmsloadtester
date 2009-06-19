#!/bin/sh

# *********************************************************************************
# JMS Load Tester startup script
#    JAVA_HOME points to the jdk/jre home which should have 
#    a subfolder called "bin" containing "java.exe"
# *********************************************************************************

#your classpath with the jar files goes here
# OpenJMS example
# OPENJMS_LIBS=/media/hda2/backup/projects/_private/JMSLoadTester/lib/openjms/
# OPENJMS_CLASSPATH="$OPENJMS_LIBS"/openjms-0.7.7-beta-1.jar:"$OPENJMS_LIBS"/jms-1.1.jar

# SonicMQ example
# SONICMQ_LIBS=/media/hda2/backup/projects/_private/JMSLoadTester/lib/sonic/
# SONICMQ_CLASSPATH="$SONICMQ_LIB"/mfcontext.jar:"$SONICMQ_LIB"/sonic_Client.jar;

# Bea Weblogic example
# WEBLOGIC_LIBS=/media/hda2/backup/projects/_private/JMSLoadTester/lib/weblogic/
# WEBLOGIC_CLASSPATH="$WEBLOGIC_LIBS"/weblogic.jar;

# OpenMQ example
# OPENMQ_LIBS=/media/hda2/backup/projects/_private/JMSLoadTester/lib/openmq/
# OPENMQ_CLASSPATH="$OPENMQ_LIBS"/fscontext.jar;"$OPENMQ_LIBS"/jms.jar;"$OPENMQ_LIBS"/ims.jar;

# YOUR_CP needs to point to the classpath you just setup
# e.g.
# YOUR_CP="$OPENJMS_CLASSPATH"
# or
# YOUR_CP="$SONICMQ_CLASSPATH"
# or
# YOUR_CP="$WEBLOGIC_CLASSPATH"
# or
# YOUR_CP="$OPENMQ_CLASSPATH"

# **************************************************************************
#
# you should not touch the lines below 
# unless you know what you are doing ;-)
#
# **************************************************************************

if [ -z "$JAVA_HOME" ]; then
	echo the JAVA_HOME environment variable is not set!
	echo please set this as the JMS Load Tester needs it
	exit 1
fi

if [ ! -r "$JAVA_HOME"/bin/java ]; then
	echo the JAVA_HOME environment variable is not set!
	echo please set this as the JMS Load Tester needs it
	exit 1
fi

LIB_DIR="./lib"
SPRING_JAR="$LIB_DIR/spring-framework-2.5.4/spring.jar"
COMMONS_LOGGING_JAR="$LIB_DIR/commons-logging-1.1.1/commons-logging-1.1.1.jar"
TESTER_JAR="$LIB_DIR/jmsloadtester_1.0.jar" 
CLASS=de.marcelsauer.jmsloadtester.Main
JAVA_EXE="$JAVA_HOME"/bin/java

CONFIG_FILE=./conf/app.properties
CP="$CLASSPATH":"$YOUR_CP":"$TESTER_JAR":"$SPRING_JAR":"$COMMONS_LOGGING_JAR":"./conf/"
CMD="$JAVA_EXE -Dapp.properties.file=$CONFIG_FILE -classpath $CP $CLASS"

echo "using JAVA_HOME:   $JAVA_HOME"
echo "using YOUR_CP:     $YOUR_CP"
echo "using CLASSPATH:   $CLASSPATH"
echo "using CMD:         $CMD"

echo "staring JMS Load Tester ...."

exec $CMD
