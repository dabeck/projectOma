package de.unikassel.projectoma.model;

public enum Daytime {
    /* MORGEN */
    MORNING(0),
    /* MITTAG */
    MIDDAY(1),
    /* ABEND */
    EVENING(2);

    private final int value;
    private Daytime(int value) {
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
