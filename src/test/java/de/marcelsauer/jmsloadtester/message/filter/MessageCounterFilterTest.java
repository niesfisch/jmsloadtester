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
package de.marcelsauer.jmsloadtester.message.filter;

import de.marcelsauer.jmsloadtester.message.ContentFilter;
import org.junit.Before;

import static org.easymock.EasyMock.expect;

public class MessageCounterFilterTest extends AbstractFilterTest {

    public MessageCounterFilterTest() {
        mockMessageTracker();
    }

    @Before
    public void setup() throws Exception {
        expect(messageTracker.getTotalMessagesSent()).andReturn(99887766).anyTimes();
        replay();
    }

    @Override
    protected String getPlaceholder() {
        return "messageCounter";
    }

    @Override
    protected String getTestRegex() {
        return ".*99887766.*";
    }

    @Override
    protected ContentFilter getFilterToTest() {
        return new MessageCounterFilter(messageTracker);
    }

}
