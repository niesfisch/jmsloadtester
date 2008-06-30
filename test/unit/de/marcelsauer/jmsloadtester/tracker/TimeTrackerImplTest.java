package de.marcelsauer.jmsloadtester.tracker;

import de.marcelsauer.jmsloadtester.AbstractJmsLoaderTest;

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
public class TimeTrackerImplTest extends AbstractJmsLoaderTest {

    private TimeTracker tracker;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tracker = new TimeTrackerImpl();
    }

    public void testGetDuration() throws InterruptedException {
        final int sleepDurationMillis = 1000;

        tracker.start();
        Thread.sleep(sleepDurationMillis);
        tracker.stop();

        double nanos = tracker.getDurationInNanoSeconds();
        double expectedMillis = nanos / (1000 * 1000);
        double expectedSeconds = expectedMillis / 1000;

        assertTrue(inDeltaRange(nanos, sleepDurationMillis * 1000 * 1000, (1000 * 1000) * 10));
        assertTrue(expectedMillis == tracker.getDurationInMilliSeconds());
        assertTrue(expectedSeconds == tracker.getDurationInSeconds());
    }

    private boolean inDeltaRange(double actual, double expected, double delta) {
        return (Math.abs(actual - expected)) <= delta;
    }

}
