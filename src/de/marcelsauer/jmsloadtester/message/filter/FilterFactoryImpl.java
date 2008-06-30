package de.marcelsauer.jmsloadtester.message.filter;

import java.util.ArrayList;
import java.util.List;

import de.marcelsauer.jmsloadtester.message.ContentFilter;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * 
 * This file is part of JMS Load Tester.
 * 
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public class FilterFactoryImpl implements FilterFactory {

    private ContentFilter messageCounterFilter;
    private ContentFilter dateFilter;
    private ContentFilter nanoFilter;
    private ContentFilter randFilter;

    public List<ContentFilter> getFilters(String input) {
        List<ContentFilter> filters = new ArrayList<ContentFilter>();
        // replace ${messageCounter}
        if (isPresent(input, FilterConstants.MESSAGE_COUNTER)) {
            filters.add(messageCounterFilter);
        }
        // replace ${datetime}
        if (isPresent(input, FilterConstants.DATE)) {
            filters.add(dateFilter);
        }
        // replace ${nanotime}
        if (isPresent(input, FilterConstants.NANO)) {
            filters.add(nanoFilter);
        }
        // replace ${random}
        if (isPresent(input, FilterConstants.RAND)) {
            filters.add(randFilter);
        }
        return filters;
    }

    private boolean isPresent(final String input, final String placeholder) {
        return StringUtils.contains(input, getFullPlaceHolder(placeholder));
    }

    private String getFullPlaceHolder(String what) {
        return FilterConstants.START + what + FilterConstants.END;
    }

    public void setMessageCounterFilter(ContentFilter messageCounterFilter) {
        this.messageCounterFilter = messageCounterFilter;
    }

    public void setDateFilter(ContentFilter dateFilter) {
        this.dateFilter = dateFilter;
    }

    public void setNanoFilter(ContentFilter nanoFilter) {
        this.nanoFilter = nanoFilter;
    }

    public void setRandFilter(ContentFilter randFilter) {
        this.randFilter = randFilter;
    }
}
