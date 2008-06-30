package de.marcelsauer.jmsloadtester.config;

import java.util.Properties;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategyFactory;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;
import de.marcelsauer.jmsloadtester.output.OutputStrategyFactory;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.PropertyUtils;
import de.marcelsauer.jmsloadtester.tools.StringUtils;


/**
 *   JMS Load Tester
 *   Copyright (C) 2008 Marcel Sauer <marcel[underscore]sauer[at]gmx.de>
 *   
 *   This file is part of JMS Load Tester.
 *
 *   JMS Load Tester is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JMS Load Tester is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public class DefaultConfigImpl implements Config {
	
    private MessageContentStrategyFactory messageContentStrategyFactory;
    
    // propery keys
    private static final String APP_PREFIX = "app.";
    
    private static final String SUBSCRIBERS_TO_START        = APP_PREFIX + "listener.thread.count";
    private static final String PUBLISHERS_TO_START         = APP_PREFIX + "sender.threads.to.start";
    private static final String SUBSCRIBER_WAIT_FOR         = APP_PREFIX + "listener.wait.for.message.count";
    private static final String MESSAGE_CONTENT_STRATEGY    = APP_PREFIX + "sender.message.content.strategy";
    private static final String LISTEN_TO_DEST              = APP_PREFIX + "listener.listen.to.destination";
    private static final String SEND_TO_DEST                = APP_PREFIX + "sender.send.to.destination";
    private static final String PUB_SLEEP                   = APP_PREFIX + "sender.pause.millis.between.send";
    private static final String DEBUG_OUT_STRATEGY          = APP_PREFIX + "output.debug.strategy";
    private static final String RESULT_OUT_STRATEGY         = APP_PREFIX + "output.result.strategy";
    private static final String MESSAGE_OUT_STRATEGY        = APP_PREFIX + "output.message.strategy";
    private static final String PAUSE_PROGRESS              = APP_PREFIX + "output.pause.seconds.between.printing.progress";
    private static final String LISTENER_RAMPUP             = APP_PREFIX + "listener.ramp.up.millis";
    private static final String SENDER_RAMPUP               = APP_PREFIX + "sender.ramp.up.millis";
    
    // connection factory
    private static final String CONNECTION_FACTORY          = "javax.jms.ConnectionFactory";
    
    // set via config file
    private static int subscribersToStart;
    private static int publishersToStart;
    private static int messagesToSend;
    private static int pubSleepMillis;
    private static int subscriberWaitFor;
    private static int eachSubscriberWaitFor;
    private static int pauseBetweenPrintProgress;
    private static int listenerRampup;
    private static int senderRampup;
    private static int expectedMessageSentCount;
    
    private static boolean createJndiDestinationIfNotFound;
    
    private static String connectionFactory;
    private static String listenToDestination;
    private static String sendToDestination;
    private static String messageContentStrategy;
    
    private static OutputStrategy debugOutputStrategy;
    private static OutputStrategy resultOutputStrategy;
    private static OutputStrategy messageOutputStrategy;
    
    public DefaultConfigImpl(Properties applicationProperties, MessageContentStrategyFactory messageContentStrategyFactory){
        this.messageContentStrategyFactory = messageContentStrategyFactory;
        loadConfig(applicationProperties);
    }
    
    public DefaultConfigImpl(String filename, MessageContentStrategyFactory messageContentStrategyFactory){
        this.messageContentStrategyFactory = messageContentStrategyFactory;
        loadConfig(filename);
    }
    
    public void loadConfig(final String filename){
        Logger.info("using config file: " + filename);
        Properties config = PropertyUtils.loadProperties(filename);
        loadConfig(config);
    }
    
    public void loadConfig(final Properties config){
        Logger.info("using properties: " + config);
        try {
            initValues(config);
        } catch (NumberFormatException e){
            Logger.error("could not load all the values from the properties file. did you provide all values with valid values?", e);
        }
    }
    
    private void initValues(final Properties properties){
        try {
            subscribersToStart     	  = getIntValue(properties.get(SUBSCRIBERS_TO_START));
            publishersToStart      	  = getIntValue(properties.get(PUBLISHERS_TO_START));
            pubSleepMillis         	  = getIntValue(properties.get(PUB_SLEEP));
            eachSubscriberWaitFor     = getIntValue(properties.get(SUBSCRIBER_WAIT_FOR));
            pauseBetweenPrintProgress = getIntValue(properties.get(PAUSE_PROGRESS)) * Constants.MILLIS_FACTOR;
            listenerRampup            = getIntValue(properties.get(LISTENER_RAMPUP));
            senderRampup              = getIntValue(properties.get(SENDER_RAMPUP));
            
            subscriberWaitFor         = eachSubscriberWaitFor * subscribersToStart;
            
            connectionFactory      = getStringValue(properties.get(CONNECTION_FACTORY));
            
            listenToDestination    = getStringValue(properties.get(LISTEN_TO_DEST));
            sendToDestination      = getStringValue(properties.get(SEND_TO_DEST));
            
            debugOutputStrategy    = OutputStrategyFactory.getOutputStrategy(getStringValue(properties.get(DEBUG_OUT_STRATEGY)));
            resultOutputStrategy   = OutputStrategyFactory.getOutputStrategy(getStringValue(properties.get(RESULT_OUT_STRATEGY)));
            messageOutputStrategy  = OutputStrategyFactory.getOutputStrategy(getStringValue(properties.get(MESSAGE_OUT_STRATEGY)));
            
            messageContentStrategy = getStringValue(properties.get(MESSAGE_CONTENT_STRATEGY));
            
            setMessagesToSend(getMessageContentStrategy().getMessageCount());
        } catch (IllegalStateException e){
            Logger.error("the configuration file seems to be incorrect", e);
        }
    }
    
    private void calculateExpectedMessageCounts(){
        expectedMessageSentCount = publishersToStart * messagesToSend;
    }
    
    private void check(final Object value){
        if(StringUtils.isEmpty(value)){
            throw new IllegalStateException("the configuration value [" + value + "] is wrong. please set a correct one or comment the whole line");
        }
    }
    
    private boolean getBooleanValue(final Object value) {
        check(value);
        return Boolean.valueOf((String) value);
    }

    private int getIntValue(final Object value) {
        check(value);
        return Integer.valueOf((String) value);
    }

    private String getStringValue(final Object value) {
        check(value);
        return String.valueOf(value);
    }

    public int getSubscribersToStart() {
        return subscribersToStart;
    }

    public int getSendersToStart() {
        return publishersToStart;
    }

    public int getMessagesToSend() {
        return messagesToSend;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

    // TODO move this somewhere else?
    // we always create a new one
    public MessageContentStrategy getMessageContentStrategy() {
        return messageContentStrategyFactory.getMessageContentStrategy(getStringValue(messageContentStrategy));
    }

    public String getListenToDestination() {
        return listenToDestination;
    }

    public String getSendToDestination() {
        return sendToDestination;
    }

    public int getPubSleepMillis() {
        return pubSleepMillis;
    }

    public OutputStrategy getDebugOutputStrategy() {
        return debugOutputStrategy;
    }

    public OutputStrategy getResultOutputStrategy() {
        return resultOutputStrategy;
    }

    public OutputStrategy getMessageOutputStrategy() {
        return messageOutputStrategy;
    }

    public int getExpectedMessageSentCount() {
        return expectedMessageSentCount;
    }

    public boolean doCreateDestinationIfNotExistent() {
        return createJndiDestinationIfNotFound;
    }

    public int getSubscriberWaitForTotalMessages() {
        return subscriberWaitFor;
    }

    public int getEachSubscriberWaitFor() {
        return eachSubscriberWaitFor;
    }

    public int getPauseBetweenPrintProgress() {
        return pauseBetweenPrintProgress;
    }

    public int getListenerRampup() {
        return listenerRampup;
    }

    public int getSenderRampup() {
        return senderRampup;
    }

    public void setMessageContentStrategyFactory(MessageContentStrategyFactory messageContentStrategyFactory) {
        this.messageContentStrategyFactory = messageContentStrategyFactory;
    }

    private void setMessagesToSend(final int messagesToSend) {
        DefaultConfigImpl.messagesToSend = messagesToSend;
        calculateExpectedMessageCounts();
    }
}
