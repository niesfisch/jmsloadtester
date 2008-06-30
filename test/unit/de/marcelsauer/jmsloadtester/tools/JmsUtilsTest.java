package de.marcelsauer.jmsloadtester.tools;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;

/**
 *   JMS Load Tester
 *   Copyright (C) 2008 Marcel Sauer <marcel[underscore]sauer[at]gmx.de>
 *   
 *   This file is part of JMS Load Tester.
 *
 *   JMS Load Tester is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JMS Load Tester is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public class JmsUtilsTest extends AbstractJmsLoaderTest {
    
    private Connection connectionMock;
    private Session sessionMock;
    
    protected void setUp() throws Exception {
        super.setUp();
        connectionMock = createMockOfType(Connection.class);
        sessionMock = createMockOfType(Session.class);
    }

    public void testCloseConnection() throws JMSException {
        connectionMock.close();
        replay();
        JmsUtils.closeConnection(connectionMock);
        verify();
    }

    public void testCloseSession() throws JMSException {
        sessionMock.close();
        replay();
        JmsUtils.closeSession(sessionMock);
        verify();
    }
}
