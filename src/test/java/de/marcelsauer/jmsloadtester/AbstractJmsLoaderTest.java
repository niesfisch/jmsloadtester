package de.marcelsauer.jmsloadtester;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

import de.marcelsauer.jmsloadtester.output.OutputStrategy;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;

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
public abstract class AbstractJmsLoaderTest extends TestCase {

    protected OutputStrategy outputStrategy;
    protected MessageTracker messageTracker;
    protected ThreadTracker threadTracker;

    protected List<Object> mocks = new ArrayList<Object>();

    public AbstractJmsLoaderTest() {
        outputStrategy = EasyMock.createMock(OutputStrategy.class);
        Logger.setOut(outputStrategy);
    }

    protected void replay(Object mock) {
        EasyMock.replay(mock);
    }

    protected void replay() {
        for (Object mock : mocks) {
            EasyMock.replay(mock);
        }
    }

    protected void verify(Object mock) {
        EasyMock.verify(mock);
    }

    protected void verify() {
        for (Object mock : mocks) {
            EasyMock.verify(mock);
        }
    }

    protected void reset() {
        for (Object mock : mocks) {
            EasyMock.reset(mock);
        }
    }

    protected <T> T createMockOfType(Class<T> mockType) {
        T mock = EasyMock.createMock(mockType);
        mocks.add(mock);
        return mock;
    }

    protected <T> T createNiceMockOfType(Class<T> mockType) {
        T mock = EasyMock.createNiceMock(mockType);
        mocks.add(mock);
        return mock;
    }

    protected void mockMessageTracker() {
        messageTracker = createMockOfType(MessageTracker.class);
    }

    protected void mockThreadTracker() {
        threadTracker = createMockOfType(ThreadTracker.class);
    }

}
