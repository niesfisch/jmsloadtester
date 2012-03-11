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
package de.marcelsauer.jmsloadtester.tracker;

import de.marcelsauer.jmsloadtester.core.Constants;

public class TimeTrackerImpl implements TimeTracker {

    // start the timer
    private long startTime;
    private long endTime;
    private boolean finished;

    @Override
    public synchronized void start() {
        startTime = System.nanoTime();
    }

    @Override
    public synchronized void stop() {
        endTime = System.nanoTime();
        finished = true;
    }

    @Override
    public synchronized double getDurationInSeconds() {
        return getDurationInMilliSeconds() / Constants.MILLIS_FACTOR;
    }

    @Override
    public synchronized double getDurationInMilliSeconds() {
        return getDurationInNanoSeconds() / (Constants.MILLIS_FACTOR * Constants.MILLIS_FACTOR);
    }

    @Override
    public synchronized double getDurationInNanoSeconds() {
        if (!finished) {
            return System.nanoTime() - startTime;
        } else {
            // last message was sent
            return endTime - startTime;
        }
    }

}
