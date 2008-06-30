package de.marcelsauer.jmsloadtester.result;

import java.util.ArrayList;
import java.util.List;

import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.output.OutputStrategy;

/**
 *   JMS Load Tester
 *   Copyright (C) 2008 Marcel Sauer <marcel[underscore]sauer[at]gmx.de>
 *   
 *   This file is part of JMS Load Tester.
 *
 *   JMS Load Tester is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JMS Load Tester is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public class DefaultResultContainer implements ResultContainer {
    
    List<Pair> resultPairs = new ArrayList<Pair>();
    
    public void addResultKeyValue(final String key, final Object value) {
        this.resultPairs.add(new Pair(key,value));
    }

    public void outputResult(final OutputStrategy outputStrategy) {
        StringBuffer sb = new StringBuffer();
        sb.append("Results: " + Constants.EOL);
        sb.append("-------------------------------" + Constants.EOL);
        for(Pair pair : resultPairs){
            sb.append(pair.key + " -> " + pair.value + Constants.EOL);
        }
        outputStrategy.output(sb.toString());
    }
    
    private class Pair {
        String key;
        Object value;
        Pair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }

}
