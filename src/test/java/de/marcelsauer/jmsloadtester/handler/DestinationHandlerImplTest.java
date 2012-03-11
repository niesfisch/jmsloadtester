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

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jndi.JndiTemplate;

import javax.jms.Destination;
import javax.naming.NamingException;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DestinationHandlerImplTest extends AbstractJmsLoaderTest {

    private DestinationHandler handler;
    private JndiTemplate mockJndiTemplate;
    private Destination mockDestination1;
    private Destination mockDestination2;

    @Before
    public void setUp() throws Exception {
        mockJndiTemplate = createMockOfType(JndiTemplate.class);
        mockDestination1 = createMockOfType(Destination.class);
        mockDestination2 = createMockOfType(Destination.class);
        handler = new DestinationHandlerImpl(mockJndiTemplate);
    }

    @Test
    public void testCaching() throws NamingException {
        expect(mockJndiTemplate.lookup("dummy1")).andReturn(mockDestination1).times(1);
        expect(mockJndiTemplate.lookup("dummy2")).andReturn(mockDestination2).times(1);
        replay();
        final Destination dest1 = handler.getDestination("dummy1");
        final Destination dest2 = handler.getDestination("dummy2");
        assertTrue(dest1 != null);
        assertTrue(dest2 != null);
        assertTrue(dest1 != dest2);
        assertFalse(dest1.equals(dest2));
        for (int i = 0; i < 100; i++) {
            Destination cachedDest = handler.getDestination("dummy1");
            assertTrue(dest1 == cachedDest);
            assertTrue(dest1.equals(cachedDest));
        }
        verify();
    }

}
