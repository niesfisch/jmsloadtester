package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;

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
public class StaticMessageContentStrategyTest extends AbstractJmsLoaderTest {

    public void testGetMessageCount() {
        assertEquals(0, getStrategy("aaa", 0).getMessageCount());
        assertEquals(2, getStrategy("bbb", 2).getMessageCount());
        assertEquals(100, getStrategy("ccc", 100).getMessageCount());
    }

    public void testGetDescription() {
        final String message = "aaa";
        final String expected = "Static: always returning message " + message + " as message content";
        assertEquals(expected, getStrategy(message, 0).getDescription());
    }

    public void testIterator() {
        // x2 because 2 txt files in the folder
        executeIterator(getStrategy("aaaa", 0), 0);
        executeIterator(getStrategy("aaaa", 1), 1);
        executeIterator(getStrategy("aaaa", 100), 100);
    }

    private void executeIterator(final MessageContentStrategy strategy, final int expectedAmount) {
        int amount = 0;
        Payload oldMessage = null;
        for (Payload message : strategy) {
            assertTrue("message is empty", message != null && !"".equals(message.asString().trim()));
            assertTrue("messages should be equal", (oldMessage != null) ? message.equals(oldMessage) : true);
            oldMessage = message;
            amount++;
        }
        assertEquals(expectedAmount, amount);
    }

    public void testRemove() {
        try {
            new StaticMessageContentStrategy("dd", 3).iterator().remove();
            fail("expected exception");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    public void testNext() {
        // tested by executeIterator
    }

    public void testHasNext() {
        // tested by executeIterator
    }

    private MessageContentStrategy getStrategy(final String message, final int amout) {
        return new StaticMessageContentStrategy(message, amout);
    }
}
