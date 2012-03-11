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
package de.marcelsauer.jmsloadtester.config;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ClassParserTest extends AbstractJmsLoaderTest {

    private static final String SEPARATOR = ",";

    @Test
    public void testParsing() throws Exception {
        final String packageDecl = "de.marcelsauer.jmsloadtester.tools.ReflectionUtilsTest$";
        String[] classesExist1 = new String[]{"TestInterceptor1"};
        String[] classesExist2 = new String[]{"TestInterceptor1", "TestInterceptor2", "TestInterceptor3"};

        String classString = "";
        for (String classname : classesExist1) {
            classString += packageDecl + classname + SEPARATOR;
        }
        assertTrue(getInterceptors(classString).size() == 1);
        classString = "";
        for (String classname : classesExist2) {
            classString += packageDecl + classname + SEPARATOR;
        }
        assertTrue(getInterceptors(classString).size() == 3);
    }

    @Test
    public void testExceptionBehaviour() throws Exception {
        testException("", "");
        testException(null, "");
        testException("", null);
        testException(null, null);

        testException("1", "");
        testException(null, "1");
        testException("1", null);
    }

    private void testException(String classes, String separator) throws Exception {
        try {
            ClassParser.parseToInstances(classes, separator);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private List<MessageInterceptor> getInterceptors(String classes) throws Exception {
        return ClassParser.parseToInstances(classes, SEPARATOR);
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
