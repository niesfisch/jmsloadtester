package de.marcelsauer.jmsloadtester;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;
import de.marcelsauer.jmsloadtester.spring.SpringFactory;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

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
public class MainTest extends TestCase {

    private static Set<String> uniqMessagesSent = new HashSet<String>();
    private static Set<String> uniqMessagesReceived = new HashSet<String>();

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testAll() {
        System.setProperty("app.properties.file", ".\\test\\resources\\config\\integrationtest.app.properties");
        Config config = SpringFactory.getBean("config");
        Main main = new Main(false);
        main.setup();
        Logger.setOut(new SenderCapture());
        main.doRun();
        assertEquals(config.getExpectedMessageSentCount(), uniqMessagesSent.size());
        assertTrue(config.getSubscriberWaitForTotalMessages() <= uniqMessagesSent.size());
    }

    private class SenderCapture implements OutputStrategy {
        public synchronized void output(String line) {
            // System.out.println(line);
            if (StringUtils.contains(line, "sending message")) {
                uniqMessagesSent.add(line);
            }
            if (StringUtils.contains(line, "total messages received so far")) {
                uniqMessagesReceived.add(line);
            }
        }
    }
}
