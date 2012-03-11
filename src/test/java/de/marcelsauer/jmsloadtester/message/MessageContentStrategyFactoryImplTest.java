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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
