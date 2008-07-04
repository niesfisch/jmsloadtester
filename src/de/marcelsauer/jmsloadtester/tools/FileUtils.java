package de.marcelsauer.jmsloadtester.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
public class FileUtils {

    public static String getFileContents(final File file) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(Constants.EOL);
            }
        } catch (FileNotFoundException e) {
            Logger.error("could not read file for message", e);
        } catch (IOException e) {
            Logger.error("could not read line in file for message", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Logger.error("could not close file for message", e);
            }
        }
        return sb.toString();
    }

}
