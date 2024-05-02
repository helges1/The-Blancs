package model.lasers;

@FunctionalInterface
public interface Cannon {
	
	/**
	 * Fire the cannon.
	 * 
	 * @return a Laser[]. Can include multiple, one, or even zero Laser-objects. 
	 */
	Laser[] fireCannon();

}
