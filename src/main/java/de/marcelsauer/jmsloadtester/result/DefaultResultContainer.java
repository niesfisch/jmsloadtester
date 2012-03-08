package de.marcelsauer.jmsloadtester.result;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;

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
