package de.marcelsauer.jmsloadtester.tracker;

import java.util.Set;

import de.marcelsauer.jmsloadtester.message.MessageNotifyable;
import de.marcelsauer.jmsloadtester.message.MessageSentAware;

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
