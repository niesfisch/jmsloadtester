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

import de.marcelsauer.jmsloadtester.core.Constants;

import java.io.PrintStream;
import java.util.Map;

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

    public static void printFormattedColumns(final PrintStream stream, final Map<String, ? extends Object> map, String divider) {
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
