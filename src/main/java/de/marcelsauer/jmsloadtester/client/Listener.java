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
