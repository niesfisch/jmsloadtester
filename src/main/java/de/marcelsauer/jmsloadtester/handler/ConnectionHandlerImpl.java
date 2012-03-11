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
