package de.marcelsauer.jmsloadtester.message;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SizeMessageContentStrategyTest extends AbstractJmsLoaderTest {

    @Test
    public void testGetNext() {
        assertTrue(getStrategy(1000, 1).next().asBytes().length == 1000);
        assertTrue(getStrategy(1000, 1).next().asBytes().length == 1000);

        assertTrue(getStrategy(1, 1).next().asBytes().length == 1);
        assertTrue(getStrategy(1, 1).next().asBytes().length == 1);

        assertTrue(getStrategy(0, 1).next().asBytes().length == 0);
        assertTrue(getStrategy(0, 1).next().asBytes().length == 0);

        assertTrue(getStrategy(0, 0).next().asBytes().length == 0);
        assertTrue(getStrategy(0, 0).next().asBytes().length == 0);

        assertTrue(getStrategy(0, 999).next().asBytes().length == 0);
        assertTrue(getStrategy(0, 999).next().asBytes().length == 0);
    }

    @Test
    public void testExceptionBehaviour() {
        try {
            getStrategy(-1, 0);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    private MessageContentStrategy getStrategy(int bytes, int count) {
        return new SizeMessageContentStrategy(bytes, count);
    }

}
