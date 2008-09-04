package de.marcelsauer.jmsloadtester.message.filter;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.ContentFilter;
import de.marcelsauer.jmsloadtester.message.Payload;

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
public abstract class AbstractFilterTest extends AbstractJmsLoaderTest {

    private ContentFilter filter;
    private String testRegex;
    private String placeholder;

    protected abstract String getPlaceholder();

    protected abstract String getTestRegex();

    protected abstract ContentFilter getFilterToTest();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        filter = getFilterToTest();
        placeholder = getPlaceholder();
        testRegex = getTestRegex();
    }

    public void testFilterReplacesNothing() {
        assertTrue("test 1 1 1 1 1".equals(filter.filter(new Payload("test 1 1 1 1 1")).asString()));
        assertTrue("     test 1 1 1 1 1        ".equals(filter.filter(new Payload("     test 1 1 1 1 1        ")).asString()));
        assertTrue(placeholder.equals(filter.filter(new Payload(placeholder)).asString()));
        assertTrue("::".equals(filter.filter(new Payload("::")).asString()));
        assertTrue((":" + placeholder).equals(filter.filter(new Payload(":" + placeholder)).asString()));
        assertTrue((placeholder + ":").equals(filter.filter((new Payload(placeholder + ":"))).asString()));
        assertTrue((": " + placeholder + " :").equals(filter.filter((new Payload(": " + placeholder + " :"))).asString()));
    }

    public void testFilterReplacesCorrectly() {
        assertTrue(filter.filter(new Payload(":" + placeholder + ":")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload("     :" + placeholder + ":")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload(":" + placeholder + ":     ")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload("    :" + placeholder + ":   ")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload("test:" + placeholder + ":test")).asString().matches(testRegex));
        assertTrue(filter.filter(new Payload(":::" + placeholder + ":::")).asString().matches(testRegex));
    }
}
