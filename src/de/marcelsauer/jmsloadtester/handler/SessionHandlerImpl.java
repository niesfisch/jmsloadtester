package de.marcelsauer.jmsloadtester.handler;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

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
public class SessionHandlerImpl implements SessionHandler {

    // one session per Thread
    private static ThreadLocal<Session> sess = new ThreadLocal<Session>();
    private ConnectionHandler connectionHandler;
    private ACK_MODE ackMode;

    public SessionHandlerImpl(ConnectionHandler connectionHandler) {
        this(connectionHandler, ACK_MODE.AUTO_ACKNOWLEDGE);
    }

    public SessionHandlerImpl(ConnectionHandler connectionHandler, ACK_MODE ackMode) {
        this.connectionHandler = connectionHandler;
        this.ackMode = ackMode;
    }

    public Session getSession() {
        if (sess.get() == null) {
            try {
                sess.set(getConnection().createSession(false, getAckMode().getMode()));
                Logger.debug("returning newly created session: [" + sess.get() + "] for this thread");
            } catch (JMSException e) {
                throw new JmsException("could not create session", e);
            }
        } else {
            Logger.debug("returning cached session: [" + sess.get() + "] for this thread");
        }
        return sess.get();
    }

    public synchronized void setConnectionHandler(final ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public synchronized void setAckMode(final ACK_MODE ackMode) {
        this.ackMode = ackMode;
    }

    private synchronized ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    private synchronized Connection getConnection() {
        return getConnectionHandler().getConnection();
    }

    private synchronized ACK_MODE getAckMode() {
        return ackMode;
    }

}
