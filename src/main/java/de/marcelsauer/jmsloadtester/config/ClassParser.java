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
package de.marcelsauer.jmsloadtester.config;

import de.marcelsauer.jmsloadtester.tools.ReflectionUtils;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
