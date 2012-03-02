package de.marcelsauer.jmsloadtester.output;

import java.io.FileWriter;
import java.io.IOException;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.core.ShutdownAware;
import de.marcelsauer.jmsloadtester.handler.ShutdownHandler;

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
public class FileOutputStrategy implements OutputStrategy, ShutdownAware {

    private String filename;
    private FileWriter writer;

    private FileOutputStrategy() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHandler(this));
    }
    
    public FileOutputStrategy(final String filename) {
        this();
        this.filename = filename;
        try {
            writer = new FileWriter(filename, true);
        } catch (IOException e) {
            throw new JmsException("could not open file for output", e);
        }
    }

    public synchronized void output(final String line) {
        try {
            writer.write(line);
            writer.write(Constants.EOL);
            writer.flush();
        } catch (IOException e) {
            throw new JmsException("could not write to file for output", e);
        }
    }

    @Override
    public String toString() {
        return "File output to: " + filename;
    }

    public String getName() {
        return "FileWriter";
    }

    public void shutdown() {
        closeWriter();
    }

    // @todo close the file?
    private synchronized void closeWriter() {
        if (writer != null) {
            // try {
            // //writer.close();
            // } catch (IOException e) {
            // throw new JmsException("could not close file for output", e);
            // }
        }
    }

}
