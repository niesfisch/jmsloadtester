package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
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
public class StringUtilsTest extends AbstractJmsLoaderTest {

    private enum ENUMS {
        A, B
    }

    ;

    @Test
    public void testIsEmpty() {
        final ENUMS a = null;
        final ENUMS b = ENUMS.A;
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(" "));
        assertTrue(StringUtils.isEmpty("     "));
        assertTrue(StringUtils.isEmpty((Object) null));
        assertTrue(StringUtils.isEmpty(a));

        assertFalse(StringUtils.isEmpty("a"));
        assertFalse(StringUtils.isEmpty(" a"));
        assertFalse(StringUtils.isEmpty("     ."));
        assertFalse(StringUtils.isEmpty(22));
        assertFalse(StringUtils.isEmpty(b));
    }
}
