package de.marcelsauer.jmsloadtester.message;

import java.util.Iterator;

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
public class MessageContentStrategyWrapper implements MessageContentStrategy {
    
    private MessageContentStrategy target;
    private ContentFilter filter;
    
    public MessageContentStrategyWrapper(MessageContentStrategy target, ContentFilter filter){
        this.target = target;
        this.filter = filter;
    }
    
    public String getDescription() {
        return target.getDescription();
    }

    public int getMessageCount() {
        return target.getMessageCount();
    }

    public String next() {
        return filter.filter(target.next());
    }

    public boolean hasNext() {
        return target.hasNext();
    }
    
    @Override
    public String toString(){
        return "applying placeholder filtering for ["  + getDescription() + "]";
    }
    
    public void remove() {
        throw new UnsupportedOperationException("not supported");
    }
    
    public Iterator<String> iterator() {
        return this;
    }

}
