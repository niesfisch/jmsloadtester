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

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.tools.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

public class FileOutputStrategyTest extends AbstractJmsLoaderTest {

    private OutputStrategy strategy;
    private final String testfile = System.getProperty("java.io.tmpdir") + Constants.SEP + "test.tmp";

    @Before
    public void setUp() throws Exception {

        new File(testfile).delete();
        strategy = new FileOutputStrategy(testfile);
    }

    @Test
    public void testOutput() throws FileNotFoundException {
        strategy.output("test");
        String contents = FileUtils.getFileContents(new File(testfile));
        String expected = "test" + Constants.EOL;
        assertTrue(contents != null && expected.equals(contents));
    }
}
