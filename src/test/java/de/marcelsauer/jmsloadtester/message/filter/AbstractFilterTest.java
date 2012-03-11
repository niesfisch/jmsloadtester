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
package de.marcelsauer.jmsloadtester.message.filter;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.ContentFilter;
import de.marcelsauer.jmsloadtester.message.Payload;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public abstract class AbstractFilterTest extends AbstractJmsLoaderTest {

    private ContentFilter filter;
    private String testRegex;
    private String placeholder;

    protected abstract String getPlaceholder();

    protected abstract String getTestRegex();

    protected abstract ContentFilter getFilterToTest();

    @Before
    public void setUp() throws Exception {
        filter = getFilterToTest();
        placeholder = getPlaceholder();
        testRegex = getTestRegex();
    }

    @Test
    public void testFilterReplacesNothing() {
        assertTrue("test 1 1 1 1 1".equals(filter.filter(new Payload("test 1 1 1 1 1")).asString()));
        assertTrue("     test 1 1 1 1 1        ".equals(filter.filter(new Payload("     test 1 1 1 1 1        ")).asString()));
        assertTrue(placeholder.equals(filter.filter(new Payload(placeholder)).asString()));
        assertTrue("::".equals(filter.filter(new Payload("::")).asString()));
        assertTrue((":" + placeholder).equals(filter.filter(new Payload(":" + placeholder)).asString()));
        assertTrue((placeholder + ":").equals(filter.filter((new Payload(placeholder + ":"))).asString()));
        assertTrue((": " + placeholder + " :").equals(filter.filter((new Payload(": " + placeholder + " :"))).asString()));
    }

    @Test
    public void testFilterReplacesCorrectly() {
        assertTrue(filter.filter(new Payload(":" + placeholder + ":")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload("     :" + placeholder + ":")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload(":" + placeholder + ":     ")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload("    :" + placeholder + ":   ")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload("test:" + placeholder + ":test")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload(":::" + placeholder + ":::")).asString().matches(testRegex));
    }
}
