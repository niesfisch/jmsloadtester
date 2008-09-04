package de.marcelsauer.jmsloadtester.config;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.MessageInterceptor;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;
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
public class ClassParserTest extends AbstractJmsLoaderTest {

    private static final String SEPARATOR = ",";

    public void testParsing() throws Exception {
        final String packageDecl = "de.marcelsauer.jmsloadtester.tools.ReflectionUtilsTest$";
        String[] classesExist1 = new String[] { "TestInterceptor1" };
        String[] classesExist2 = new String[] { "TestInterceptor1", "TestInterceptor2", "TestInterceptor3" };

        String classString = "";
        for(String classname : classesExist1){
            classString += packageDecl + classname + SEPARATOR;
        }
        assertTrue(getInterceptors(classString).size() == 1);
        classString = "";
        for(String classname : classesExist2){
            classString += packageDecl + classname + SEPARATOR;
        }
        assertTrue(getInterceptors(classString).size() == 3);
    }

    public void testExceptionBehaviour() throws Exception{
        testException("", "");
        testException(null, "");
        testException("", null);
        testException(null, null);
        
        testException("1", "");
        testException(null, "1");
        testException("1", null);
    }
    
    private void testException(String classes, String separator) throws Exception{
        try {
            ClassParser.parseToInstances(classes, separator);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
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
