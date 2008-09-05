package de.marcelsauer.jmsloadtester.handler;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.tools.Logger;

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
public abstract class AbstractThreadAwareSessionHandler implements SessionHandler {

    private String ackMode;
    
    // one session per Thread
    private static ThreadLocal<Session> sess = new ThreadLocal<Session>();

    public AbstractThreadAwareSessionHandler (final String ackMode){
        this.ackMode = ackMode;
    }
    
    abstract Session getThreadSession(final Connection connection, Config config) throws JMSException;

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
    
    protected String getAckMode() {
        return ackMode;
    }
}
