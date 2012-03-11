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
package de.marcelsauer.jmsloadtester.message;

public class MessageContentStrategyFactoryImpl implements MessageContentStrategyFactory {

    private final static String FOLDER = "FOLDER";
    private final static String STATIC = "STATIC";
    private final static String SIZE = "SIZE";
    private final static String HASH = "#";

    public MessageContentStrategy getMessageContentStrategy(final String type) {
        if (type == null) {
            throw new IllegalArgumentException("the message content strategy was null");
        }
        MessageContentStrategy strategy = null;
        if (type.indexOf(FOLDER) > -1) {
            strategy = new FolderMessageContentStrategy(getDirectory(type), getRegex(type), getFileSentAmount(type));
        } else if (type.indexOf(STATIC) > -1) {
            strategy = new StaticMessageContentStrategy(getStaticMessage(type), getMessageCount(type, STATIC));
        } else if (type.indexOf(SIZE) > -1) {
            strategy = new SizeMessageContentStrategy(getBytes(type), getMessageCount(type, SIZE));
        } else {
            throw new IllegalArgumentException("the message content strategy does not exist, was: " + type);
        }
        return strategy;
    }

    private String getDirectory(final String type) {
        String tmp = getDefinition(type, FOLDER);
        return tmp.split(HASH)[2];
    }

    private int getFileSentAmount(final String type) {
        String tmp = getDefinition(type, FOLDER);
        return Integer.valueOf(tmp.split(HASH)[0]);
    }

    private String getRegex(final String type) {
        String tmp = getDefinition(type, FOLDER);
        return tmp.split(HASH)[1];
    }

    private String getStaticMessage(final String type) {
        String tmp = getDefinition(type, STATIC);
        return tmp.split(HASH)[1];
    }

    private int getBytes(final String type) {
        String tmp = getDefinition(type, SIZE);
        return Integer.valueOf(tmp.split(HASH)[1]);
    }

    private int getMessageCount(final String type, final String strategy) {
        String tmp = getDefinition(type, strategy);
        return Integer.valueOf(tmp.split(HASH)[0]);
    }

    private String getDefinition(final String type, final String prefix) {
        return type.replaceAll(prefix + HASH, "");
    }

}
