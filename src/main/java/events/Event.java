package events;

import javax.swing.event.HyperlinkEvent.EventType;

/**
 * Interface for events.
 */
public interface Event {

    void execute();

    EventType getType();

}
