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
package de.marcelsauer.jmsloadtester;

import de.marcelsauer.jmsloadtester.config.Config;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;
import de.marcelsauer.jmsloadtester.spring.SpringFactory;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MainTest extends TestCase {

    private static Set<String> uniqMessagesSent = new HashSet<String>();
    private static Set<String> uniqMessagesReceived = new HashSet<String>();

    @Test
    public void testXXX() {

    }

    @Test
    @Ignore("TBD")
    public void xxtestAll() {
        // spring needs to know where to find the properties file
        System.setProperty("app.properties.file", ".\\test\\resources\\config\\integrationtest.app.properties");

        final Main main = new Main(false);

        // here we overwrite to capture the output that would otherwise be
        // ignored or printed to a file
        Logger.setOut(new SenderCapture());

        main.doRun();

        final Config config = SpringFactory.getBean("config");

        assertEquals(config.getExpectedMessageSentCount(), uniqMessagesSent.size());
        assertTrue(config.getSubscriberWaitForTotalMessages() == uniqMessagesReceived.size());

        System.out.println(uniqMessagesReceived.size());
    }

    private static class SenderCapture implements OutputStrategy {
        public synchronized void output(String line) {
            // System.out.println(line);
            if (StringUtils.contains(line, "sending message")) {
                uniqMessagesSent.add(line);
            }
            if (StringUtils.contains(line, "MessageTracker was informed of incoming message")) {
                uniqMessagesReceived.add(line);
            }
        }
    }
}
