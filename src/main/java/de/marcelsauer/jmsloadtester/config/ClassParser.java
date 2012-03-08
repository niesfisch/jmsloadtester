package de.marcelsauer.jmsloadtester.config;

import de.marcelsauer.jmsloadtester.tools.ReflectionUtils;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
public class ClassParser {

    public static <T> List<T> parseToInstances(String classes, String separator) throws Exception {
        if (StringUtils.isEmpty(classes) || StringUtils.isEmpty(separator)) {
            throw new IllegalArgumentException("classes and separator must be given");

        }
        List<T> instances = new ArrayList<T>();
        String[] parsed = classes.split(separator);
        if (parsed.length > 0) {
            for (String classname : parsed) {
                T instance = ReflectionUtils.<T>newInstance(classname);
                instances.add(instance);
            }
        }
        return instances;
    }
}
