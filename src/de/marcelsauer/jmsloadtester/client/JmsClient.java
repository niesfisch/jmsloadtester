package de.marcelsauer.jmsloadtester.client;

import javax.jms.ConnectionFactory;

import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.handler.ConnectionHandler;
import de.marcelsauer.jmsloadtester.handler.ConnectionHandlerImpl;
import de.marcelsauer.jmsloadtester.handler.DestinationHandlerImpl;
import de.marcelsauer.jmsloadtester.handler.MessageHandler;
import de.marcelsauer.jmsloadtester.handler.MessageHandlerImpl;
import de.marcelsauer.jmsloadtester.handler.SessionHandler;
import de.marcelsauer.jmsloadtester.handler.SessionHandlerImpl;
import de.marcelsauer.jmsloadtester.message.MessageFactory;

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
public abstract class JmsClient implements Runnable {
    
    private ConnectionFactory       connectionFactory;
    private DestinationHandlerImpl  destinationHandler;
    private MessageFactory          messageFactory;
    private Config                  config;
    
    protected MessageHandler getMessageHandler(){
        // connection handler
        ConnectionHandler connectionHandler = new ConnectionHandlerImpl(getConnectionFactory());
        
        // session handler
        SessionHandler sessionHandler = new SessionHandlerImpl(connectionHandler);
        
        // message handler
        MessageHandlerImpl messageHandler = new MessageHandlerImpl();
        messageHandler.setDestinationHandler(getDestinationHandler());
        messageHandler.setSessionHandler(sessionHandler);
        messageHandler.setMessageFactory(getMessageFactory());
        
        return messageHandler;
    }
    
	public String getName() {
		return this.toString();
	}

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDestinationHandler(DestinationHandlerImpl destinationHandler) {
        this.destinationHandler = destinationHandler;
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }
    
    public void setConfig(Config config) {
        this.config = config;
    }

    private MessageFactory getMessageFactory() {
        return messageFactory;
    }

    private DestinationHandlerImpl getDestinationHandler() {
        return destinationHandler;
    }
    
    private ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
    
    protected Config getConfig() {
        return config;
    }
}
