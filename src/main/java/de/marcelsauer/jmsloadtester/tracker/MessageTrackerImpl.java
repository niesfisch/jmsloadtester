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
package de.marcelsauer.jmsloadtester.tracker;

import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.tools.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.HashSet;
import java.util.Set;

public class MessageTrackerImpl implements MessageTracker {

    private int totalMessagesReceived;
    private int totalMessagesSent;

    private boolean firstMessageSent;
    private boolean firstMessageReceived;

    private int totalMessagesToBeSent;
    private int totalMessagesToBeReceived;

    private TimeTracker senderTimeTracker;
    private TimeTracker listenerTimeTracker;

    private Set<String> messagesReceived = new HashSet<String>();
    private Set<String> messagesSent = new HashSet<String>();

    @Override
    public synchronized void onMessage(final Message message) {
        totalMessagesReceived++;
        try {
            messagesReceived.add(message.getJMSMessageID());
        } catch (JMSException e) {
            throw new JmsException(e);
        } finally {
            handleListenerTimers();
            printReceivedStats();
        }
    }

    @Override
    public synchronized int getTotalMessagesReceived() {
        return totalMessagesReceived;
    }

    @Override
    public synchronized Set<String> getMessagesReceived() {
        return messagesReceived;
    }

    @Override
    public synchronized Set<String> getMessagesSent() {
        return messagesSent;
    }

    @Override
    public synchronized int getTotalMessagesSent() {
        return totalMessagesSent;
    }

    @Override
    public synchronized void messageSent(final Message message) {
        totalMessagesSent++;
        try {
            messagesSent.add(message.getJMSMessageID());
        } catch (JMSException e) {
            throw new JmsException(e);
        } finally {
            handleSenderTimers();
            printSentStats();
        }
    }

    @Override
    public synchronized void setTotalMessagesToBeSent(int totalMessagesToBeSent) {
        this.totalMessagesToBeSent = totalMessagesToBeSent;
    }

    @Override
    public synchronized void setTotalMessagesToBeReceived(int totalMessagesToBeReceived) {
        this.totalMessagesToBeReceived = totalMessagesToBeReceived;
    }

    @Override
    public synchronized void setSenderTimeTracker(final TimeTracker senderTimeTracker) {
        this.senderTimeTracker = senderTimeTracker;
    }

    @Override
    public synchronized void setListenerTimeTracker(final TimeTracker listenerTimeTracker) {
        this.listenerTimeTracker = listenerTimeTracker;
    }

    @Override
    public synchronized boolean isAllReceived() {
        return getTotalMessagesReceived() >= getTotalMessagesToBeReceived();
    }

    @Override
    public synchronized boolean isAllSent() {
        return getTotalMessagesSent() >= getTotalMessagesToBeSent();

    }

    private synchronized void printReceivedStats() {
        StringBuffer sb = new StringBuffer();
        sb.append("MessageTracker was informed of incoming message, ");
        sb.append("total messages received so far " + getTotalMessagesReceived());
        Logger.debug(sb.toString());
    }

    private synchronized void printSentStats() {
        StringBuffer sb = new StringBuffer();
        sb.append("MessageTracker was informed of sent message, ");
        sb.append("total messages sent so far " + getTotalMessagesSent());
        Logger.debug(sb.toString());
    }

    private synchronized int getTotalMessagesToBeSent() {
        return totalMessagesToBeSent;
    }

    private synchronized int getTotalMessagesToBeReceived() {
        return totalMessagesToBeReceived;
    }

    private synchronized void handleSenderTimers() {
        // activate the timer
        if (!firstMessageSent) {
            senderTimeTracker.start();
            firstMessageSent = true;
        }

        if (isAllSent()) {
            senderTimeTracker.stop();
        }
    }

    private synchronized void handleListenerTimers() {
        // activate the timer
        if (!firstMessageReceived) {
            listenerTimeTracker.start();
            firstMessageReceived = true;
        }

        if (isAllReceived()) {
            listenerTimeTracker.stop();
        }
    }

}
