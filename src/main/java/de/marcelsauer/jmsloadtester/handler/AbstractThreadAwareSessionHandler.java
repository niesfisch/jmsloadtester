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

import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.tools.Logger;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public abstract class AbstractThreadAwareSessionHandler implements SessionHandler {

    private ACK_MODE ackMode;

    // one session per Thread
    private static ThreadLocal<Session> sess = new ThreadLocal<Session>();

    public AbstractThreadAwareSessionHandler(final String ackMode) {
        this.ackMode = ACK_MODE.valueOf(ackMode);
    }

    protected abstract Session getThreadSession(final Connection connection, Config config) throws JMSException;

    @Override
    public final Session getSession(final Connection connection, final Config config) {
        if (sess.get() == null) {
            try {
                sess.set(getThreadSession(connection, config));
                Logger.debug("returning newly created session: [" + sess.get() + "] for this thread");
            } catch (JMSException e) {
                throw new JmsException("could not create session", e);
            }
        } else {
            Logger.debug("returning cached session: [" + sess.get() + "] for this thread");
        }
        return sess.get();
    }

    protected ACK_MODE getAckMode() {
        return ackMode;
    }
}
