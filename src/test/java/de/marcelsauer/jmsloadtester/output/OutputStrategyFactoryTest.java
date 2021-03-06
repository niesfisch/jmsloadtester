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
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
