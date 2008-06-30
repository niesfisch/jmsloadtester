package de.marcelsauer.jmsloadtester.handler;

import java.util.List;

import javax.jms.MessageListener;

import de.marcelsauer.jmsloadtester.message.JmsMessage;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;

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
public interface MessageHandler {
    void sendMessage(JmsMessage message);
    void sendMessage(String text, String destination);
    void attachMessageListener(String destination, MessageListener listener);
    void addMessageInterceptor(MessageInterceptor interceptor);
    void addMessageSentAware(MessageSentAware sentAware);
    void addMessageSentAware(List<MessageSentAware> sentAwares);
    JmsMessage getMessage(String text, String destination);
}
