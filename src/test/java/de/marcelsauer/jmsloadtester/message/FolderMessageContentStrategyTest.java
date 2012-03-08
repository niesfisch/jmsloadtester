package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Test;

import static org.junit.Assert.*;

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
