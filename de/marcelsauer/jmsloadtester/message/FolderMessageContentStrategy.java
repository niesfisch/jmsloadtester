package de.marcelsauer.jmsloadtester.message;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.tools.FileUtils;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

/**
 *   JMS Load Tester
 *   Copyright (C) 2008 Marcel Sauer <marcel[underscore]sauer[at]gmx.de>
 *   
 *   This file is part of JMS Load Tester.
 *
 *   JMS Load Tester is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JMS Load Tester is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public class FolderMessageContentStrategy implements MessageContentStrategy {
	
    private static Map<String,String> cache = new HashMap<String,String>();
	private String directory;
	private String regex;
	private File[] files;
	private int counter;
	
	private int amount;
	private int amountCounter;
	
	public FolderMessageContentStrategy(final String directory, final String regex, final int amount){
		this.directory = directory;
		this.regex = regex;
		this.amount = amount;
		if(amount > 0){
		    init();
		} else {
		    Logger.info("message send amount was 0. so not trying to load anything.");
		}
	}
	
	public String next() {
	    String nextMessage = getCached(files[counter].getName()); 
	    if(!StringUtils.isEmpty(nextMessage)){
            Logger.debug("returning cached file: " + cache);
            increaseCounter();
	    } else {
	        nextMessage = FileUtils.getFileContents(files[counter]);
	        putCached(files[counter].getName(), nextMessage);
	        increaseCounter();
	    }
	    return nextMessage;
	}

    public boolean hasNext() {
        return (files != null) ? counter < files.length : false;
    }

    public int getMessageCount() {
        return (files != null) ? files.length * amount : 0;
    }

    public String getDescription() {
        return "Folder: using all files matching " + regex + " in directory " + directory + " as message content. returning each " + amount + " times";
    }
    
    @Override
    public String toString(){
        return getDescription();
    }

    public void remove() {
        throw new UnsupportedOperationException("not supported");
    }

    public Iterator<String> iterator() {
        return this;
    }

	private void increaseCounter(){
        if(++amountCounter >= amount){
            counter++;
            amountCounter=0;
        }
    }

	private synchronized String getCached(final String filename){
	    return cache.get(filename);
	}
	
	private synchronized void putCached(final String filename, final String content){
	    cache.put(filename, content);
	}
	
	private void init(){
		File dir = new File(directory);
		files = dir.listFiles(new RegexFilter());
		if(files == null){
			throw new NullPointerException("the provided message directory doesn't seem to exist. try to use the full pathname.");
		}
	}
	
	private class RegexFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			String fullname = dir.getPath() + Constants.SEP + name;
			File file = new File(fullname);
		    if(file.isFile() && name.matches(regex)){
				return true;
			}
			return false;
		}
	}
}
