package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
public class MessageContentStrategyFactoryImplTest extends AbstractJmsLoaderTest {

    private MessageContentStrategyFactory factory;

    @Before
    public void setUp() throws Exception {

        factory = new MessageContentStrategyFactoryImpl();
    }

    @Test
    public void testFactoryBehaviour() {
        final String testDir = System.getProperty("java.io.tmpdir");
        assertTrue(factory.getMessageContentStrategy("FOLDER#8#.*txt#" + testDir) instanceof FolderMessageContentStrategy);
        assertTrue(factory.getMessageContentStrategy("STATIC#500#your message content") instanceof StaticMessageContentStrategy);
        assertTrue(factory.getMessageContentStrategy("SIZE#8#10") instanceof SizeMessageContentStrategy);
    }

    @Test
    public void testExceptionBehaviour() {
        testIllegalArgumentException("does not exist");
        testIllegalArgumentException("");
        testIllegalArgumentException("                   ");
        testIllegalArgumentException(null);
        testNumberFormatException("SIZE#aa#10");
        testNumberFormatException("SIZE#aa#b");
    }

    private void testNumberFormatException(final String toTest) {
        try {
            factory.getMessageContentStrategy(toTest);
            fail("expected Exception");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private void testIllegalArgumentException(final String toTest) {
        try {
            factory.getMessageContentStrategy(toTest);
            fail("expected Exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
