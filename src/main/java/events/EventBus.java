package events;

import java.util.ArrayList;
import java.util.List;

/**
 * Event bus for handling events.
 * This class is responsible for registering event handlers and posting events
 * to these handlers. 
 */
public class EventBus {
	
	private List<EventHandler> handlers = new ArrayList<>();
	
	/**
	 * Register an event handler.
	 * @param handler The event handler to register.
	 */
	public void register(EventHandler handler) {
		handlers.add(handler);
	}
	
	/**
	 * Post an event to all registered handlers.
	 * @param event The event to post.
	 */
	public void post(Event event) {
		for (EventHandler handler : handlers) {
			handler.handle(event);
		}
	}

}
