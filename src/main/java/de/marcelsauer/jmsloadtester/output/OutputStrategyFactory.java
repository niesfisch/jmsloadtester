package de.marcelsauer.jmsloadtester.output;

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
public class OutputStrategyFactory {

    public static final String STDOUT = "STDOUT";
    public static final String STDERR = "STDERR";
    public static final String FILE = "FILE";
    public static final String SILENT = "SILENT";

    public static OutputStrategy getOutputStrategy(final String type) {
        OutputStrategy strategy = null;
        if (STDOUT.equals(type)) {
            strategy = new StdoutOutputStrategy();
        } else if (STDERR.equals(type)) {
            strategy = new StderrOutputStrategy();
        } else if (SILENT.equals(type)) {
            strategy = new SilentOutputStrategy();
        } else if (type.indexOf(FILE) > -1) {
            strategy = new FileOutputStrategy(type.replaceAll("FILE#", ""));
        } else {
            throw new IllegalArgumentException("the output strategy does not exist, was: " + type);
        }
        return strategy;
    }
}
