package de.unikassel.projectoma.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Random;

public class Food extends Article {

    private Daytime daytime;
    private boolean isHealty;
    private boolean isHeavy;
    private FoodType type;



    public Food(String name, Timestamp duration) {
	this.setName(name);
	this.setDuration(duration);
	super.ArticleType = RequestType.eat;
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
    
    public FoodType getType() {
	return type;
    }
    public void setType(FoodType type) {
	this.type = type;
    }
    public Food withType(FoodType type) {
	this.type = type;
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
		    .withType(FoodType.EGGS_AND_BACON)
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
		    .withType(FoodType.CAKE_WITH_CHANTILLY_CREAM)
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
		    .withType(FoodType.CEREALS_AND_MILK)
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
		    .withType(FoodType.FRUIT)
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
		    .withType(FoodType.VITAMINE_SHAKE)
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
		    .withType(FoodType.SCHNITZEL_AND_FRENCH_FRIES)
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
		    .withType(FoodType.BOILED_COD_FISH_WITH_POTATOES)
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
		    .withType(FoodType.KNUCKLE_OF_PORK)
		    );
	    put(
		    FoodType.CANNELLONI_WITH_CHEESE_SAUCES,
		    new Food(
			    "Cannelloni mit Käsesauce",
			    new Timestamp(0, 0, 0, 0, 30, 0, 0)
			    )
		    .withDaytime(Daytime.MIDDAY)
		    .withHeavy(false)
		    .withHealty(true)
		    .withType(FoodType.CANNELLONI_WITH_CHEESE_SAUCES)
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
		    .withType(FoodType.SUSHI)
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
		    .withType(FoodType.NOODLE_SOUP)
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
		    .withType(FoodType.ROLLMOP_HERRINGS_ON_BREAD)
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
		    .withType(FoodType.BREAD_AND_BUTTER)
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
		    .withType(FoodType.TOAST_HAWAI)
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
		    .withType(FoodType.SALAD)
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
		    .withType(FoodType.RICE_CRACKERS_WITH_CHEESE)
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
		    .withType(FoodType.PUMPKIN_SOUP)
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
		    .withType(FoodType.BEER_JELLY)
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
		    .withType(FoodType.HALF_A_PIG_ON_TOAST)
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
		    .withType(FoodType.FOOD_OF_THE_GODS)
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
		    .withType(FoodType.RAABERDATSCHI)
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
		    .withType(FoodType.TOTE_OMA)
		    );
	}};

	public static Food randomFood(Daytime dt) {
	    FoodType[] range;
	    Random rand = new Random();

	    switch (dt) {
	    case MORNING:
		range = new FoodType[] {
			FoodType.EGGS_AND_BACON,
			FoodType.CAKE_WITH_CHANTILLY_CREAM,
			FoodType.CEREALS_AND_MILK,
			FoodType.FRUIT,
			FoodType.VITAMINE_SHAKE
		};
		break;
	    case MIDDAY:
		range = new FoodType[] {
			FoodType.SCHNITZEL_AND_FRENCH_FRIES,
			FoodType.BOILED_COD_FISH_WITH_POTATOES,
			FoodType.KNUCKLE_OF_PORK,
			FoodType.CANNELLONI_WITH_CHEESE_SAUCES,
			FoodType.SUSHI,
			FoodType.NOODLE_SOUP
		};
		break;
	    case EVENING:
		range = new FoodType[] {
			FoodType.ROLLMOP_HERRINGS_ON_BREAD,
			FoodType.BREAD_AND_BUTTER,
			FoodType.TOAST_HAWAI,
			FoodType.SALAD,
			FoodType.RICE_CRACKERS_WITH_CHEESE,
			FoodType.PUMPKIN_SOUP
		};
		break;
	    default:
		range = new FoodType[] {
			FoodType.BEER_JELLY,
			FoodType.HALF_A_PIG_ON_TOAST,
			FoodType.FOOD_OF_THE_GODS,
			FoodType.RAABERDATSCHI,
			FoodType.TOTE_OMA
		};
	    }

	    return FoodList.get(range[rand.nextInt(range.length)]);
	}

}
