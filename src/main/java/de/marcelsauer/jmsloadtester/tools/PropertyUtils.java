package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.core.JmsException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
public class PropertyUtils {

    public static Properties loadProperties(final String filename) {
        InputStream stream = PropertyUtils.class.getClassLoader().getResourceAsStream(filename);
        try {
            Properties prop = new Properties();
            prop.load(stream);
            return prop;
        } catch (IOException e) {
            throw new JmsException("could not load properties.", e);
        }
    }
}
