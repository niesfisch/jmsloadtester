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
package de.marcelsauer.jmsloadtester.message;

public class JmsMessage {

    private Payload message;
    private String destination;

    public JmsMessage(final Payload message, final String destination) {
        this.message = message;
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public Payload getMessage() {
        return message;
    }

    public void setMessage(final Payload message) {
        this.message = message;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(message);
        sb.append(destination);
        return sb.toString();
    }
}
