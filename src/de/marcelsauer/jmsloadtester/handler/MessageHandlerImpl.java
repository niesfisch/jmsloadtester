package de.marcelsauer.jmsloadtester.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.message.JmsMessage;
import de.marcelsauer.jmsloadtester.message.MessageFactory;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;
import de.marcelsauer.jmsloadtester.message.Payload;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * 
 * This file is part of JMS Load Tester.
 * 
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
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

    public void attachMessageListener(final String destination, final MessageListener listener) {
        try {
            getConsumer(destination).setMessageListener(listener);
        } catch (JMSException e) {
            throw new JmsException("could not attach message listener to destination " + destination, e);
        }
    }

    public JmsMessage getMessage(final Payload message, final String destination) {
        return new JmsMessage(message, destination);
    }

    public void setSessionHandler(final SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public void setDestinationHandler(final DestinationHandler destinationHandler) {
        this.destinationHandler = destinationHandler;
    }

    public void addMessageInterceptor(final MessageInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    public void addMessageSentAware(final MessageSentAware sentAware) {
        this.sentAwares.add(sentAware);
    }

    public void addMessageSentAware(final List<MessageSentAware> sentAwares) {
        this.sentAwares.addAll(sentAwares);
    }

    public void setMessageFactory(final MessageFactory messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void sendMessage(final Payload message, final String destination) {
        sendMessage(getMessage(message, destination));
    }

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
        if (producer.get() == null) {
            try {
                producer.set(getSession().createProducer(getDestinationHandler().getDestination(destination)));
                Logger.debug("returning newly created MessageProducer: [" + producer.get() + "]");
            } catch (JMSException e) {
                throw new JmsException("could not create message producer", e);
            }
        } else {
            Logger.debug("returning cached MessageProducer: [" + producer.get() + "]");
        }
        return producer.get();
    }

    private Session getSession() {
        return getSessionHandler().getSession(getConnectionHandler().getConnection(), getConfig());
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
