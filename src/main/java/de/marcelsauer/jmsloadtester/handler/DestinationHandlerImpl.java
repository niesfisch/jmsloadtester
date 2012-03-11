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
package de.marcelsauer.jmsloadtester.handler;

import de.marcelsauer.jmsloadtester.core.JmsException;
import de.marcelsauer.jmsloadtester.tools.Logger;
import org.springframework.jndi.JndiTemplate;

import javax.jms.Destination;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

public class DestinationHandlerImpl implements DestinationHandler {

    private JndiTemplate jndiTemplate;
    private Map<String, Destination> cache = new HashMap<String, Destination>();

    public DestinationHandlerImpl(JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }

    @Override
    public synchronized Destination getDestination(final String name) {
        Destination dest = getCached(name);
        if (dest != null) {
            Logger.debug("returning cached destination: " + dest + " " + dest.hashCode());
        } else {
            try {
                dest = (Destination) jndiTemplate.lookup(name);
                Logger.debug("returning newly created destination: [" + dest + "] " + dest.hashCode());
                putCached(name, dest);
            } catch (NamingException e) {
                // JndiUtil.printBindings(jndiTemplate.);
                throw new JmsException("could not lookup destination " + name, e);
            }
        }
        return dest;
    }

    public synchronized void setJndiTemplate(JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }

    private synchronized Destination getCached(final String destination) {
        return cache.get(destination);
    }

    private synchronized void putCached(final String destinationName, final Destination destination) {
        cache.put(destinationName, destination);
    }

}