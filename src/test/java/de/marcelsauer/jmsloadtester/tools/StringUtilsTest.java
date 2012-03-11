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
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest extends AbstractJmsLoaderTest {

    private enum ENUMS {
        A, B
    }

    ;

    @Test
    public void testIsEmpty() {
        final ENUMS a = null;
        final ENUMS b = ENUMS.A;
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(" "));
        assertTrue(StringUtils.isEmpty("     "));
        assertTrue(StringUtils.isEmpty((Object) null));
        assertTrue(StringUtils.isEmpty(a));

        assertFalse(StringUtils.isEmpty("a"));
        assertFalse(StringUtils.isEmpty(" a"));
        assertFalse(StringUtils.isEmpty("     ."));
        assertFalse(StringUtils.isEmpty(22));
        assertFalse(StringUtils.isEmpty(b));
    }
}
