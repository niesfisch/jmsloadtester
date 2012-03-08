package de.marcelsauer.jmsloadtester.message.filter;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.message.ContentFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * <p/>
 * This file is part of JMS Load Tester.
 * <p/>
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p/>
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
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
