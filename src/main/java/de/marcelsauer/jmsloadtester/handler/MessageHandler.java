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
package de.marcelsauer.jmsloadtester.handler;

import de.marcelsauer.jmsloadtester.message.JmsMessage;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;
import de.marcelsauer.jmsloadtester.message.Payload;

import javax.jms.DeliveryMode;
import javax.jms.MessageListener;
import java.util.Collection;
import java.util.List;

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
