# What's that for? 

JMS Load Tester is a small open source command line based Java (>1.5) application to load test JMS brokers like SonicMQ, ActiveMQ, OpenJMS and others. It is configured using a properties file and should run under Windows and Un*x systems(i’ve used it with Ubuntu).

# Getting JMS Load Tester

just take the latest zip from the [downloads](http://github.com/niesfisch/jmsloadtester/downloads)

or clone the repository and build from scratch with maven

    git clone git://github.com/niesfisch/jmsloadtester.git
    cd jmsloadtester.git
    mvn clean package
    cd target/dist

the rest is explained [here](http://jmsloadtester.marcel-sauer.de)
