package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.core.JmsException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

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
public class ByteMessageFactory implements MessageFactory {
    @Override
    public Message toMessage(final Payload payload, final Session session) {
        try {
            return createMessage(payload.asBytes(), session);
        } catch (JMSException e) {
            throw new JmsException(e);
        }
    }

    private BytesMessage createMessage(final byte[] bytes, final Session session) throws JMSException {
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(bytes);
        return message;
    }

}
