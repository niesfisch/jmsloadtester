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

import java.util.Iterator;

public class StaticMessageContentStrategy implements MessageContentStrategy {

    private int messageCount;
    private Payload message;
    private int count;

    public StaticMessageContentStrategy(final String message, final int messageCount) {
        this.messageCount = messageCount;
        this.message = new Payload(message);
    }

    @Override
    public Payload next() {
        return message;
    }

    @Override
    public boolean hasNext() {
        return count++ < messageCount;
    }

    @Override
    public int getMessageCount() {
        return messageCount;
    }

    @Override
    public String getDescription() {
        return "Static: always returning message " + message + " as message content";
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
}
