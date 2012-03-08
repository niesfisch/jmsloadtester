#!/bin/sh

# *********************************************************************************
# JMS Load Tester startup script
#    JAVA_HOME points to the jdk/jre home which should have 
#    a subfolder called "bin" containing "java.exe"
# *********************************************************************************

# your classpath with the jar files goes here

# HornetQ example
# HORNET_CLASSPATH=/home/msauer/.m2/repository/org/hornetq/hornetq-jms-client/2.1.2.Final/hornetq-jms-client-2.1.2.Final.jar
# HORNET_CLASSPATH=$HORNET_CLASSPATH:/home/msauer/.m2/repository/org/hornetq/hornetq-core-client/2.1.2.Final/hornetq-core-client-2.1.2.Final.jar
# HORNET_CLASSPATH=$HORNET_CLASSPATH:/home/msauer/.m2/repository/javax/jms/jms/1.1/jms-1.1.jar
# HORNET_CLASSPATH=$HORNET_CLASSPATH:/home/msauer/.m2/repository/jboss/jnp-client/4.0.2/jnp-client-4.0.2.jar
# HORNET_CLASSPATH=$HORNET_CLASSPATH:/home/msauer/.m2/repository/org/jboss/logging/jboss-logging-spi/2.0.5.GA/jboss-logging-spi-2.0.5.GA.jar
# HORNET_CLASSPATH=$HORNET_CLASSPATH:/home/msauer/.m2/repository/org/jboss/netty/netty/3.2.0.Final/netty-3.2.0.Final.jar

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
# YOUR_CP="$HORNET_CLASSPATH"
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

LIBS="./lib/*"
CLASS=de.marcelsauer.jmsloadtester.Main
JAVA_EXE="$JAVA_HOME"/bin/java

CONFIG_FILE=./conf/app.properties
CP="$CLASSPATH":"$YOUR_CP":"$LIBS":"./conf/"
CMD="$JAVA_EXE -Dapp.properties.file=$CONFIG_FILE -classpath $CP $CLASS"

echo "using JAVA_HOME:   $JAVA_HOME"
echo "using YOUR_CP:     $YOUR_CP"
echo "using CLASSPATH:   $CLASSPATH"
echo "using CMD:         $CMD"

echo "staring JMS Load Tester ...."

exec $CMD
