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
package de.marcelsauer.jmsloadtester.output;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.core.ShutdownAware;
import de.marcelsauer.jmsloadtester.handler.ShutdownHandler;

import java.io.FileWriter;
import java.io.IOException;

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
