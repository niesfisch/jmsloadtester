package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.core.JmsException;

import java.io.*;

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

    public static byte[] getBytesFromFile(final File file) {
        byte[] bytes = null;
        InputStream is = null;

        try {
            is = new FileInputStream(file);
            // Get the size of the file
            long length = file.length();

            if (length > Integer.MAX_VALUE) {
                // File is too large
            }

            // Create the byte array to hold the data
            bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new JmsException("Could not completely read file " + file.getName());
            }

        } catch (IOException e) {
            throw new JmsException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new JmsException(e);
            }
        }

        return bytes;
    }
}
