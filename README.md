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
open the file **conf/jndi.properties** and use the JNDI provider settings that the app should use to lookup the connection factories and destinations. this is just to make sure the application can connect to a local or remote JNDI repository, nothing more.typical examples can be found in the conf/samples folder. have a look at the JNDI setup instructions of the provider you are using or ask you sysadmins for the connection details. the application is using this information to create the JNDI "InitialContext" and holds this for the rest of the runtime.

e.g. a typical SonicMQ setup

    java.naming.provider.url=tcp://localhost:2506
    java.naming.factory.initial=com.sonicsw.jndi.mfcontext.MFContextFactory
    java.naming.security.principal=Administrator
    java.naming.security.credentials=Administrator

4. open the file **conf/app.properties** and change the value for the property "javax.jms.ConnectionFactory" to the jms connection factory that should be used to create the sessions. this has to be available in the JNDI repository. a typical setup for SonicMQ topic/queue connection factory can be found here. the connection factory can be a topic or queue connection factory as long as it implements the javax.jms.ConnectionFactory interface.

5. close the files and save them

//-- pictures here .....

in order to use the application you have to place certain vendor jar files in the classpath, which are not part of JMS Load Tester itself. when the application is starting it is trying to connect to the JNDI repository you provided in the jndi.properties files. it then tries to get a javax.jms.ConnectionFactory object(instance) via JNDI. to get this working the JRE/JVM needs these provider specific implementations in the classpath. if they are not present you will get the infamous "ClassNotFoundException". consult the documentation of your JMS provider. the selected jar files in the following screenshots give you an idea:

//-- pictures here .....


1. open the file **jmsloadtester.bat** (Windows) or **jmsloadtester.sh** (Un*x) and point the "YOUR_CP" to all the jars that should be included (i.e. the vendor jars we just talked about)
2. look at the example lines starting with "set YOUR_CP=..." (Windows) or "YOUR_CP=..." (Un*x) and use them (delete the "REM" or "#") in front of them
3. save the file and close it
4. look at the more detailed explanation

now open the file **app.properties** again. it’s time to configure the actual load test.

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

save and close all the open files. execute the “jmsloadtester.bat” or “jmsloadtester.sh”. the application should be running, sending to and receiving from the destination you configured.

if everything is working then you can continue with doing some real load testing by changing the settings and "burning down" your JMS broker. Have a look at some useful settings to start with (further down). Try to watch the load on the broker and network in parallel.

to be continued.........

