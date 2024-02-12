package events;

@FunctionalInterface
public interface EventHandler {
	
	void handle(Event event);
	
}
