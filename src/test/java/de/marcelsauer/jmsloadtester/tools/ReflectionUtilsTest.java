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
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

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
