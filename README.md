# What's that for? 

JMS Load Tester is a small open source command line based Java (>1.6) application to load test JMS brokers like SonicMQ, ActiveMQ, OpenJMS and others. 

It is configured using a properties file and should run under Windows and Un*x systems(i've used it with Ubuntu).

The tool can start multiple Threads (Listeners, Senders) that "penetrate" the JMS broker by sending and/or receiving loads of messages. That's already all - no magic at all. It can be configured with a property file and all the broker and destination lookup is done via JNDI. At the moment the tool handles everything as generic JMS "destination". It is not aware of the underlaying implementations like Queue or Topic. The destination lookup is also done via JNDI. Have a look at the Screenshots, the User Guide and the different property values which should give you a fairly good understanding of what this tool does and how it works.

Please let me know which features are missing, what does not work as expected or seems to be buggy.

# Getting JMS Load Tester

just take the latest zip from the [downloads](http://github.com/niesfisch/jmsloadtester/downloads)

or clone the repository and build from scratch with maven

    git clone git://github.com/niesfisch/jmsloadtester.git
    cd jmsloadtester.git
    mvn clean package
    cd target/dist

the rest is explained [here](http://jmsloadtester.marcel-sauer.de)

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

//-- pictures here .....

in order to use the application you have to place certain vendor jar files in the classpath, which are not part of JMS Load Tester itself. when the application is starting it is trying to connect to the JNDI repository you provided in the jndi.properties files. it then tries to get a javax.jms.ConnectionFactory object(instance) via JNDI. to get this working the JRE/JVM needs these provider specific implementations in the classpath. if they are not present you will get the infamous "ClassNotFoundException". consult the documentation of your JMS provider. the selected jar files in the following screenshots give you an idea:

//-- pictures here .....


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

to be continued.........

## the different app.properties settings:

<table class="guidetable" border="0">
<tbody>
<tr>
<th>setting</th>
<th>values</th>
<th>explanation</th>
</tr>
<tr>
<td valign="top">jndi.properties</td>
<td valign="top">a filename, e.g. &#8220;c:/tmp/jndi.properties&#8221;</td>
<td valign="top">points to the directory and file that holds the jndi connection properties. <span style="color: #ff0000;">make sure you provide forward slashes &#8220;/&#8221; for directories.</span></td>
</tr>
<tr>
<td valign="top">javax.jms.ConnectionFactory</td>
<td valign="top">a name, e.g. SonicMQTopicConnectionFactory</td>
<td valign="top">the name of the connection factory that is setup in your JNDI repository</td>
</tr>
<tr>
<td valign="top">app.listener.thread.count</td>
<td valign="top">a number &gt;= 0; e.g. 20</td>
<td valign="top">how many listener threads should be started</td>
</tr>
<tr>
<td valign="top">app.listener.wait.for.message.count</td>
<td valign="top">a number &gt;= 0, e.g. 1000</td>
<td valign="top">a number telling each thread how many messages to wait for</td>
</tr>
<tr>
<td valign="top">app.listener.listen.to.destination</td>
<td valign="top">a name, e.g. FooQueue or FooTopic or Foo.Test.Topic</td>
<td valign="top">the name of the JMS destination (Queue or Topic) each listener thread should connect to and wait for incoming messages</td>
</tr>
<tr>
<td valign="top">app.sender.threads.to.start</td>
<td valign="top">a number &gt;= 0; e.g. 20</td>
<td valign="top">how many sender threads should be started</td>
</tr>
<tr>
<td valign="top">app.sender.send.to.destination</td>
<td valign="top">a name, e.g. FooQueue or FooTopic or Foo.Test.Topic</td>
<td valign="top">the name of the JMS destination (Queue or Topic) each sender thread should send the messages to</td>
</tr>
<tr>
<td valign="top">app.sender.message.content.strategy</td>
<td valign="top">STATIC or FOLDER (<a href="#app.sender.message.content.strategy">detailed explanation</a>)</td>
<td valign="top">tells each sender what message content to send. always the same (=STATIC) or all files in a specific folder(FOLDER). can match files on regular expressions and embed custom fields that change for each message sent.</td>
</tr>
<tr>
<td valign="top">app.sender.pause.millis.between.send</td>
<td valign="top">a number &gt;= 0; e.g. 20</td>
<td valign="top">tells each sender thread how many milliseconds to pause between each send</td>
</tr>
<tr>
<td valign="top">app.output.pause.seconds.between.printing.progress</td>
<td valign="top">a number &gt;= 1; e.g. 2</td>
<td valign="top">how long to wait before the next progress message is printed. 1 should be sufficient</td>
</tr>
<tr>
<td valign="top">app.output.debug.strategy</td>
<td valign="top">one of STDOUT, STDERR, SILENT, FILE (<a href="#app.output.XXX.strategy">detailed explanation</a>)</td>
<td valign="top">tells the application what to do with the debug output, useful for debugging <img src='http://tech.marcel-sauer.de/wp-includes/images/smilies/icon_wink.gif' alt=';-)' class='wp-smiley' /> and seeing what goes on</td>
</tr>
<tr>
<td valign="top">app.output.result.strategy</td>
<td valign="top">one of STDOUT, STDERR, SILENT, FILE (<a href="#app.output.XXX.strategy">detailed explanation</a>)</td>
<td valign="top">tells the application what to do with the result/summary after the app is finished</td>
</tr>
<tr>
<td valign="top">app.output.message.strategy</td>
<td valign="top">one of STDOUT, STDERR, SILENT, FILE (<a href="#app.output.XXX.strategy">detailed explanation</a>)</td>
<td valign="top">tells the application what to do with each message that is received</td>
</tr>
<tr>
<td valign="top">app.listener.ramp.up.millis</td>
<td valign="top">a number &gt;= 0, e.g. 0 or 500 (in milliseconds)</td>
<td valign="top">the milliseconds pause before the next listener thread is started (makes sense if you start more than one listener)</td>
</tr>
<tr>
<td valign="top">app.sender.ramp.up.millis</td>
<td valign="top">a number &gt;= 0, e.g. 0 or 500 (in milliseconds)</td>
<td valign="top">the milliseconds pause before the next listener thread is started (makes sense if you start more than one sender)</td>
</tr>
<tr>
<td valign="top">javax.jms.message.factory</td>
<td valign="top">de.marcelsauer.jmsloadtester.message.TextMessageFactory or de.marcelsauer.jmsloadtester.message.ByteMessageFactory</td>
<td valign="top">determines which message type will be used, TextMessageFactory -&gt; javax.jms.TextMessage, ByteMessage -&gt; javax.jms.BytesMessage</td>
</tr>
<tr>
<td valign="top">app.message.interceptors</td>
<td valign="top">de.marcelsauer.jmsloadtester.client.Sender which is the default one or a comma separated list. they all need to implement de.marcelsauer.jmsloadtester.message.MessageInterceptor</td>
<td valign="top">each interceptor will be called before the message is sent. they get the message and some other framework instances to alter the message</td>
</tr>
<tr>
<td valign="top">javax.jms.session.handler</td>
<td valign="top">the default one is de.marcelsauer.jmsloadtester.handler.DefaultSessionHandlerImpl</td>
<td valign="top">responsible to create the jms session, gets a connection, sets the acknowledge mode based on &#8220;javax.jms.session.acknowledge.mode&#8221;, should extend de.marcelsauer.jmsloadtester.handler.AbstractThreadAwareSessionHandler which will handle the thread context</td>
</tr>
<tr>
<td valign="top">javax.jms.session.acknowledge.mode</td>
<td valign="top">one of AUTO_ACKNOWLEDGE, CLIENT_ACKNOWLEDGE or DUPS_OK_ACKNOWLEDGE see <a href="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/Session.html#AUTO_ACKNOWLEDGE"title="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/Session.html#AUTO_ACKNOWLEDGE"  onclick="javascript:urchinTracker ('/outbound/article/java.sun.com');">jms spec</a> for details</td>
<td valign="top">will set the ack mode on the session</td>
</tr>
<tr>
<td valign="top">app.listener.explicit.acknowledge.message</td>
<td valign="top">true or false(default)</td>
<td valign="top">will force the call to &#8220;message.acknowledge();&#8221; on receive of a message. you could set this to true if you are using the &#8220;CLIENT_ACKNOWLEDGE&#8221; as javax.jms.session.acknowledge.mode. if you set it to false and use CLIENT_ACKNOWLEDGE then the broker keeps a message until it is consumed and acknowledged. when the app finishes and hasn&#8217;t acknowledged the messages it has received(which &#8220;false&#8221; will do) the broker will recover the messages as the client session has stopped and the broker thinks it has to redeliver the message, which it will the next time a client connects to the destination(queue here).</p>
<p>AUTO_ACKNOWLEDGE and DUPS_OK_ACKNOWLEDGE will autmatically acknowledge the message so &#8220;false&#8221; is fine.</td>
</tr>
<tr>
<td valign="top">javax.jms.delivery.mode</td>
<td valign="top">PERSISTENT or NON_PERSISTENT</td>
<td valign="top"><a href="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/DeliveryMode.html"title="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/DeliveryMode.html"  onclick="javascript:urchinTracker ('/outbound/article/java.sun.com');">http://java.sun.com/j2ee/1.4/docs/api/javax/jms/DeliveryMode.html</a></td>
</tr>
<tr>
<td valign="top">javax.jms.message.producer.time.to.live</td>
<td valign="top">time in milliseconds or &#8220;0&#8243; for messages that will not expire</td>
<td valign="top"><a href="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/MessageProducer.html#setTimeToLive(long)"title="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/MessageProducer.html#setTimeToLive(long)"  onclick="javascript:urchinTracker ('/outbound/article/java.sun.com');">http://java.sun.com/j2ee/1.4/docs/api/javax/jms/MessageProducer.html#setTimeToLive(long)</a></td>
</tr>
<tr>
<td valign="top">javax.jms.message.producer.priority</td>
<td valign="top">0(lowest) - 9(highest)</td>
<td valign="top"><a href="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/MessageProducer.html#setPriority(int)"title="http://java.sun.com/j2ee/1.4/docs/api/javax/jms/MessageProducer.html#setPriority(int)"  onclick="javascript:urchinTracker ('/outbound/article/java.sun.com');">http://java.sun.com/j2ee/1.4/docs/api/javax/jms/MessageProducer.html#setPriority(int)</a></td>
</tr>
</tbody>
</table>




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

// ----- table ....

listeners can receive more than they were waiting for as long as they get the messages they were waiting for based on the configuration.

## creating your own session handler and message interceptors

follow these steps to overwrite the default session handler and call you own one (all the examples are illustrated based on eclipse)
1. create a new eclipse project -> e.g. JmsLoady
2. copy the "conf" and "lib" directories from the jms load tester distribution into the new projects root folder (see image)

// -- picture

3. now open the project settings
- add all the jar files found in the libs directory to the "libraries" tab (see image)
- add the "conf" directory on the "source" tab (see image)

// -- picture

4. now add all your necessary jms api libs to the classpath (like sonic jars, openmq etc.)
5. open the "conf/app.properties" and "conf/jndi.properties" file and change to your appropriate values
6. open the "run as" dialogue (see image)
- double click on "java application", enter "de.marcelsauer.jmsloadtester.Main" as main class in the "Main" tab
- open the "arguments" tab and add the line "-Dapp.properties.file=./conf/app.properties" to the "VM arguments" (see image)
- save and run the app

// -- picture

if everything is running then we can go ahead and actually create our own classes
1. create a new package "samples" in src
2. create a new class "TheSampleSessionHandler.java" which extends "AbstractThreadAwareSessionHandler" (take the sample one from the image)

// -- picture

3. create two new classes "TheSampleInterceptor1" and "TheSampleInterceptor2" which implement "MessageInterceptor" (take the sample one from the image)

// -- picture

4. now open the "conf/app.properties" and add/change the values (see image)

// -- picture

5. build and run the application again
6. check the console window for the "system.out.println" lines (see image) which prove that our new classes are used

you should now have an idea of how these "pluggable" features work and create some real stuff
the rest is up to you :)
feel free to contact me if something is wrong or unclear or you know a better way to do things.




























