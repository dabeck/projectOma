package de.unikassel.projectoma.model;

public enum RequestType {
   eat(1),
   drink(2),
   wash_dishes(3),
   wash_clothes(4),
   sleep(5),
   medicine(6),
   walk(7),
   clean_car(8),
   music(9),
   shopping(10);

	private final int value;
	private RequestType(int value) {
		this.value = value;
	}

	/**
	 * Get the integer representation of the enum's constant.
	 *
	 * @return
	 */
	public int getValue() {
		return value;
	}

}
