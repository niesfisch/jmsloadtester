/**
 * Copyright (C) 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.marcelsauer.jmsloadtester.handler;

import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.message.*;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageHandlerImpl implements MessageHandler {

    private SessionHandler sessionHandler;
    private DestinationHandler destinationHandler;
    private MessageFactory messageProducer;
    private List<MessageInterceptor> interceptors = new ArrayList<MessageInterceptor>();
    private List<MessageSentAware> sentAwares = new ArrayList<MessageSentAware>();
    // one producer per Thread
    private ThreadLocal<MessageProducer> producer = new ThreadLocal<MessageProducer>();
    private MessageTracker messageTracker;
    private ThreadTracker threadTracker;
    private ConnectionHandler connectionHandler;
    private Config config;

    @Override
    public void sendMessage(final JmsMessage message) {
        Message msg = getMessageFactory().toMessage(message.getMessage(), getSession());
        callMessageInterceptors(msg);
        MessageProducer producer = getProducer(message.getDestination());
        try {
            producer.send(msg);
        } catch (JMSException e) {
            throw new JmsException("could not send message", e);
        }
        informMessageSentAware(msg);
    }

    @Override
    public void attachMessageListener(final String destination, final MessageListener listener) {
        try {
            getConsumer(destination).setMessageListener(listener);
        } catch (JMSException e) {
            throw new JmsException("could not attach message listener to destination " + destination, e);
        }
    }

    @Override
    public JmsMessage getMessage(final Payload message, final String destination) {
        return new JmsMessage(message, destination);
    }

    public void setSessionHandler(final SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public void setDestinationHandler(final DestinationHandler destinationHandler) {
        this.destinationHandler = destinationHandler;
    }

    @Override
    public void addMessageInterceptor(final MessageInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    @Override
    public void addMessageSentAware(final MessageSentAware sentAware) {
        this.sentAwares.add(sentAware);
    }

    @Override
    public void addMessageSentAware(final List<MessageSentAware> sentAwares) {
        this.sentAwares.addAll(sentAwares);
    }

    public void setMessageFactory(final MessageFactory messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void sendMessage(final Payload message, final String destination) {
        sendMessage(getMessage(message, destination));
    }

    @Override
    public void addMessageInterceptors(Collection<MessageInterceptor> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void setMessageTracker(MessageTracker messageTracker) {
        this.messageTracker = messageTracker;
    }

    public void setThreadTracker(ThreadTracker threadTracker) {
        this.threadTracker = threadTracker;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    private ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    private ThreadTracker getThreadTracker() {
        return threadTracker;
    }

    private DestinationHandler getDestinationHandler() {
        return destinationHandler;
    }

    private MessageProducer getProducer(final String destination) {
        MessageProducer messageProducer = producer.get();
        if (messageProducer == null) {
            try {
                messageProducer = getSession().createProducer(getDestinationHandler().getDestination(destination));
                messageProducer.setDeliveryMode(DELIVERY_MODE.valueOf(config.getDeliveryMode()).getMode());
                messageProducer.setPriority(config.getPriority());
                // millis
                messageProducer.setTimeToLive(config.getTimeToLive());
                producer.set(messageProducer);
                Logger.debug("returning newly created MessageProducer: [" + messageProducer + "]");
            } catch (JMSException e) {
                throw new JmsException("could not create message producer", e);
            }
        } else {
            Logger.debug("returning cached MessageProducer: [" + producer.get() + "]");
        }
        return producer.get();
    }

    private Session getSession() {
        String username = getConfig().getConnectionUsername();
        String password = getConfig().getConnectionPassword();
        Connection connection = getConnectionHandler().getConnection(username, password);
        return getSessionHandler().getSession(connection, getConfig());
    }

    private void informMessageSentAware(final Message message) {
        for (MessageSentAware aware : sentAwares) {
            aware.messageSent(message);
        }
    }

    private void callMessageInterceptors(final Message message) {
        for (MessageInterceptor interceptor : interceptors) {
            try {
                Logger.debug("calling interceptor [" + interceptor + "] on message");
                interceptor.intercept(message, getThreadTracker(), getMessageTracker());
            } catch (JMSException e) {
                throw new JmsException("could not intercept message with interceptor " + interceptor);
            }
        }
    }

    private MessageFactory getMessageFactory() {
        return messageProducer;
    }

    private SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    private MessageConsumer getConsumer(final String destination) {
        MessageConsumer consumer;
        try {
            consumer = getSession().createConsumer(getDestinationHandler().getDestination(destination));
        } catch (JMSException e) {
            throw new JmsException("could not create message consumer on destination " + destination, e);
        }
        return consumer;
    }

    private MessageTracker getMessageTracker() {
        return messageTracker;
    }

    private Config getConfig() {
        return config;
    }
}
