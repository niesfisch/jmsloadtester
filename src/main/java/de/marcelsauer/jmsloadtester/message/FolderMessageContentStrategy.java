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
package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.tools.FileUtils;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FolderMessageContentStrategy implements MessageContentStrategy {

    private static Map<String, Payload> cache = new HashMap<String, Payload>();
    private String directory;
    private String regex;
    private File[] files;
    private int counter;

    private int amount;
    private int amountCounter;

    public FolderMessageContentStrategy(final String directory, final String regex, final int amount) {
        this.directory = directory;
        this.regex = regex;
        this.amount = amount;
        if (amount > 0) {
            init();
        } else {
            Logger.info("message send amount was 0. so not trying to load anything.");
        }
    }

    @Override
    public Payload next() {
        Payload nextMessage = getCached(files[counter].getName());
        if (!StringUtils.isEmpty(nextMessage)) {
            Logger.debug("returning cached file: " + cache);
            increaseCounter();
        } else {
            nextMessage = new Payload(FileUtils.getBytesFromFile(files[counter]));
            putCached(files[counter].getName(), nextMessage);
            increaseCounter();
        }
        return nextMessage;
    }

    @Override
    public boolean hasNext() {
        return (files != null) ? counter < files.length : false;
    }

    @Override
    public int getMessageCount() {
        return (files != null) ? files.length * amount : 0;
    }

    @Override
    public String getDescription() {
        return "Folder: using all files matching " + regex + " in directory " + directory + " as message content. returning each " + amount + " times";
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public Iterator<Payload> iterator() {
        return this;
    }

    private void increaseCounter() {
        if (++amountCounter >= amount) {
            counter++;
            amountCounter = 0;
        }
    }

    private synchronized Payload getCached(final String filename) {
        return cache.get(filename);
    }

    private synchronized void putCached(final String filename, final Payload content) {
        cache.put(filename, content);
    }

    private void init() {
        File dir = new File(directory);
        files = dir.listFiles(new RegexFilter());
        if (files == null) {
            throw new NullPointerException("the provided message directory doesn't seem to exist. try to use the full pathname.");
        }
    }

    private class RegexFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            String fullname = dir.getPath() + Constants.SEP + name;
            File file = new File(fullname);
            if (file.isFile() && name.matches(regex)) {
                return true;
            }
            return false;
        }
    }
}
