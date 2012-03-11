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

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategyFactory;
import de.marcelsauer.jmsloadtester.message.StaticMessageContentStrategy;
import de.marcelsauer.jmsloadtester.output.FileOutputStrategy;
import de.marcelsauer.jmsloadtester.output.StderrOutputStrategy;
import de.marcelsauer.jmsloadtester.output.StdoutOutputStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;

public class DefaultConfigImplTest extends AbstractJmsLoaderTest {

    private static final String configFile = "unittest.app.properties";
    private Config config;

    @Before
    public void setUp() throws Exception {
        MessageContentStrategyFactory messageContentStrategyFactoryMock = createMockOfType(MessageContentStrategyFactory.class);
        MessageContentStrategy staticStrategyMock = createMockOfType(StaticMessageContentStrategy.class);

        expect(messageContentStrategyFactoryMock.getMessageContentStrategy("STATIC#100#the static test message:random: :datetime: :nanotime:")).andReturn(staticStrategyMock).anyTimes();
        expect(staticStrategyMock.getMessageCount()).andReturn(100);

        replay();

        config = new DefaultConfigImpl(configFile, messageContentStrategyFactoryMock);
    }

    @Test
    public void test() {

        assertTrue(config.getConnectionFactory().equals("QueueConnectionFactory"));
        assertTrue(config.getSubscribersToStart() == 1);
        assertTrue(config.getListenerRampup() == 2);
        assertTrue(config.getSubscriberWaitForTotalMessages() == 3 * 1);
        assertTrue(config.getListenToDestination().equals("4"));

        assertTrue(config.getSendersToStart() == 5);
        assertTrue(config.getSenderRampup() == 6);
        assertTrue(config.getSendToDestination().equals("7"));
        assertTrue(config.getPubSleepMillis() == 8);
        assertTrue(config.getExpectedMessageSentCount() == 5 * 100);
        assertTrue(config.getMessagesToSend() == 100);

        assertTrue(config.getPauseBetweenPrintProgress() == 9000);

        assertTrue(config.getMessageContentStrategy() instanceof StaticMessageContentStrategy);

        assertTrue(config.getDebugOutputStrategy() instanceof StdoutOutputStrategy);
        assertTrue(config.getResultOutputStrategy() instanceof StderrOutputStrategy);
        assertTrue(config.getMessageOutputStrategy() instanceof FileOutputStrategy);

        assertTrue(config.getMessageInterceptors().size() == 1);
        assertTrue(config.isExplicitAcknowledgeMessage());
        assertTrue(config.getEachSubscriberWaitFor() == 3);
    }
}
