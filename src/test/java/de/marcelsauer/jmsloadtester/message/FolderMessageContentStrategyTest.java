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

public class FolderMessageContentStrategyTest extends AbstractJmsLoaderTest {

    private static final String folder = "./src/test/resources/testmessages";
    private static final String folderNotExistent = ".\\test\\resources\\NotGtere";

    private String regexTxt = ".*.txt";
    private String regexXml = "t.*.xml";
    private String regexNoMatch = ".*.yyy";

    @Test
    public void testFolderMessageContentStrategy() {
        // 0 should not do anything
        new FolderMessageContentStrategy(folderNotExistent, "", 0);
        try {
            new FolderMessageContentStrategy(folderNotExistent, "", 1);
            fail("expected an exception because directory does not exist");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testGetMessageCount() {
        assertEquals(0, getStrategy(regexTxt, 0).getMessageCount());
        assertEquals(2, getStrategy(regexTxt, 1).getMessageCount());
        assertEquals(40, getStrategy(regexTxt, 20).getMessageCount());

        assertEquals(0, getStrategy(regexXml, 0).getMessageCount());
        assertEquals(1, getStrategy(regexXml, 1).getMessageCount());
        assertEquals(20, getStrategy(regexXml, 20).getMessageCount());

        assertEquals(0, getStrategy(regexNoMatch, 0).getMessageCount());
        assertEquals(0, getStrategy(regexNoMatch, 1).getMessageCount());
        assertEquals(0, getStrategy(regexNoMatch, 20).getMessageCount());
    }

    @Test
    public void testGetDescription() {
        final String expected = "Folder: using all files matching " + regexTxt + " in directory " + folder + " as message content. returning each " + 0 + " times";
        assertEquals(expected, getStrategy(regexTxt, 0).getDescription());
    }

    @Test
    public void testIterator() {
        // x2 because 2 txt files in the folder
        executeIterator(getStrategy(regexTxt, 0), 0, 2);
        executeIterator(getStrategy(regexTxt, 1), 2, 2);
        executeIterator(getStrategy(regexTxt, 2), 4, 2);

        executeIterator(getStrategy(regexXml, 0), 0, 1);
        executeIterator(getStrategy(regexXml, 1), 1, 1);
        executeIterator(getStrategy(regexXml, 20), 20, 1);

        executeIterator(getStrategy(regexNoMatch, 0), 0, 0);
        executeIterator(getStrategy(regexNoMatch, 1), 0, 0);
        executeIterator(getStrategy(regexNoMatch, 20), 0, 0);
    }

    private void executeIterator(final MessageContentStrategy strategy, final int expectedAmount, final int fileCount) {
        int amount = 0;
        Payload oldMessage = null;
        for (Payload message : strategy) {
            assertTrue("message is empty", message != null && !"".equals(message.asString().trim()));
            // as soon as we switch files they should be unequal
            if (amount != expectedAmount / fileCount) {
                assertTrue("messages should be equal", (oldMessage != null) ? message.equals(oldMessage) : true);
            } else {
                assertTrue("message should be unequal", (oldMessage != null) ? !message.equals(oldMessage) : false);
            }
            oldMessage = message;
            amount++;
        }
        assertEquals(expectedAmount, amount);
    }

    @Test
    public void testRemove() {
        try {
            new FolderMessageContentStrategy(folder, "ss", 2).iterator().remove();
            fail("expected exception");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    private MessageContentStrategy getStrategy(final String regex, final int amout) {
        return new FolderMessageContentStrategy(folder, regex, amout);
    }

    @Test
    public void testNext() {
        // tested by executeIterator
    }

    @Test
    public void testHasNext() {
        // tested by executeIterator
    }
}
