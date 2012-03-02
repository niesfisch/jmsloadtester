package de.marcelsauer.jmsloadtester.message;

import java.util.Arrays;
import java.util.Iterator;

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
public class SizeMessageContentStrategy implements MessageContentStrategy {

    private int messageCount;
    private int bytes;
    private int count;
    private Payload message;

    public SizeMessageContentStrategy(final int bytes, final int messageCount) {
        if(bytes < 0){
            throw new IllegalArgumentException("the bytes must be greater than zero");
        }
        this.messageCount = messageCount;
        this.bytes = bytes;
        generateMessage();
    }

    public Payload next() {
        return message;
    }

    public boolean hasNext() {
        return count++ < messageCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public String getDescription() {
        return "Size: always returning random " + bytes + " bytes as message content";
    }

    @Override
    public String toString() {
        return getDescription();
    }

    private void generateMessage() {
        byte[] data = new byte[bytes];
        Arrays.fill(data, "1".getBytes()[0]);     
        this.message = new Payload(data);
    }

    public void remove() {
        throw new UnsupportedOperationException("not supported");
    }

    public Iterator<Payload> iterator() {
        return this;
    }
}
