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
package de.marcelsauer.jmsloadtester.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringFactory {

    private static final String[] configLocations = new String[]{"./conf/spring/*-appCtx.xml"};
    private static final ApplicationContext appCtx = new FileSystemXmlApplicationContext(configLocations);

    public static synchronized ApplicationContext getAppCtx() {
        return appCtx;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> T getBean(final String name) {
        return (T) getAppCtx().getBean(name);
    }
}
