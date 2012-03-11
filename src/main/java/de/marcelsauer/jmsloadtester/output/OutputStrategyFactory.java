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
package de.marcelsauer.jmsloadtester.output;

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
