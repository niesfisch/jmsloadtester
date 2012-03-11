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
package de.marcelsauer.jmsloadtester;

import de.marcelsauer.jmsloadtester.output.OutputStrategy;
import de.marcelsauer.jmsloadtester.tools.Logger;
import de.marcelsauer.jmsloadtester.tracker.MessageTracker;
import de.marcelsauer.jmsloadtester.tracker.ThreadTracker;
import org.easymock.classextension.EasyMock;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJmsLoaderTest {

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
