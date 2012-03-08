package de.marcelsauer.jmsloadtester.jndi;

import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import java.util.Map;
import java.util.TreeMap;

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
