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

public class MessageContentStrategyWrapper implements MessageContentStrategy {

    private MessageContentStrategy target;
    private ContentFilter filter;

    public MessageContentStrategyWrapper(final MessageContentStrategy target, final ContentFilter filter) {
        this.target = target;
        this.filter = filter;
    }

    public String getDescription() {
        return target.getDescription();
    }

    public int getMessageCount() {
        return target.getMessageCount();
    }

    public Payload next() {
        return filter.filter(target.next());
    }

    public boolean hasNext() {
        return target.hasNext();
    }

    @Override
    public String toString() {
        return "applying placeholder filtering for [" + getDescription() + "]";
    }

    public void remove() {
        throw new UnsupportedOperationException("not supported");
    }

    public Iterator<Payload> iterator() {
        return this;
    }

}
