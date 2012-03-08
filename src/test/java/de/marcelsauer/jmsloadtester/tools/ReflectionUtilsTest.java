package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;

import static junit.framework.Assert.assertTrue;
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
public class ReflectionUtilsTest extends AbstractJmsLoaderTest {

    @Test
    public void testNewInstance() throws Exception {
        MessageInterceptor i1 = ReflectionUtils.newInstance("de.marcelsauer.jmsloadtester.tools.ReflectionUtilsTest$TestInterceptor1");
        MessageInterceptor i2 = ReflectionUtils.newInstance("de.marcelsauer.jmsloadtester.tools.ReflectionUtilsTest$TestInterceptor2");
        MessageInterceptor i3 = ReflectionUtils.newInstance("de.marcelsauer.jmsloadtester.tools.ReflectionUtilsTest$TestInterceptor3");

        assertTrue(i1 != null && i1 instanceof MessageInterceptor);
        assertTrue(i2 != null && i1 instanceof MessageInterceptor);
        assertTrue(i3 != null && i1 instanceof MessageInterceptor);
    }

    @Test
    public void testNewInstanceExceptions() throws Exception {
        try {
            MessageInterceptor i1 = ReflectionUtils.newInstance("de.marcelsauer.jmsloadtester.tools.ReflectionUtilsTest$NonInterceptor");
            fail("expected ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }

        try {
            MessageInterceptor i1 = ReflectionUtils.newInstance(null);
            fail("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            MessageInterceptor i1 = ReflectionUtils.newInstance("does.not.exist");
            fail("expected NullPointerException");
        } catch (ClassNotFoundException e) {
            // expected
        }
    }

    static class TestInterceptor1 implements MessageInterceptor {
        public void intercept(Message message, ThreadTracker threadTracker, MessageTracker messageTracker) throws JMSException {
        }
    }

    static class TestInterceptor2 extends TestInterceptor1 {
    }

    static class TestInterceptor3 extends TestInterceptor2 {
    }

    static class NonInterceptor {
    }

}
