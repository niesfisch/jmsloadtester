package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.output.OutputStrategy;

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
