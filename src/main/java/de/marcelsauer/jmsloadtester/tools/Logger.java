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

import de.marcelsauer.jmsloadtester.output.OutputStrategy;

public class Logger {

    private static OutputStrategy out;
    private static final String DELIMITER = ">>>>>>>";

    public static void info(final String text) {
        synchronized (out) {
            System.out.println("info [" + ThreadTools.getCurrentThreadName() + "] " + text);
        }
    }

    public static void debug(final String text) {
        synchronized (out) {
            out.output("debug [" + ThreadTools.getCurrentThreadName() + "] " + text);
        }
    }

    public static void error(final String text, final Exception e) {
        synchronized (out) {
            error(text);
            e.printStackTrace(System.err);
        }
    }

    public static void error(final String text) {
        synchronized (out) {
            System.err.println(DELIMITER + " " + text);
        }
    }

    public static void error(final Exception e) {
        synchronized (out) {
            error(null, e);
        }
    }

    public static void setOut(final OutputStrategy out) {
        synchronized (out) {
            Logger.out = out;
        }
    }
}
