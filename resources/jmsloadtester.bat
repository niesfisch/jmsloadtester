@echo off
REM **************************************************************************
REM JMS Load Tester startup script
REM    JAVA_HOME points to the jdk/jre home which should have 
REM    a subfolder called "bin" containing "java.exe"
REM    
REM    http://jmsloadtester.marcel-sauer.de/
REM **************************************************************************

REM your classpath with the jar files goes here !!

REM OpenJMS example
REM set OPENJMS_LIBS=D:\dev\eclipse\workspace_TRUNK\JMSLoadTester\lib\openjms\
REM set OPENJMS_CLASSPATH=%OPENJMS_LIBS%\openjms-0.7.7-beta-1.jar;%OPENJMS_LIBS%\jms-1.1.jar

REM SonicMQ example
REM set SONICMQ_LIBS=D:\dev\eclipse\workspace_TRUNK\JMSLoadTester\lib\sonic\
REM set SONICMQ_CLASSPATH=%SONICMQ_LIB%\mfcontext.jar;%SONICMQ_LIB%\sonic_Client.jar;

REM Bea Weblogic example
REM set WEBLOGIC_LIBS=D:\dev\eclipse\workspace_TRUNK\JMSLoadTester\lib\weblogic\
REM set WEBLOGIC_CLASSPATH=%WEBLOGIC_LIBS%\weblogic.jar;

REM YOUR_CP needs to point to the classpath you just setup
REM e.g.
REM set YOUR_CP=%OPENJMS_CLASSPATH%
REM or
REM set YOUR_CP=%SONICMQ_CLASSPATH%
REM or
REM set YOUR_CP=%WEBLOGIC_CLASSPATH%


REM **************************************************************************
REM
REM you should not touch the lines below 
REM unless you know what you are doing ;-)
REM
REM **************************************************************************

if not "%JAVA_HOME%" == "" goto withJavaHome
echo the JAVA_HOME environment variable is not set!
echo please set this as the JMS Load Tester needs it
exit 

:withJavaHome

if exist "%JAVA_HOME%\bin\java.exe" goto start
echo the JAVA_HOME environment variable is not set!
echo please set this as the JMS Load Tester needs it
exit

:start

set CLASS=de.marcelsauer.jmsloadtester.Main
set TESTER_JAR=jmsloadtester_0.1.jar 
set JAVA_EXE="%JAVA_HOME%\bin\java"

set CONFIG_FILE=./conf/app.properties
set CP=%CLASSPATH%;%YOUR_CP%;%TESTER_JAR%
set CMD=%JAVA_EXE% -classpath "%CP%" %CLASS% -Dapp.properties.file=%CONFIG_FILE%

echo using JAVA_HOME:    %JAVA_HOME%
echo using YOUR_CP:      %YOUR_CP%
echo using CLASSPATH:    %CLASSPATH%
echo using CMD:          %CMD%
echo staring JMS Load Tester ....

%CMD%