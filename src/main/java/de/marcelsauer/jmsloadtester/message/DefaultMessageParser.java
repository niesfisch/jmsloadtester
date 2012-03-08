package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.tools.Logger;

import javax.jms.*;
import java.util.Enumeration;

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
