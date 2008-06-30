package de.marcelsauer.jmsloadtester.tracker;

import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;

import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.tools.Logger;

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

    public synchronized int getTotalMessagesReceived() {
        return totalMessagesReceived;
    }

    public synchronized Set<String> getMessagesReceived() {
        return messagesReceived;
    }

    public synchronized Set<String> getMessagesSent() {
        return messagesSent;
    }

    public synchronized int getTotalMessagesSent() {
        return totalMessagesSent;
    }

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

    public synchronized void setTotalMessagesToBeSent(int totalMessagesToBeSent) {
        this.totalMessagesToBeSent = totalMessagesToBeSent;
    }

    public synchronized void setTotalMessagesToBeReceived(int totalMessagesToBeReceived) {
        this.totalMessagesToBeReceived = totalMessagesToBeReceived;
    }

    public void setSenderTimeTracker(TimeTracker senderTimeTracker) {
        this.senderTimeTracker = senderTimeTracker;
    }

    public void setListenerTimeTracker(TimeTracker listenerTimeTracker) {
        this.listenerTimeTracker = listenerTimeTracker;
    }

    public synchronized boolean isAllReceived() {
        return getTotalMessagesReceived() >= getTotalMessagesToBeReceived();
    }

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
