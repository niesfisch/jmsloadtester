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
import de.marcelsauer.jmsloadtester.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
