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
package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;

public class TextMessageFactoryTest extends AbstractJmsLoaderTest {

    private MessageFactory factory;
    private Session mockSession;
    private TextMessage mockTextMessage;

    @Before
    public void setUp() throws Exception {

        factory = new TextMessageFactory();
        mockSession = createMockOfType(Session.class);
        mockTextMessage = createMockOfType(TextMessage.class);
    }

    @Test
    public void testFactoryBehaviour() throws JMSException {
        expect(mockSession.createTextMessage("the message")).andReturn(mockTextMessage);
        replay();
        Message textMessage = factory.toMessage(new Payload("the message"), mockSession);
        assertTrue(textMessage instanceof TextMessage);
        verify();
    }
}
