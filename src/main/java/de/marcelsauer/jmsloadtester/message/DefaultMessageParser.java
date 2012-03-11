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

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.tools.Logger;

import javax.jms.*;
import java.util.Enumeration;

public class DefaultMessageParser implements MessageParser {
    @Override
    public String getSummary(final Message message) {
        StringBuffer sb = new StringBuffer();
        try {
            // all other if-else
            sb.append("          content: " + Constants.EOL);
            sb.append(get(message) + Constants.EOL);

            extractProperties(message, sb);
            extractHeaders(message, sb);
        } catch (JMSException e) {
            Logger.error("could not extract headers and properties for message summary", e);
        }
        return sb.toString();
    }

    private void extractHeaders(final Message message, final StringBuffer sb) throws JMSException {
        add("JMSDestination", message.getJMSDestination(), sb);
        add("getJMSDeliveryMode", message.getJMSDeliveryMode(), sb);
        add("JMSMessageID", message.getJMSMessageID(), sb);
        add("JMSTimestamp", message.getJMSTimestamp(), sb);
        add("JMSCorrelationID", message.getJMSCorrelationID(), sb);
        add("JMSReplyTo", message.getJMSReplyTo(), sb);
        add("JMSRedelivered", message.getJMSRedelivered(), sb);
        add("JMSType", message.getJMSType(), sb);
        add("JMSExpiration", message.getJMSExpiration(), sb);
        add("JMSPriority", message.getJMSPriority(), sb);
    }

    private void add(final String key, final Object value, final StringBuffer sb) {
        sb.append("          " + key + ": " + value + Constants.EOL);
    }

    private void extractProperties(final Message message, final StringBuffer sb) throws JMSException {
        Enumeration<?> propNames = message.getPropertyNames();
        while (propNames.hasMoreElements()) {
            String key = (String) propNames.nextElement();
            String value = message.getObjectProperty(key).toString();
            sb.append("          property [" + key + "] with value [" + value + "]" + Constants.EOL);
        }
    }

    private String get(final Message message) throws JMSException {
        String content = "";
        if (message instanceof TextMessage) {
            content = getPayload((TextMessage) message);
        }
        // all the other if-else
        return content;
    }

    private String getPayload(final TextMessage message) throws JMSException {
        return message.getText();
    }

    private String getPayload(final BytesMessage message) {
        return null;
    }

    private String getPayload(final ObjectMessage message) {
        return null;
    }

}
