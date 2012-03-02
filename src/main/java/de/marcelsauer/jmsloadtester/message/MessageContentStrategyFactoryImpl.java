package de.marcelsauer.jmsloadtester.message;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * 
 * This file is part of JMS Load Tester.
 * 
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
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
