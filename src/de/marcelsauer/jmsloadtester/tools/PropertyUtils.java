package de.marcelsauer.jmsloadtester.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import de.marcelsauer.jmsloadtester.core.JmsException;

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
public class PropertyUtils {

    public static Properties loadProperties(final String filename) {
        final Properties tmp = new Properties();
        final Properties prop = new Properties();
        try {
            tmp.load(new FileInputStream(filename));
            for (Object key : tmp.keySet()) {
                String k = (String) key;
                String value = tmp.getProperty(k);
                if (k != null && value != null) {
                    prop.put(k.trim(), value.trim());
                }
            }
        } catch (FileNotFoundException e) {
            throw new JmsException("could not load property file " + filename + ". is the path correct?", e);
        } catch (IOException e) {
            throw new JmsException("could not load property file " + filename + ". is the path correct?", e);
        }
        return prop;
    }

}
