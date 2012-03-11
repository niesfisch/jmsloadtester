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
package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class StaticMessageContentStrategyTest extends AbstractJmsLoaderTest {

    @Test
    public void testGetMessageCount() {
        assertEquals(0, getStrategy("aaa", 0).getMessageCount());
        assertEquals(2, getStrategy("bbb", 2).getMessageCount());
        assertEquals(100, getStrategy("ccc", 100).getMessageCount());
    }

    @Test
    public void testGetDescription() {
        final String message = "aaa";
        final String expected = "Static: always returning message " + message + " as message content";
        assertEquals(expected, getStrategy(message, 0).getDescription());
    }

    @Test
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

    @Test
    public void testRemove() {
        try {
            new StaticMessageContentStrategy("dd", 3).iterator().remove();
            fail("expected exception");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void testNext() {
        // tested by executeIterator
    }

    @Test
    public void testHasNext() {
        // tested by executeIterator
    }

    private MessageContentStrategy getStrategy(final String message, final int amout) {
        return new StaticMessageContentStrategy(message, amout);
    }
}
