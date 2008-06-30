package de.marcelsauer.jmsloadtester.config;

import java.util.Properties;

import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;


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
public interface Config {
    void loadConfig(Properties config);
    void loadConfig(String file);
    
    int getSubscribersToStart();
    int getSendersToStart();
    int getMessagesToSend();
    int getExpectedMessageSentCount();
    int getPubSleepMillis();
    int getSubscriberWaitForTotalMessages();
    int getEachSubscriberWaitFor();
    int getPauseBetweenPrintProgress();
	int getListenerRampup();
	int getSenderRampup();
    
	boolean doCreateDestinationIfNotExistent();
    
    String getConnectionFactory();
    String getListenToDestination();
    String getSendToDestination();
    
    OutputStrategy getDebugOutputStrategy();
    OutputStrategy getResultOutputStrategy();
    OutputStrategy getMessageOutputStrategy();
  
    MessageContentStrategy getMessageContentStrategy();
}
