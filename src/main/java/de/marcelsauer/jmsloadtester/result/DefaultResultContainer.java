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
package de.marcelsauer.jmsloadtester.result;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;

import java.util.ArrayList;
import java.util.List;

public class DefaultResultContainer implements ResultContainer {

    private List<Pair> resultPairs = new ArrayList<Pair>();

    @Override
    public void addResultKeyValue(final String key, final Object value) {
        this.resultPairs.add(new Pair(key, value));
    }

    @Override
    public void outputResult(final OutputStrategy outputStrategy) {
        StringBuffer sb = new StringBuffer();
        sb.append("Results: " + Constants.EOL);
        sb.append("-------------------------------" + Constants.EOL);
        for (Pair pair : resultPairs) {
            sb.append(pair.key + " -> " + pair.value + Constants.EOL);
        }
        outputStrategy.output(sb.toString());
    }

    private static class Pair {
        String key;
        Object value;

        Pair(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

}
