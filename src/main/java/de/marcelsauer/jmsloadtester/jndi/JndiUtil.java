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
package de.marcelsauer.jmsloadtester.jndi;

import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import java.util.Map;
import java.util.TreeMap;

public class JndiUtil {

    public static void getBindings(final Context context, final Map<String, String> results) {
        getBindings(context, "", "", results);
    }

    public static void getBindings(final Context context, String name, String spacer, final Map<String, String> results) {
        name = (name == null) ? "" : name;
        spacer = (spacer == null) ? "" : spacer;
        try {
            NamingEnumeration<NameClassPair> en = context.list(name);
            while (en.hasMoreElements()) {
                String delim = (name.length() > 0) ? "/" : "";
                NameClassPair nc = en.next();
                results.put(name + delim + nc.getName(), nc.getClassName());
                getBindings(context, nc.getName(), spacer, results);
            }
        } catch (javax.naming.NamingException ex) {
            // ignore
        }
    }

    public static void printBindings(final Context context) {
        Map<String, String> bindings = new TreeMap<String, String>();
        getBindings(context, bindings);
        Logger.info("the following bindings were found in the repository:");
        StringUtils.printFormattedColumns(System.out, bindings, ":");
    }

}
