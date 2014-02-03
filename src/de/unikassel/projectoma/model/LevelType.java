package de.unikassel.projectoma.model;

public enum LevelType {
    NONE(0),
    SIMPLE(1),
    MEDIUM(2),
    HARD(3);

    private final int value;
    private LevelType(int value) {
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
