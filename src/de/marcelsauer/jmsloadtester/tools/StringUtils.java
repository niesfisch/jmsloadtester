package de.marcelsauer.jmsloadtester.tools;

import java.io.PrintStream;
import java.util.Map;

import de.marcelsauer.jmsloadtester.core.Constants;

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
public class StringUtils {

    public static boolean isEmpty(final String what) {
        return (what == null || "".equals(what.trim()));
    }

    public static boolean isEmpty(final Object what) {
        if (what == null) {
            return true;
        }
        if (what instanceof String) {
            return isEmpty((String) what);
        }
        return false;
    }

    public static boolean isEmpty(final Enum<?> what) {
        return (what == null);
    }

    public static boolean contains(final String input, final String regex) {
        return (input != null && regex != null && !"".equals(input) && input.contains(regex));
    }

    public static void printFormattedColumns(PrintStream stream, Map<String, ? extends Object> map, String divider) {
        int longest = 0;
        for (String key : map.keySet()) {
            if (key.length() > longest) {
                longest = key.length();
            }
        }
        synchronized (stream) {
            for (String key : map.keySet()) {
                stream.printf("%-" + longest + "s " + divider + " %s" + Constants.EOL, key, map.get(key));
            }
        }
    }
}
