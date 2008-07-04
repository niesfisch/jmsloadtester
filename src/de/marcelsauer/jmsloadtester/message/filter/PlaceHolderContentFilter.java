package de.marcelsauer.jmsloadtester.message.filter;

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
public class PlaceHolderContentFilter implements ContentFilter {

    private FilterFactory filterFactory;

    public PlaceHolderContentFilter(final FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    public String filter(final String input) {
        // no need to replace
        if (!StringUtils.contains(input, ":")) {
            return input;
        }
        String message = input;
        for (ContentFilter filter : filterFactory.getFilters(input)) {
            message = filter.filter(message);
        }
        return message;
    }
}
