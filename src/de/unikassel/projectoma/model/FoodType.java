package de.unikassel.projectoma.model;

public enum FoodType {

	/* MORGENS */
	
	/* Ruehrei mit Speck */
	EGGS_AND_BACON(0),
	/* Kuchen mit Sahne */
	CAKE_WITH_CHANTILLY_CREAM(1),
	/* Muesli mit Milch */
	CEREALS_AND_MILK(2),
	/* Obst */
	FRUIT(3),
	/* Vitaminshake */
	VITAMINE_SHAKE(4),
	
	
	/* MITTAGS */
	
	/* Schnitzel mit Pommes */
	SCHNITZEL_AND_FRENCH_FRIES(5),
	/* Kabeljau mit Kartoffeln */
	BOILED_COD_FISH_WITH_POTATOES(6),
	/* Schweinshaxe */
	KNUCKLE_OF_PORK(7),
	/* Cannelloni mit Kaesesauce */
	CANNELLONI_WITH_CHEESE_SAUCES(8),
	/* Sushi */
	SUSHI(9),
	/* Nudelsuppe */
	NOODLE_SOUP(10),
	
	
	/* ABENDS */
	
	/* Rollmops auf Brot */
	ROLLMOP_HERRINGS_ON_BREAD(11),
	/* Butterbrot */
	BREAD_AND_BUTTER(12),
	/* Toast Hawai */
	TOAST_HAWAI(13),
	/* Salat */
	SALAD(14),
	/* Reiscracker mit Kaese */
	RICE_CRACKERS_WITH_CHEESE(15),
	/* Kuerbissuppe */
	PUMPKIN_SOUP(16),
	
	
	/* lustige Gerichte als besondere Abwechslung */
	
	/* Biersuelze */
	BEER_JELLY(17),
	/* 1/2 Schwein auf Toast */
	HALF_A_PIG_ON_TOAST(18),
	/* Wackelpeter */
	FOOD_OF_THE_GODS(19),
	/* Raaberdatschi */
	RAABERDATSCHI(20),
	/* Tote Oma */
	TOTE_OMA(21);
	

	private final int value;
	private FoodType(int value) {
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
