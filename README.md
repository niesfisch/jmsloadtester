# What's that for?

JMS Load Tester is a small open source command line based Java (>1.6) application to load test JMS brokers like SonicMQ, ActiveMQ, OpenJMS, HornetQ and others.

It is configured using a properties file and should run under Windows and Un*x systems.

The tool can start multiple Threads (Listeners, Senders) that "penetrate" the JMS broker by sending and/or receiving loads of messages. That's already all - no magic at all. It can be configured with a property file and all the broker and destination lookup is done via JNDI. At the moment the tool handles everything as generic JMS "destination". It is not aware of the underlaying implementations like Queue or Topic. The destination lookup is also done via JNDI. Have a look at the Screenshots, the User Guide and the different property values which should give you a fairly good understanding of what this tool does and how it works.

Please let me know which features are missing, what does not work as expected or seems to be buggy.

# Getting JMS Load Tester

just take the latest zip from the [downloads](http://github.com/niesfisch/jmsloadtester/downloads) and follow the setup instructions further down.

or clone the repository and build from scratch with maven

    git clone git://github.com/niesfisch/jmsloadtester.git
    cd jmsloadtester.git
    mvn clean package
    cd target/dist/jmsloadtester_xxx <----- here you will find the package application
    chmod a+x jmsloadtester.sh

# Licence

Apache 2.0 http://www.apache.org/licenses/LICENSE-2.0.txt

# Release Notes

[Release Notes](https://github.com/niesfisch/jmsloadtester/blob/master/releasenotes.txt)

# Screenshots

<p>
    <a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/silent.jpg" class="none"><img class="" title="running JMS Load Tester with minimal configured output" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/silent-300x124.jpg" alt="running JMS Load Tester with minimal configured output" width="300" height="124"></a>
    <a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/appconf.jpg" class="none"><img class="none" title="running JMS Load Tester with minimal configured output" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/appconf-300x166.jpg" alt="app.properties config file used to setup JMS Load Tester" width="300" height="166"></a>
</p>
<p>
    <a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/debug.jpg" class="none"><img class="alignnone size-medium wp-image-12" title="running JMS Load Tester with debug output to STDOUT enabled" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/debug-300x234.jpg" alt="running JMS Load Tester with debug output to STDOUT enabled" width="300" height="234"></a>
    <a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/jndi3.jpg" class="none"><img class="alignnone size-medium wp-image-13" title="typical JNDI configuration examples" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/jndi3-300x267.jpg" alt="typical JNDI configuration examples" width="300" height="267"></a>
    <a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/messages.jpg" class="none"><img class="alignnone size-medium wp-image-14" title="running JMS Load Tester printing all sent/received messages to STDOUT" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/messages-300x234.jpg" alt="running JMS Load Tester printing all sent/received messages to STDOUT" width="300" height="234"></a>
</p>

# Basic Setup

1. download the zip file and extract it to any location you like.
2. change into that directory
3.
open the file **conf/jndi.properties** and use the JNDI provider settings that the app should use to lookup the connection factories and destinations. this is just to make sure the application can connect to a local or remote JNDI repository, nothing more.typical examples can be found in the conf/samples folder. have a look at the JNDI setup instructions of the provider you are using or ask you sysadmins for the connection details. the application is using this information to create the JNDI ["InitialContext"](http://java.sun.com/products/jndi/tutorial/basics/prepare/initial.html) and holds this for the rest of the runtime.

e.g. a typical SonicMQ setup

    java.naming.provider.url=tcp://localhost:2506
    java.naming.factory.initial=com.sonicsw.jndi.mfcontext.MFContextFactory
    java.naming.security.principal=Administrator
    java.naming.security.credentials=Administrator

4. open the file **conf/app.properties** and change the value for the property "javax.jms.ConnectionFactory" to the jms connection factory that should be used to create the sessions. this has to be available in the JNDI repository. a typical setup for SonicMQ topic/queue connection factory can be found here. the connection factory can be a topic or queue connection factory as long as it implements the [javax.jms.ConnectionFactory](http://java.sun.com/j2ee/1.4/docs/api/javax/jms/ConnectionFactory.html) interface.

5. close the files and save them

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/sonic_admin1.jpg" class="none"><img title="sonic admin" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/sonic_admin1-150x150.jpg" alt="sonic admin" width="150" height="150"></a>
<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/sonic_admin2.jpg" class="none"><img title="sonic admin" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/sonic_admin2-150x150.jpg" alt="sonic admin" width="150" height="150"></a>
<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/openjms_screen.jpg" class="none"><img title="openjms" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/openjms_screen-150x150.jpg" alt="openjms" width="150" height="150"></a>

in order to use the application you have to place certain vendor jar files in the classpath, which are not part of JMS Load Tester itself. when the application is starting it is trying to connect to the JNDI repository you provided in the jndi.properties files. it then tries to get a javax.jms.ConnectionFactory object(instance) via JNDI. to get this working the JRE/JVM needs these provider specific implementations in the classpath. if they are not present you will get the infamous "ClassNotFoundException". consult the documentation of your JMS provider. the selected jar files in the following screenshots give you an idea:

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/openjms_libs.jpg" class="none"><img title="typical openjms libs" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/openjms_libs-150x150.jpg" alt="typical openjms libs" width="150" height="150"></a>
<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/weblogic_libs.jpg" class="none"><img title="typical weblogic libs" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/weblogic_libs-150x150.jpg" alt="typical weblogic libs" width="150" height="150"></a>
<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/sonic_libs.jpg" class="none"><img title="typical sonic libs" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/sonic_libs-150x150.jpg" alt="typical sonic libs" width="150" height="150"></a>

1. open the file **jmsloadtester.bat** (Windows) or **jmsloadtester.sh** (Un*x) and point the "YOUR_CP" to all the jars that should be included (i.e. the vendor jars we just talked about)
2. look at the example lines starting with "set YOUR_CP=..." (Windows) or "YOUR_CP=..." (Un*x) and use them (delete the "REM" or "#") in front of them
3. save the file and close it
4. look at the more detailed explanation

now open the file **app.properties** again. its time to configure the actual load test.

change the the lines starting with "app.listener.*"

    app.listener.thread.count=1
    app.listener.wait.for.message.count=1
    app.listener.listen.to.destination=name of your Topic or Queue, must be available in the JNDI repository

change the the lines starting and "app.sender.*"

    app.sender.threads.to.start=1
    app.sender.send.to.destination=name of your Topic or Queue, must be available in the JNDI repository
    app.sender.message.content.strategy=STATIC#1#this is the test message
    app.sender.pause.millis.between.send=0

setup how much output you want to see

    app.output.debug.strategy=STDOUT
    app.output.result.strategy=STDOUT
    app.output.message.strategy=STDOUT

save and close all the open files. execute the "jmsloadtester.bat" or "jmsloadtester.sh". the application should be running, sending to and receiving from the destination you configured.

if everything is working then you can continue with doing some real load testing by changing the settings and "burning down" your JMS broker. Have a look at some useful settings to start with (further down). Try to watch the load on the broker and network in parallel.

## app.properties

... [gives you an overview](https://github.com/niesfisch/jmsloadtester/blob/master/src/main/assembly/conf/app.properties) of what you can setup

## the "app.sender.message.content.strategy"

tells the application what message content to send.

**never delete the dividers ("#") in the line. the application needs them to split the settings.**

e.g.

    app.sender.message.content.strategy=STATIC#500#your message content

would tell each sending thread to send the message "your message content" 500 times. if you have 20 parallel threads running this would result in 500 * 20 = 1000 messages to be sent.

e.g.

    app.sender.message.content.strategy=FOLDER#8#.*txt#c:/testmessages

folder looks like:

    c:/testmessages
    message1.txt
    message2.txt
    message4.jpg
    message5.xml
    message6.doc
    message7.txt

would tell each thread to take every file ending with "txt" in the folder "c:/testmessages" and send it "8" times. if you have 20 parallel threads running this would result in 20 * 3 * 8 = 480 messages to be sent (only the .txt files  and each one 8 times). this only works on the specified folder, not recursively. be careful when choosing the matcher for the files. it has to be provided in a Regular Expression format that Java [understands](http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.html). **make sure you provide forward slashes "/" for directories**. the following table lists some typical examples.

    .*            all files
    .*.txt        all files ending with .txt
    .*message.*   all files having the word "message" in the filename
    123.text      only the file "123.text"
    ^[\\d]+.text  all files starting with at least one (+) digits (0-9), e.g. 123.text but not test123.text


use [this description](http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.html) to get an overview of what the "matcher" can do. make sure you escape all "constructs" with a backslash. e.g. "\d" stands for all known digits, when you want to use it you have to put "\\d" into the app.properties file, otherwise the application would search for a file with "\d" in the file name.

you are also able to put in certain placeholders. this works for **STATIC** and **FOLDER** (in each file that will be sent). the table below lists the allowed placeholders. each placeholder has to be surrounded by colons ":"


    nanotime   outputs the nanotime (timestamp) when the message was generated, e.g. 1322952392731831
    datetime   outputs the date and time when the message was generated, e.g. Tue May 13 17:33:36 BST 2008
    random     outputs a random number (uses java.util.Random.nextLong()), e.g. -2893472384729384 or 39483983349

e.g.

    app.sender.message.content.strategy=STATIC#1#this is the test message :datetime: :nanotime:

the resulting text in the message should look something like:

    this is the test message Tue May 13 17:43:07 BST 2008 1322952392731831

## the "app.output.XXX.strategy"

defines where the output should go. if you don't want to see every detail about the running application then it makes sense to set every output to "SILENT". this leaves you with only the most important information beeing printed. be careful when you use the **FILE** output. this will slow down the performance as the application has to handle the file access. try the various settings and see how much is "useful" for you. turn everything on (STDOUT) if you are using the application for the first time, as you should know how this tool works. you are free to choose any of the settings for each strategy.

    STDOUT    prints to standard out (STDOUT)
    STDERR    prints to standard error (STDERR)
    SILENT    prints nothing
    FILE#     prints to the specified file, make sure you have the hash "#" after FILE

e.g.

    app.output.debug.strategy=STDERR
    app.output.result.strategy=FILE#c:/results/results.txt
    app.output.message.strategy=STDOUT

or

    app.output.debug.strategy=FILE#c:/results/debug.txt
    app.output.result.strategy=FILE#c:/results/results.txt
    app.output.message.strategy=FILE#c:/results/messages.txt

or

    app.output.debug.strategy=SILENT
    app.output.result.strategy=SILENT
    app.output.message.strategy=SILENT

## the "jmsloadtester.bat" and "jmsloadtester.sh"

the application is shipped without the provider specific jars holding the JMS implementation. that's why you have to provide them, i.e. point to them, in the .bat file. the application then knows where to find them.
to do so open the "jmsloadtester.bat" or "jmsloadtester.sh" with an editor and append one or multiple "set YOUR_CP" lines (after the "REM your classpath with the jar files goes here !!").


OpenJMS example

    ...
    set OPENJMS_LIBS=D:\dev\eclipse\workspace_TRUNK\JMSLoadTester\lib\openjms\
    set OPENJMS_CLASSPATH=%OPENJMS_LIBS%\openjms-0.7.7-beta-1.jar;%OPENJMS_LIBS%\jms-1.1.jar
    ...
    set YOUR_CP=%OPENJMS_CLASSPATH%
    ....

would tell the application to load the two OpenJMS specific jar files "openjms-0.7.7-beta-1.jar" and "jms-1.1.jar" when the application is starting up. have a look at the samples in the "bat" or "sh" file. to figure out which jar files you need consult the documentation of your broker. a not so perfect solution would be to include all jar files from the lib directory of your JMS broker (remember: "not so perfect" ;-)).
make sure that you include the "%YOUR_CP%" in each line you add after the first line. e.g.

    ...
    set YOUR_LIBS=D:\providerA\libs\
    set YOUR_CLASSPATH=%YOUR_LIBS%\a.jar;%YOUR_LIBS%\b.jar;%YOUR_LIBS%\c.jar;%YOUR_LIBS%\d.jar;
    ...
    set YOUR_CP=%YOUR_CLASSPATH%
    ....

i think you got the idea ;-)

## some settings to start with

in order to load test a JMS broker it makes sens to try different settings, e.g. the senders that are started, how many messages they should send, the pause they should do between each send and so on. the following settings should give you an idea of how to start.
i would start by only sending messages, i.e. set the listeners to 0 and try different sender counts

    # listener setup
    app.listener.thread.count=0

    # sender setup
    app.sender.threads.to.start=10
    app.sender.message.content.strategy=STATIC#100#test message :datetime: :random: :nanotime:
    app.sender.pause.millis.between.send=0

will send 10 * 100 = 1000 messages to the broker

    # sender setup
    app.sender.threads.to.start=40
    app.sender.message.content.strategy=STATIC#1000#test message :datetime: :random: :nanotime:
    app.sender.pause.millis.between.send=0

will send 40 * 1000 = 40000 messages to the broker


    # sender setup
    app.sender.threads.to.start=80
    app.sender.message.content.strategy=STATIC#1000#test message :datetime: :random: :nanotime:
    app.sender.pause.millis.between.send=0

will send 80 * 1000 = 80000 messages to the broker

now it's time for some real action

    # sender setup
    app.sender.threads.to.start=1000
    app.sender.message.content.strategy=STATIC#1000#test message :datetime: :random: :nanotime:
    app.sender.pause.millis.between.send=0


will send 1000 * 1000 = 1000000 messages to the broker


set all the output strategies to "SILENT" to get maximum performance.
on my machine i get a "heap space out of memory" exceptions or the broker loses it's connections and so on when i reach a certain limit. that's why it is is a load test tool at all :)

## things to bear in mind

at the moment JMS Load Tester is starting as many threads as you have defined in the app.properties file. it depends on the speed of the local JVM running the application how fast everything will be. it does not wait until all threads have started up and then sends all the messages. each started thread sends it's messages as soon as it has started. so no "synchronous" "fire-now" thing. on a slow machine with litte RAM you won't get the same performance as on a fast machine with lots of RAM. with less RAM you are likely to get "HeapSpaceOutOfMemory" exceptions.

### Sender and Listener examples

when you define listeners and senders for the same destination then you should see that messages sent arrive back in the application because they share the same destination. some typical scenarios (we assume the same destination is set):

<table border="1">
<tbody>
<tr>
<th></th>
<th>count</th>
<th>messages</th>
<th>total</th>
</tr>
<tr>
<td>senders</td>
<td>10</td>
<td>100</td>
<td>1000</td>
</tr>
<tr>
<td>listeners</td>
<td>10</td>
<td>100</td>
<td>1000</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">1000 messages should have been sent and received on the same destination. the program exits and prints the result.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>20</td>
<td>500</td>
<td>10000</td>
</tr>
<tr>
<td>listeners</td>
<td>500</td>
<td>20</td>
<td>10000</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">10000 messages should have been sent and received on the same destination. the program exits and prints the result.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>100</td>
<td>40</td>
<td>4000</td>
</tr>
<tr>
<td>listeners</td>
<td>1</td>
<td>1000</td>
<td>1000</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">4000 messages should have been sent. 4000 should have been received, even if only 1000 were expected. the program exits and prints the result.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>70</td>
<td>100</td>
<td>7000</td>
</tr>
<tr>
<td>listeners</td>
<td>70</td>
<td>1000</td>
<td>70000</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">7000 messages should have been sent. 7000 of 70000 expected should have been received. the program will wait until all of the 70000 expected messages are received.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>60</td>
<td>10000</td>
<td>600000</td>
</tr>
<tr>
<td>listeners</td>
<td>1</td>
<td>1</td>
<td>1</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">600000 messages should have been sent. 600000 should have been received, even if only 1 was expected. the program exits and prints the result.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>0</td>
<td>100</td>
<td>0</td>
</tr>
<tr>
<td>listeners</td>
<td>2</td>
<td>100</td>
<td>200</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">0 messages should have been sent. 0 of 200 expected should have been received (because nothing is sending). the program will wait until all of the 200 expected messages are received.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>10</td>
<td>10</td>
<td>100</td>
</tr>
<tr>
<td>listeners</td>
<td>11</td>
<td>10</td>
<td>110</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">100 messages should have been sent. 100 of 110 expected should have been received. the program will wait until all of the 110 expected messages are received.</td>
</tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td>senders</td>
<td>10</td>
<td>10</td>
<td>100</td>
</tr>
<tr>
<td>listeners</td>
<td>0</td>
<td>100</td>
<td>0</td>
</tr>
<tr>
<td><b>result</b></td>
<td colspan="3">100 messages should have been sent. nothing is listening for incoming messages. the program exits and prints the result.</td>
</tr>
</tbody>
</table>

listeners can receive more than they were waiting for as long as they get the messages they were waiting for based on the configuration.

## creating your own session handler and message interceptors

follow these steps to overwrite the default session handler and call you own one (all the examples are illustrated based on eclipse)
1. create a new eclipse project -> e.g. JmsLoady
2. copy the "conf" and "lib" directories from the jms load tester distribution into the new projects root folder (see image)

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_eclipse_project_11.jpg"><img class="alignnone size-thumbnail wp-image-39" title="ext_eclipse_project_11" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_eclipse_project_11-150x150.jpg" alt="" width="150" height="150"></a>

3. now open the project settings
- add all the jar files found in the libs directory to the "libraries" tab (see image)
- add the "conf" directory on the "source" tab (see image)

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_libs_2.jpg"><img class="alignnone size-thumbnail wp-image-35" title="ext_libs_2" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_libs_2-150x150.jpg" alt="" width="150" height="150"></a>
<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_source_3.jpg"><img class="alignnone size-thumbnail wp-image-30" title="ext_source_3" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_source_3-150x150.jpg" alt="" width="150" height="150"></a>

4. now add all your necessary jms api libs to the classpath (like sonic jars, openmq etc.)
5. open the "conf/app.properties" and "conf/jndi.properties" file and change to your appropriate values
6. open the "run as" dialogue (see image)
- double click on "java application", enter "de.marcelsauer.jmsloadtester.Main" as main class in the "Main" tab
- open the "arguments" tab and add the line "-Dapp.properties.file=./conf/app.properties" to the "VM arguments" (see image)
- save and run the app

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_run_as1_4.jpg"><img class="alignnone size-thumbnail wp-image-36" title="ext_run_as1_4" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_run_as1_4-150x150.jpg" alt="" width="150" height="150"></a>
<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_run_as2_5.jpg"><img class="alignnone size-thumbnail wp-image-37" title="ext_run_as2_5" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_run_as2_5-150x150.jpg" alt="" width="150" height="150"></a>

if everything is running then we can go ahead and actually create our own classes
1. create a new package "samples" in src
2. create a new class "TheSampleSessionHandler.java" which extends "AbstractThreadAwareSessionHandler" (take the sample one from the image)

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_sessionhandler_6.jpg"><img class="alignnone size-thumbnail wp-image-41" title="ext_sessionhandler_6" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_sessionhandler_6-150x150.jpg" alt="" width="150" height="150"></a>

3. create two new classes "TheSampleInterceptor1" and "TheSampleInterceptor2" which implement "MessageInterceptor" (take the sample one from the image)

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_interceptor_7.jpg"><img class="alignnone size-thumbnail wp-image-34" title="ext_interceptor_7" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_interceptor_7-150x150.jpg" alt="" width="150" height="150"></a>

4. now open the "conf/app.properties" and add/change the values (see image)

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_appconfig_8.jpg"><img class="alignnone size-thumbnail wp-image-32" title="ext_appconfig_8" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_appconfig_8-150x150.jpg" alt="" width="150" height="150"></a>

5. build and run the application again
6. check the console window for the "system.out.println" lines (see image) which prove that our new classes are used

<a href="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_sysout_9.jpg"><img class="alignnone size-thumbnail wp-image-31" title="ext_sysout_9" src="https://raw.githubusercontent.com/niesfisch/jmsloadtester/master/doc/images/ext_sysout_9-150x150.jpg" alt="" width="150" height="150"></a>

you should now have an idea of how these "pluggable" features work and create some real stuff
the rest is up to you :)
feel free to contact me if something is wrong or unclear or you know a better way to do things.

# How it works - some details

just a short wrap up of how JMS Load Tester works

- Java > 1.6, purely based on Java interfaces, provider specific implementations are not bundled, they have to be provided by you (in form of "jar" files)
- all lookup and retrieving is done via JNDI (Factories, Queues, Topics)
- the application creates a Thread for each listener and sender (as configured in the app.properties)
- two central pieces keep track of everything created (ThreadTracker) and messages sent&received (MessageTracker)
- each Thread gets its own javax.jms.Session instance (via ThreadLocal)
- one javax.jms.Connection is reused and shared
- the javax.naming.Context is reused and shared
- the javax.jms.Destination instances are cached
- the javax.jms.MessageProducer instances are cached (per Thread)
- the application uses Runtime.getRuntime().addShutdownHook to register certain components for cleanup
- currently supports javax.jms.BytesMessage and  javax.jms.TextMessage
- messages loaded from the file system are cached once they are loaded the first time
- message loaded from the file system are stored as byte array until they are actually send
- the timer works based on System.nanoTime()
- different acknowledgement modes are supported when creating the sender sessions

can this be done better? i am sure it can. as i am neither a JMS nor a Threading expert there are loads of things that could be improved i guess. why not give some suggestions after you have tried the tool?
