package de.marcelsauer.jmsloadtester.client;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.handler.MessageHandler;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.ThreadTools;

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
public class Sender extends JmsClient implements MessageInterceptor {

    private int sleepMilliseconds;
    private int messagesSent;
    private String destination;
    private MessageContentStrategy messageContentStrategy;
    private List<MessageSentAware> messageSentAware = new ArrayList<MessageSentAware>();

    public void run() {
        MessageHandler messageHandler = getMessageHandler();
        messageHandler.addMessageInterceptor(this);
        messageHandler.addMessageSentAware(messageSentAware);
        try {
            MessageContentStrategy messages = getMessageContentStrategy();
            for (String message : messages) {
                Logger.debug("sending message: " + message);
                messageHandler.sendMessage(message, getDestination());
                messagesSent++;
                try {
                    Thread.sleep(getSleepMilliseconds());
                } catch (InterruptedException e) {
                    Logger.error("could not sleep", e);
                }
            }
            Logger.debug("done sending");
        } catch (Exception e) {
            throw new JmsException("problems while sending message", e);
        }
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public void setSleepMilliseconds(final int sleepMilliseconds) {
        this.sleepMilliseconds = sleepMilliseconds;
    }

    public Message intercept(final Message message) {
        try {
            message.setObjectProperty("currentThreadName", ThreadTools.getCurrentThreadName());
            message.setObjectProperty("messagesSent", messagesSent + 1);
        } catch (JMSException e) {
            throw new JmsException("could not intercept outgoing message", e);
        }
        return message;
    }

    public void addMessageSentAware(final MessageSentAware messageSentAware) {
        this.messageSentAware.add(messageSentAware);
    }

    public void setMessageContentStrategy(MessageContentStrategy messageContentStrategy) {
        this.messageContentStrategy = messageContentStrategy;
    }

    private MessageContentStrategy getMessageContentStrategy() {
        return messageContentStrategy;
    }

    private String getDestination() {
        return destination;
    }

    private int getSleepMilliseconds() {
        return sleepMilliseconds;
    }
}
