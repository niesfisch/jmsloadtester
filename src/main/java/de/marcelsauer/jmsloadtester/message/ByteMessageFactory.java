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

import de.marcelsauer.jmsloadtester.core.JmsException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

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
