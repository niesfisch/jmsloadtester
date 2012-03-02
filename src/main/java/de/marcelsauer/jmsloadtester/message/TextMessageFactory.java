package de.marcelsauer.jmsloadtester.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import de.marcelsauer.jmsloadtester.core.JmsException;

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
public class TextMessageFactory implements MessageFactory {

    public Message toMessage(final Payload payload, final Session session) {
        try {
            return createMessage(payload.asString(), session);
        } catch (JMSException e) {
            throw new JmsException(e);
        }
    }

    private TextMessage createMessage(final String text, final Session session) throws JMSException {
        return session.createTextMessage(text);
    }
}
