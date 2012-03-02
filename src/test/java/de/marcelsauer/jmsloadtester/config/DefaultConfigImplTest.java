package de.marcelsauer.jmsloadtester.config;

import static org.easymock.EasyMock.expect;
import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategy;
import de.marcelsauer.jmsloadtester.message.MessageContentStrategyFactory;
import de.marcelsauer.jmsloadtester.message.StaticMessageContentStrategy;
import de.marcelsauer.jmsloadtester.output.FileOutputStrategy;
import de.marcelsauer.jmsloadtester.output.StderrOutputStrategy;
import de.marcelsauer.jmsloadtester.output.StdoutOutputStrategy;

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
public class DefaultConfigImplTest extends AbstractJmsLoaderTest {

    private static final String configFile = "unittest.app.properties";
    private Config config;
    private MessageContentStrategyFactory messageContentStrategyFactoryMock;
    private MessageContentStrategy staticStrategyMock;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        messageContentStrategyFactoryMock = createMockOfType(MessageContentStrategyFactory.class);
        staticStrategyMock = createMockOfType(StaticMessageContentStrategy.class);

        expect(messageContentStrategyFactoryMock.getMessageContentStrategy("STATIC#100#the static test message:random: :datetime: :nanotime:")).andReturn(staticStrategyMock).anyTimes();
        expect(staticStrategyMock.getMessageCount()).andReturn(100);

        replay();

        config = new DefaultConfigImpl(configFile, messageContentStrategyFactoryMock);
    }

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
