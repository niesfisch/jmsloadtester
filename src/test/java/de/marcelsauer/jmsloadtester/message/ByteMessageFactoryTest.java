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

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;

public class ByteMessageFactoryTest extends AbstractJmsLoaderTest {

    private MessageFactory factory;
    private Session mockSession;
    private BytesMessage mockByteMessage;

    @Before
    public void setUp() throws Exception {

        factory = new ByteMessageFactory();
        mockSession = createMockOfType(Session.class);
        mockByteMessage = createMockOfType(BytesMessage.class);
    }

    @Test
    public void testFactoryBehaviour() throws JMSException {
        final byte[] bytes = new byte[3];
        expect(mockSession.createBytesMessage()).andReturn(mockByteMessage);
        mockByteMessage.writeBytes(bytes);
        replay();
        Message byteMessage = factory.toMessage(new Payload(bytes), mockSession);
        assertTrue(byteMessage instanceof BytesMessage);
        verify();
    }
}
