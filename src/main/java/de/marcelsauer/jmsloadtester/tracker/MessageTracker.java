package de.marcelsauer.jmsloadtester.tracker;

import de.marcelsauer.jmsloadtester.message.MessageNotifyable;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;

import java.util.Set;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer
 * <marcel[underscore]sauer[at]gmx.de>
 * <p/>
 * This file is part of JMS Load Tester.
 * <p/>
 * JMS Load Tester is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p/>
 * JMS Load Tester is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * JMS Load Tester. If not, see <http://www.gnu.org/licenses/>.
 */
public interface MessageTracker extends MessageNotifyable, MessageSentAware {

    void setTotalMessagesToBeSent(int totalMessagesToBeSent);

    void setTotalMessagesToBeReceived(int totalMessagesToBeReceived);

    void setSenderTimeTracker(TimeTracker senderTimeTracker);

    void setListenerTimeTracker(TimeTracker listenerTimeTracker);

    int getTotalMessagesReceived();

    int getTotalMessagesSent();

    boolean isAllReceived();

    boolean isAllSent();

    Set<String> getMessagesReceived();

    Set<String> getMessagesSent();
}
