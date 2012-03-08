package de.marcelsauer.jmsloadtester.output;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
public class OutputStrategyFactoryTest extends AbstractJmsLoaderTest {

    @Test
    public void testFactoryBehaviour() {
        assertTrue(OutputStrategyFactory.getOutputStrategy("STDOUT") instanceof StdoutOutputStrategy);
        assertTrue(OutputStrategyFactory.getOutputStrategy("STDERR") instanceof StderrOutputStrategy);
        assertTrue(OutputStrategyFactory.getOutputStrategy("SILENT") instanceof SilentOutputStrategy);
        assertTrue(OutputStrategyFactory.getOutputStrategy("FILE") instanceof FileOutputStrategy);

        try {
            OutputStrategyFactory.getOutputStrategy("NOT THERE");
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
