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
package de.marcelsauer.jmsloadtester.config;

import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;

import java.util.List;
import java.util.Properties;

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

    boolean isExplicitAcknowledgeMessage();

    String getConnectionUsername();

    String getConnectionPassword();

    String getConnectionFactory();

    String getListenToDestination();

    String getSendToDestination();

    OutputStrategy getDebugOutputStrategy();

    OutputStrategy getResultOutputStrategy();

    OutputStrategy getMessageOutputStrategy();

    MessageContentStrategy getMessageContentStrategy();

    List<MessageInterceptor> getMessageInterceptors();

    String getDeliveryMode();

    int getPriority();

    long getTimeToLive();

}
