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

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.ContentFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class FilterFactoryImplTest extends AbstractJmsLoaderTest {

    private FilterFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new FilterFactoryImpl();
    }

    @Test
    public void testFactoryBehaviour() {
        // basic tests
        assertTrue(factory.getFilters(null).size() == 0);
        assertTrue(factory.getFilters("").size() == 0);
        assertTrue(factory.getFilters("       ").size() == 0);

        assertTrue(factory.getFilters("datetime").size() == 0);
        assertTrue(factory.getFilters("nanotime").size() == 0);
        assertTrue(factory.getFilters("random").size() == 0);
        assertTrue(factory.getFilters("messageCounter").size() == 0);

        assertTrue(factory.getFilters(":datetime").size() == 0);
        assertTrue(factory.getFilters(":nanotime").size() == 0);
        assertTrue(factory.getFilters(":random").size() == 0);
        assertTrue(factory.getFilters(":messageCounter").size() == 0);

        assertTrue(factory.getFilters("datetime:").size() == 0);
        assertTrue(factory.getFilters("nanotime:").size() == 0);
        assertTrue(factory.getFilters("random:").size() == 0);
        assertTrue(factory.getFilters("messageCounter:").size() == 0);

        assertTrue(factory.getFilters(":datetime:").size() == 1);
        assertTrue(factory.getFilters(":nanotime:").size() == 1);
        assertTrue(factory.getFilters(":random:").size() == 1);
        assertTrue(factory.getFilters(":messageCounter:").size() == 1);

        assertTrue(factory.getFilters(" :datetime: ").size() == 1);
        assertTrue(factory.getFilters(" :nanotime: ").size() == 1);
        assertTrue(factory.getFilters(" :random: ").size() == 1);
        assertTrue(factory.getFilters(" :messageCounter:    ").size() == 1);

        assertTrue(factory.getFilters(" :datetime: :datetime: :datetime: ").size() == 1);
        assertTrue(factory.getFilters(" :nanotime: :datetime: :datetime: ").size() == 2);
        assertTrue(factory.getFilters(" :datetime: :random: :datetime: ").size() == 2);
        assertTrue(factory.getFilters("    :messageCounter::messageCounter::messageCounter:    ").size() == 1);
        assertTrue(factory.getFilters(" :datetime: :nanotime: :random: :messageCounter:").size() == 4);

        // type tests
        ((FilterFactoryImpl) factory).setDateFilter(createMockOfType(DateFilter.class));
        ((FilterFactoryImpl) factory).setNanoFilter(createMockOfType(NanoFilter.class));
        ((FilterFactoryImpl) factory).setRandFilter(createMockOfType(RandomFilter.class));
        ((FilterFactoryImpl) factory).setMessageCounterFilter(createMockOfType(MessageCounterFilter.class));

        List<ContentFilter> filters = factory.getFilters(" :datetime: :nanotime: :random: :messageCounter:");

        assertTrue(filters.get(0) instanceof MessageCounterFilter);
        assertTrue(filters.get(1) instanceof DateFilter);
        assertTrue(filters.get(2) instanceof NanoFilter);
        assertTrue(filters.get(3) instanceof RandomFilter);

    }
}
