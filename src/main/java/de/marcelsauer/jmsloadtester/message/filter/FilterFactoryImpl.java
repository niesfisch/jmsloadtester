package de.marcelsauer.jmsloadtester.message.filter;

import de.marcelsauer.jmsloadtester.message.ContentFilter;
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
public class FilterFactoryImpl implements FilterFactory {

    private ContentFilter messageCounterFilter;
    private ContentFilter dateFilter;
    private ContentFilter nanoFilter;
    private ContentFilter randFilter;

    public List<ContentFilter> getFilters(final String input) {
        final List<ContentFilter> filters = new ArrayList<ContentFilter>();
        // if we don't have ":" we are done
        if (!StringUtils.contains(input, FilterConstants.START)) {
            return filters;
        }
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

    private String getFullPlaceHolder(final String what) {
        return FilterConstants.START + what + FilterConstants.END;
    }

    public void setMessageCounterFilter(final ContentFilter messageCounterFilter) {
        this.messageCounterFilter = messageCounterFilter;
    }

    public void setDateFilter(final ContentFilter dateFilter) {
        this.dateFilter = dateFilter;
    }

    public void setNanoFilter(final ContentFilter nanoFilter) {
        this.nanoFilter = nanoFilter;
    }

    public void setRandFilter(final ContentFilter randFilter) {
        this.randFilter = randFilter;
    }
}
