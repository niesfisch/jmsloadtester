package de.marcelsauer.jmsloadtester.client;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.handler.MessageHandler;
import de.marcelsauer.jmsloadtester.message.MessageNotifyable;
import de.marcelsauer.jmsloadtester.message.MessageParser;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.ThreadTools;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * <p/>
 * This file is part of JMS Load Tester.
 * <p/>
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p/>
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public class Listener extends JmsClient {

    private String listenToDestination;
    private OutputStrategy messageOutStrategy;
    private MessageParser messageParser;
    private List<MessageNotifyable> messageNotifyables = new ArrayList<MessageNotifyable>();
    private boolean explicitAckMessage;

    public void run() {
        try {
            MessageHandler messageHandler = getMessageHandler();
            messageHandler.attachMessageListener(getListenToDestination(), new ThreadListener());
        } catch (Exception e) {
            throw new JmsException("could not receive all messages", e);
        }
    }

    /**
     * the message listener
     */
    private class ThreadListener implements MessageListener {
        public void onMessage(Message message) {
            printMessageDetails(message);
            messageReceived(message);
            if (isExplicitAckMessage()) {
                try {
                    Logger.debug("trying to acknowledge message" + message.getJMSMessageID());
                    message.acknowledge();
                    Logger.debug("successfully acknowledged message" + message.getJMSMessageID());
                } catch (JMSException e) {
                    throw new JmsException("could not acknowledge message", e);
                }
            }
        }
    }

    private String getMessageDetails(final Message message) {
        StringBuffer sb = new StringBuffer();
        sb.append("[" + ThreadTools.getCurrentThreadName() + "]" + Constants.EOL);
        sb.append("    Received message: " + Constants.EOL);
        sb.append(messageParser.getSummary(message));
        return sb.toString();
    }

    private void printMessageDetails(final Message message) {
        getMessageOutStrategy().output(getMessageDetails(message));
    }

    private void messageReceived(final Message message) {
        for (MessageNotifyable m : messageNotifyables) {
            m.onMessage(message);
        }
    }

    public void setListenToDestination(final String subscribeToDestination) {
        this.listenToDestination = subscribeToDestination;
    }

    public void addMessageNotifyable(final MessageNotifyable messageNotifyable) {
        this.messageNotifyables.add(messageNotifyable);
    }

    public void setMessageOutStrategy(final OutputStrategy messageOutStrategy) {
        this.messageOutStrategy = messageOutStrategy;
    }

    public void setMessageParser(MessageParser messageParser) {
        this.messageParser = messageParser;
    }

    public void setExplicitAckMessage(boolean explicitAckMessage) {
        this.explicitAckMessage = explicitAckMessage;
    }

    public boolean isExplicitAckMessage() {
        return explicitAckMessage;
    }

    private OutputStrategy getMessageOutStrategy() {
        return messageOutStrategy;
    }

    private String getListenToDestination() {
        return listenToDestination;
    }

}
