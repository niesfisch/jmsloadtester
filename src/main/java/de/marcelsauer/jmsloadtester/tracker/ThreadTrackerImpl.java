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

import de.marcelsauer.jmsloadtester.client.Listener;
import de.marcelsauer.jmsloadtester.client.Sender;
import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategyWrapper;
import de.marcelsauer.jmsloadtester.message.MessageParser;
import de.marcelsauer.jmsloadtester.message.filter.PlaceHolderContentFilter;
import de.marcelsauer.jmsloadtester.spring.SpringFactory;

public class ThreadTrackerImpl implements ThreadTracker {

    private static int subscribersCreated;
    private static int publishersCreated;
    private MessageTracker messageTracker;
    private MessageParser messageParser;
    private Config config;

    public ThreadTrackerImpl(Config config) {
        this.config = config;
    }

    private MessageContentStrategy getMessageContentStrategy() {
        MessageContentStrategy target = config.getMessageContentStrategy();
        // @todo dodgy, remove this?
        PlaceHolderContentFilter filter = SpringFactory.getBean("placeholderContentFilter");
        // a new one for each thread !
        return new MessageContentStrategyWrapper(target, filter);
    }

    @Override
    public void createListenerThread(final String name) {
        final Listener listener = new Listener();
        listener.setListenToDestination(config.getListenToDestination());
        listener.addMessageNotifyable(messageTracker);
        listener.setMessageOutStrategy(config.getMessageOutputStrategy());
        listener.setMessageParser(messageParser);
        listener.setExplicitAckMessage(config.isExplicitAcknowledgeMessage());

        final Thread subscriberThread = new Thread(listener);
        subscriberThread.setName(name + " [" + listener.hashCode() + "]");
        subscriberThread.start();
        subscribersCreated++;
    }

    @Override
    public void createSenderThread(final String name) {
        final Sender sender = new Sender();
        sender.setDestination(config.getSendToDestination());
        sender.setSleepMilliseconds(config.getPubSleepMillis());
        sender.addMessageSentAware(messageTracker);
        sender.setMessageContentStrategy(getMessageContentStrategy());
        sender.setMessageInterceptors(config.getMessageInterceptors());

        final Thread senderThread = new Thread(sender);
        senderThread.setName(name + " [" + sender.hashCode() + "]");
        senderThread.start();
        publishersCreated++;
    }

    public synchronized int subscriberCreated() {
        return subscribersCreated++;
    }

    public synchronized int publisherCreated() {
        return publishersCreated++;
    }

    @Override
    public synchronized int getListenersStarted() {
        return subscribersCreated;
    }

    @Override
    public synchronized int getSendersStarted() {
        return publishersCreated;
    }

    public void setMessageTracker(final MessageTracker messageTracker) {
        this.messageTracker = messageTracker;
    }

    public void setMessageParser(final MessageParser messageParser) {
        this.messageParser = messageParser;
    }
}
