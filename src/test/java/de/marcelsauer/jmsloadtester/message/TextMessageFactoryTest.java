package de.marcelsauer.jmsloadtester.message;

import static org.easymock.EasyMock.expect;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;

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
public class TextMessageFactoryTest extends AbstractJmsLoaderTest {

    private MessageFactory factory;
    private Session mockSession;
    private TextMessage mockTextMessage;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        factory = new TextMessageFactory();
        mockSession = createMockOfType(Session.class);
        mockTextMessage = createMockOfType(TextMessage.class);
    }

    public void testFactoryBehaviour() throws JMSException {
        expect(mockSession.createTextMessage("the message")).andReturn(mockTextMessage);
        replay();
        Message textMessage = factory.toMessage(new Payload("the message"), mockSession);
        assertTrue(textMessage instanceof TextMessage);
        verify();
    }
}
