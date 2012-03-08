package de.marcelsauer.jmsloadtester.handler;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.core.ShutdownAware;
import de.marcelsauer.jmsloadtester.tools.JmsUtils;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;

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
public class ConnectionHandlerImpl implements ConnectionHandler, ShutdownAware {

    private static Connection con;
    private ConnectionFactory connectionFactory;

    private ConnectionHandlerImpl() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHandler(this));
    }

    public ConnectionHandlerImpl(ConnectionFactory connectionFactory) {
        this();
        this.connectionFactory = connectionFactory;
    }

    @Override
    public synchronized Connection getConnection(String username, String password) {
        if (con == null) {
            try {
                if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                    con = getConnectionFactory().createConnection(username, password);
                } else {
                    con = getConnectionFactory().createConnection();
                }
                con.start();
                Logger.debug("returning newly created Connection: [" + getSummary(con) + "]");
            } catch (JMSException e) {
                throw new JmsException("could not create connection", e);
            }
        } else {
            Logger.debug("returning cached Connection: [" + getSummary(con) + "]");
        }
        return con;
    }

    @Override
    public Connection getConnection() {
        return getConnection(null, null);
    }

    private synchronized String getSummary(final Connection con) {
        StringBuffer sb = new StringBuffer();
        try {
            ConnectionMetaData meta = con.getMetaData();
            if (meta != null) {
                sb.append(Constants.EOL);
                sb.append("JMSProviderName: " + meta.getJMSProviderName() + Constants.EOL);
                sb.append("Class: " + con.getClass() + Constants.EOL);
                sb.append("JMSMajorVersion: " + meta.getJMSMajorVersion() + Constants.EOL);
                sb.append("JMSMinorVersion: " + meta.getJMSMinorVersion() + Constants.EOL);
                sb.append("JMSVersion: " + meta.getJMSVersion() + Constants.EOL);
            }
        } catch (JMSException e) {
            Logger.error("could not get connection meta information", e);
        }
        return sb.toString();

    }

    public synchronized void setConnectionFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void shutdown() {
        closeConnection();
    }

    public String getName() {
        return this.toString();
    }

    private synchronized void closeConnection() {
        JmsUtils.closeConnection(con);
    }

    private synchronized ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

}
