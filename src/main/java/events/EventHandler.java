package events;

/**
 * Functional interface for handling events.An event handler is responsible for
 * defining the behavior when an event is received.
 */
@FunctionalInterface
public interface EventHandler {
	
	/**
	 * Handle an event.
	 * @param event The event to handle.
	 */
	void handle(Event event);
	
}
