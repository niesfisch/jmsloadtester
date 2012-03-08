package de.marcelsauer.jmsloadtester.handler;

import de.marcelsauer.jmsloadtester.message.JmsMessage;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;
import de.marcelsauer.jmsloadtester.message.Payload;

import javax.jms.DeliveryMode;
import javax.jms.MessageListener;
import java.util.Collection;
import java.util.List;

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
public interface MessageHandler {
    void sendMessage(JmsMessage message);

    void sendMessage(Payload message, String destination);

    void attachMessageListener(String destination, MessageListener listener);

    void addMessageInterceptor(MessageInterceptor interceptor);

    void addMessageInterceptors(Collection<MessageInterceptor> interceptors);

    void addMessageSentAware(MessageSentAware sentAware);

    void addMessageSentAware(List<MessageSentAware> sentAwares);

    JmsMessage getMessage(Payload text, String destination);


    static enum DELIVERY_MODE {
        PERSISTENT(DeliveryMode.PERSISTENT), NON_PERSISTENT(DeliveryMode.NON_PERSISTENT),;
        int mode = -1;

        DELIVERY_MODE(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }
    }
}
