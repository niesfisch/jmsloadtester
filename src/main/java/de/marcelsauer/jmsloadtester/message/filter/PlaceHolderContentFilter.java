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
import de.marcelsauer.jmsloadtester.message.Payload;
import de.marcelsauer.jmsloadtester.tools.ArrayUtils;

public class PlaceHolderContentFilter implements ContentFilter {

    private FilterFactory filterFactory;

    public PlaceHolderContentFilter(final FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    public Payload filter(final Payload input) {
        // no need to replace
        if (!ArrayUtils.contains(input.asBytes(), FilterConstants.START.getBytes()[0])) {
            return input;
        }
        Payload message = input;
        for (ContentFilter filter : filterFactory.getFilters(input.asString())) {
            message = filter.filter(message);
        }
        return message;
    }
}
