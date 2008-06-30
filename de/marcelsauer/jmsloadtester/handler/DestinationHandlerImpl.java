package de.marcelsauer.jmsloadtester.handler;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.naming.NamingException;

import org.springframework.jndi.JndiTemplate;

import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.tools.Logger;

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
public class DestinationHandlerImpl implements DestinationHandler {
    
    private JndiTemplate jndiTemplate;
    private Map<String,Destination> cache = new HashMap<String,Destination>();
    
    public DestinationHandlerImpl(JndiTemplate jndiTemplate){
        this.jndiTemplate = jndiTemplate;
    }
    
    public synchronized Destination getDestination(final String name) {
        Destination dest = getCached(name);
        if(dest != null){
            Logger.debug("returning cached destination: " + dest + " " + dest.hashCode());
        } else {
            try {
                dest = (Destination) jndiTemplate.lookup(name);
                Logger.debug("returning newly created destination: [" + dest + "] " + dest.hashCode());
                putCached(name, dest);
            } catch (NamingException e){
                //JndiUtil.printBindings(jndiTemplate.);
                throw new JmsException("could not lookup destination " +  name, e);
            }
        }
        return dest;
    }

    public void setJndiTemplate(JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }

    private synchronized Destination getCached(final String destination){
        return cache.get(destination);
    }
    
    private synchronized void putCached(final String destinationName, final Destination destination){
        cache.put(destinationName, destination);
    }

}