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
