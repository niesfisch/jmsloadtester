package de.marcelsauer.jmsloadtester.tracker;

import de.marcelsauer.jmsloadtester.core.Constants;

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
public class TimeTrackerImpl implements TimeTracker {

    // start the timer
    private long startTime;
    private long endTime;
    private boolean finished;

    public synchronized void start() {
        startTime = System.nanoTime();
    }

    public synchronized void stop() {
        endTime = System.nanoTime();
        finished = true;
    }

    public synchronized double getDurationInSeconds() {
        return getDurationInMilliSeconds() / Constants.MILLIS_FACTOR;
    }

    public synchronized double getDurationInMilliSeconds() {
        return getDurationInNanoSeconds() / (Constants.MILLIS_FACTOR * Constants.MILLIS_FACTOR);
    }

    public synchronized double getDurationInNanoSeconds() {
        if (!finished) {
            return System.nanoTime() - startTime;
        } else {
            // last message was sent
            return endTime - startTime;
        }
    }

}
