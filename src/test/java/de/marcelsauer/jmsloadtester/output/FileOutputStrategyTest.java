package de.marcelsauer.jmsloadtester.output;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import de.marcelsauer.jmsloadtester.core.Constants;
import de.marcelsauer.jmsloadtester.tools.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

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
