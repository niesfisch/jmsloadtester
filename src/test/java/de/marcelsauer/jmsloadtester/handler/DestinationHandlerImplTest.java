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
