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

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * <p/>
 * This file is part of JMS Load Tester.
 * <p/>
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p/>
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
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
