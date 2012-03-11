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
package de.marcelsauer.jmsloadtester.client;

import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.handler.MessageHandler;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;
import de.marcelsauer.jmsloadtester.message.Payload;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.ThreadTools;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

public class Sender extends JmsClient implements MessageInterceptor {

    private int sleepMilliseconds;
    private int messagesSent;
    private String destination;
    private MessageContentStrategy messageContentStrategy;
    private List<MessageSentAware> messageSentAware = new ArrayList<MessageSentAware>();
    private List<MessageInterceptor> messageInterceptors = new ArrayList<MessageInterceptor>();

    public void run() {
        final MessageHandler messageHandler = getMessageHandler();
        messageHandler.addMessageInterceptors(getMessageInterceptors());
        messageHandler.addMessageSentAware(messageSentAware);
        try {
            MessageContentStrategy messages = getMessageContentStrategy();
            for (Payload message : messages) {
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

    public void intercept(final Message message, final ThreadTracker threadTracker, final MessageTracker messageTracker) throws JMSException {
        message.setObjectProperty("currentThreadName", ThreadTools.getCurrentThreadName());
        message.setObjectProperty("threadMessagesSent", messagesSent + 1);
        message.setObjectProperty("totalMessagesSent", messageTracker.getTotalMessagesSent() + 1);
    }

    public void addMessageSentAware(final MessageSentAware messageSentAware) {
        this.messageSentAware.add(messageSentAware);
    }

    public void setMessageContentStrategy(final MessageContentStrategy messageContentStrategy) {
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

    private List<MessageInterceptor> getMessageInterceptors() {
        return messageInterceptors;
    }

    public void setMessageInterceptors(List<MessageInterceptor> messageInterceptors) {
        this.messageInterceptors = messageInterceptors;
    }
}
