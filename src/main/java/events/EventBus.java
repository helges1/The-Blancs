package events;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
	
	private List<EventHandler> handlers = new ArrayList<>();
	
	public void register(EventHandler handler) {
		handlers.add(handler);
	}
	
	public void post(Event event) {
		for (EventHandler handler : handlers) {
			handler.handle(event);
		}
	}

}
