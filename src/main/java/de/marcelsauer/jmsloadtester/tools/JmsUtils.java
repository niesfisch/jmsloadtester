package de.marcelsauer.jmsloadtester.tools;

import de.marcelsauer.jmsloadtester.core.JmsException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * JMS Load Tester Copyright (C) 2008 Marcel Sauer <marcel_sauer@gmx.de>
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
public class JmsUtils {

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                Logger.debug("closing connection: " + con);
                con.close();
            } catch (JMSException e) {
                throw new JmsException("could not close connection", e);
            } finally {
                con = null;
            }
        }
    }

    public static void closeSession(Session sess) {
        if (sess != null) {
            try {
                Logger.debug("closing session: " + sess);
                sess.close();
            } catch (JMSException e) {
                throw new JmsException("could not close session", e);
            } finally {
                sess = null;
            }
        }
    }
}
