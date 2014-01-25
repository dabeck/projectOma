package de.unikassel.projectoma.model;

import java.sql.Timestamp;
import java.util.HashMap;

public class Food extends Article {

	private Daytime daytime;
	private boolean isHealty;
	private boolean isHeavy;
	
	
	
	
	public Food(String name, Timestamp duration) {
		this.setName(name);
		this.setDuration(duration);
	}
	
	
	

	public Daytime getDaytime() {
		return daytime;
	}

	public void setDaytime(Daytime daytime) {
		this.daytime = daytime;
	}
	
	public Food withDaytime(Daytime daytime) {
		this.daytime = daytime;
		return this;
	}

	public boolean isHealty() {
		return isHealty;
	}

	public void setHealty(boolean isHealty) {
		this.isHealty = isHealty;
	}
	
	public Food withHealty(boolean isHealty) {
		this.isHealty = isHealty;
		return this;
	}

	public boolean isHeavy() {
		return isHeavy;
	}

	public void setHeavy(boolean isHeavy) {
		this.isHeavy = isHeavy;
	}
	
	public Food withHeavy(boolean isHeavy) {
		this.isHeavy = isHeavy;
		return this;
	}
	
	
	
	public static HashMap<FoodType, Food> FoodList = new HashMap<FoodType, Food>() {
		
		/**
         * 
         */
		private static final long serialVersionUID = 1L;
		
		{
		/* Morgens */
		put(
				FoodType.EGGS_AND_BACON,
				new Food(
"Rührei mit Speck",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MORNING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.CAKE_WITH_CHANTILLY_CREAM,
				new Food(
						"Kuchen mit Sahne",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MORNING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.CEREALS_AND_MILK,
				new Food(
"Müsli mit Milch",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.MORNING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.FRUIT,
				new Food(
						"Obst",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MORNING)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.VITAMINE_SHAKE,
				new Food(
						"Vitaminshake",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.MORNING)
					.withHeavy(false)
					.withHealty(true)
				);
		
		/* Mittags */
		put(
				FoodType.SCHNITZEL_AND_FRENCH_FRIES,
				new Food(
						"Schnitzel mit Pommes",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.BOILED_COD_FISH_WITH_POTATOES,
				new Food(
						"Kabeljau mit Kartoffeln",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.KNUCKLE_OF_PORK,
				new Food(
						"Schweinshaxe",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.CANNELLONI_WITH_CHEESE_SAUCES,
				new Food(
						"Cannelloni mit K�sesauce",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.SUSHI,
				new Food(
						"Sushi",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.NOODLE_SOUP,
				new Food(
						"Nudelsuppe",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(true)
					.withHealty(false)
				);
		
		/* Abends */
		put(
				FoodType.ROLLMOP_HERRINGS_ON_BREAD,
				new Food(
						"Rollmopsauf Brot",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.BREAD_AND_BUTTER,
				new Food(
						"Butterbrot",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.TOAST_HAWAI,
				new Food(
						"Toast Hawai",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(false)
					.withHealty(true)
				);
		put(
				FoodType.SALAD,
				new Food(
						"Salat",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.RICE_CRACKERS_WITH_CHEESE,
				new Food(
"Reiscracker mit Käse",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.PUMPKIN_SOUP,
				new Food(
"Kürbissuppe",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(true)
					.withHealty(false)
				);
		
		/* Extra */
		put(
				FoodType.BEER_JELLY,
				new Food(
"Biersülze",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.HALF_A_PIG_ON_TOAST,
				new Food(
						"1/2 Schwein auf Toast",
						new Timestamp(0, 0, 0, 1, 0, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.FOOD_OF_THE_GODS,
				new Food(
						"Wackelpeter",
						new Timestamp(0, 0, 0, 0, 6, 0, 0)
						)
					.withDaytime(Daytime.MORNING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.RAABERDATSCHI,
				new Food(
						"Raaberdatschi",
						new Timestamp(0, 0, 0, 0, 15, 0, 0)
						)
					.withDaytime(Daytime.EVENING)
					.withHeavy(true)
					.withHealty(false)
				);
		put(
				FoodType.TOTE_OMA,
				new Food(
						"Tote Oma",
						new Timestamp(0, 0, 0, 0, 30, 0, 0)
						)
					.withDaytime(Daytime.MIDDAY)
					.withHeavy(true)
					.withHealty(false)
				);
	}};
}
