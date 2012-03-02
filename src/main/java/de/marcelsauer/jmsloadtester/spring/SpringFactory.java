package de.marcelsauer.jmsloadtester.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
public class SpringFactory {

    private static final String[] configLocations = new String[] { "./conf/spring/*-appCtx.xml" };
    private static final ApplicationContext appCtx = new FileSystemXmlApplicationContext(configLocations);

    public static synchronized ApplicationContext getAppCtx() {
        return appCtx;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> T getBean(final String name) {
        return (T) getAppCtx().getBean(name);
    }
}
