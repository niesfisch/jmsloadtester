package de.marcelsauer.jmsloadtester.message.filter;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.ContentFilter;
import de.marcelsauer.jmsloadtester.message.Payload;
import org.junit.Before;
import org.junit.Test;

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
