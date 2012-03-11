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
package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public class JmsUtilsTest extends AbstractJmsLoaderTest {

    private Connection connectionMock;
    private Session sessionMock;

    @Before
    public void setUp() throws Exception {

        connectionMock = createMockOfType(Connection.class);
        sessionMock = createMockOfType(Session.class);
    }

    @Test
    public void testCloseConnection() throws JMSException {
        connectionMock.close();
        replay();
        JmsUtils.closeConnection(connectionMock);
        verify();
    }

    @Test
    public void testCloseSession() throws JMSException {
        sessionMock.close();
        replay();
        JmsUtils.closeSession(sessionMock);
        verify();
    }
}
