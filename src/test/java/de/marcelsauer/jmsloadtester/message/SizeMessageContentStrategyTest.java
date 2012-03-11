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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SizeMessageContentStrategyTest extends AbstractJmsLoaderTest {

    @Test
    public void testGetNext() {
        assertTrue(getStrategy(1000, 1).next().asBytes().length == 1000);
        assertTrue(getStrategy(1000, 1).next().asBytes().length == 1000);

        assertTrue(getStrategy(1, 1).next().asBytes().length == 1);
        assertTrue(getStrategy(1, 1).next().asBytes().length == 1);

        assertTrue(getStrategy(0, 1).next().asBytes().length == 0);
        assertTrue(getStrategy(0, 1).next().asBytes().length == 0);

        assertTrue(getStrategy(0, 0).next().asBytes().length == 0);
        assertTrue(getStrategy(0, 0).next().asBytes().length == 0);

        assertTrue(getStrategy(0, 999).next().asBytes().length == 0);
        assertTrue(getStrategy(0, 999).next().asBytes().length == 0);
    }

    @Test
    public void testExceptionBehaviour() {
        try {
            getStrategy(-1, 0);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    private MessageContentStrategy getStrategy(int bytes, int count) {
        return new SizeMessageContentStrategy(bytes, count);
    }

}
