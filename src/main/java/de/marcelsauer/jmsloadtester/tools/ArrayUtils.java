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

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ArrayUtils {

    public static byte[] replaceFirst(byte[] arrayToSearch, byte[] pattern, byte[] replacement) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int offset = replaceNext(arrayToSearch, pattern, replacement, output, 0);
        output.write(arrayToSearch, offset, arrayToSearch.length - offset);
        return output.toByteArray();
    }

    public static byte[] replaceAll(byte[] arrayToSearch, byte[] pattern, byte[] replacement) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int offset = 0;
        while (offset > -1) {
            offset = replaceNext(arrayToSearch, pattern, replacement, output, offset);
        }

        return output.toByteArray();
    }

    private static int replaceNext(byte[] arrayToSearch, byte[] pattern, byte[] replacement, ByteArrayOutputStream output, int offset) {
        int i = find(arrayToSearch, offset, pattern);
        if (i == -1) {
            output.write(arrayToSearch, offset, arrayToSearch.length - offset);
            offset = -1;
        } else {
            output.write(arrayToSearch, offset, i - offset);
            output.write(replacement, 0, replacement.length);
            offset = i + pattern.length;
        }
        return offset;
    }

    public static int find(byte[] arrayToSearch, byte[] pattern) {
        return find(arrayToSearch, 0, arrayToSearch.length, pattern);
    }

    public static int find(byte[] arrayToSearch, int offset, byte[] pattern) {
        return find(arrayToSearch, offset, arrayToSearch.length, pattern);
    }

    public static int find(byte[] arrayToSearch, int offset, int limit, byte[] pattern) {
        if (pattern.length == 0 || arrayToSearch.length == 0)
            return -1;

        byte firstByte = pattern[0];

        for (int i = offset; i < limit; i++) {
            if (arrayToSearch[i] == firstByte) {
                if (areaMatches(arrayToSearch, i, pattern)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean areaMatches(byte[] arrayToSearch, int startPosition, byte[] pattern) {
        if (arrayToSearch.length < startPosition + pattern.length) {
            return false;
        }

        for (int i = 0; i < pattern.length; i++) {
            if (arrayToSearch[i + startPosition] != pattern[i])
                return false;
        }

        return true;
    }

    public static boolean contains(byte[] array, byte toSearch) {
        byte[] copy = new byte[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        Arrays.sort(copy);
        return Arrays.binarySearch(copy, toSearch) >= 0;
    }
}
