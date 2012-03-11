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
package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayUtilsTest extends AbstractJmsLoaderTest {

    @Test
    public void testFindByteArrayByteArray() {
        byte[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
        assertEquals(18, ArrayUtils.find(a, "STU".getBytes()));
    }

    @Test
    public void testFindByteArrayIntByteArray() {
        byte[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
        assertEquals(18, ArrayUtils.find(a, 0, "STU".getBytes()));
        assertEquals(44, ArrayUtils.find(a, 19, "STU".getBytes()));
    }

    @Test
    public void testFindByteArrayIntIntByteArray() {
        byte[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();

        assertEquals(3, ArrayUtils.find(a, 0, 26, "DEF".getBytes()));
        assertEquals(-1, ArrayUtils.find(a, 0, 26, "FED".getBytes()));
        assertEquals(29, ArrayUtils.find(a, 4, 52, "DEF".getBytes()));
    }

    @Test
    public void testAreaMatches() {
        byte[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
        byte[] b = "FGH".getBytes();

        assertTrue(ArrayUtils.areaMatches(a, 5, b));
        assertFalse(ArrayUtils.areaMatches(a, 4, b));
    }

    @Test
    public void testReplaceAll() {
        byte[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
        byte[] b = "ABCDEFGHIJKLMNOPQRMARCELVWXYZABCDEFGHIJKLMNOPQRMARCELVWXYZ".getBytes();
        byte[] c = ArrayUtils.replaceAll(a, "STU".getBytes(), "MARCEL".getBytes());
        assertEquals(new String(b), new String(c));
        c = ArrayUtils.replaceAll(a, "SPU".getBytes(), "MARCEL".getBytes());
        assertEquals(new String(a), new String(c));
    }

    @Test
    public void testReplaceFirst() {
        byte[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
        byte[] b = "ABCDEFGHIJKLMNOPQRMARCELVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
        byte[] c = ArrayUtils.replaceFirst(a, "STU".getBytes(), "MARCEL".getBytes());
        assertEquals(new String(b), new String(c));
        c = ArrayUtils.replaceAll(a, "SPU".getBytes(), "MARCEL".getBytes());
        assertEquals(new String(a), new String(c));
    }

    @Test
    public void testReplaceAllUsingRealPattern() {
        byte[] a = "ABCDEFGHIJKLMNOPQR[[PLACEHOLDER]][[PLACEHOLDER]]STUVWXYZABCDEFGHIJKLMNOPQR[[PLACEHOLDER]]STUVWXYZ".getBytes();
        byte[] b = "ABCDEFGHIJKLMNOPQRBANGBANGSTUVWXYZABCDEFGHIJKLMNOPQRBANGSTUVWXYZ".getBytes();
        byte[] c = ArrayUtils.replaceAll(a, "[[PLACEHOLDER]]".getBytes(), "BANG".getBytes());
        assertEquals(new String(b), new String(c));
    }

    @Test
    public void testContains() {
        byte[] a = "ABCDEFGHIJKLMNOPQR[[PLACEHOLDER]][[PLACEHOLDER]]STUVWXYZABCDEFGHIJKLMNOPQR[[PLACEHOLDER]]STUVWXYZ".getBytes();
        byte[] b = "[[".getBytes();
        byte[] c = "".getBytes();

        assertTrue(ArrayUtils.contains(a, "[".getBytes()[0]));
        assertFalse(ArrayUtils.contains(a, "@".getBytes()[0]));

        assertTrue(ArrayUtils.contains(b, "[".getBytes()[0]));
        assertFalse(ArrayUtils.contains(b, " ".getBytes()[0]));

        assertFalse(ArrayUtils.contains(c, "[".getBytes()[0]));
        assertFalse(ArrayUtils.contains(c, "]".getBytes()[0]));
    }

}
