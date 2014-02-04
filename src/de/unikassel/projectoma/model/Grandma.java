package de.unikassel.projectoma.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class Grandma {

	/* Name der Oma. */
	private String name;
	/* Schwierigkeitsgrad des Spiels. */
	private LevelType level;
	/* Wunsch, welcher aktuell abgearbeitet wird. */
	private Article currentAction;
	
	/* Android Alarms */
	private HashMap<Intent, PendingIntent> alarms;
	
	/* Wuensche */
	private List<Article> wishes;
	/* Einkaufsliste */
	private List<Article> shoppingList;
	
	/* Speisekammer */
	private List<Food> pantry;
	/* Vorratskammer */
	private List<Article> inventory;
	/* Kleiderschrank */
	private int warderobeCount;
	
	protected PropertyChangeSupport propertyChangeSupport;
	
	
	
	
	public void save(SharedPreferences sp) {
		Editor edit = sp.edit();
		Gson gson = new Gson();
		String json = gson.toJson(this);
		edit.putString("de.unikassel.projectoma.grandma", json);
		edit.commit();
	}

	public static Grandma load(SharedPreferences sp) {
		Gson gson = new Gson();
		String json = sp.getString("de.unikassel.projectoma.grandma", "");
		return gson.fromJson(json, Grandma.class);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
	    	String old = this.name;
		this.name = name;
		propertyChangeSupport.firePropertyChange("name", old, name);
	}
	public Grandma withName(String name) {
		this.name = name;
		return this;
	}
	
	public LevelType getLevel() {
		return level;
	}
	public void setLevel(LevelType level) {
	    	LevelType old = this.level;
		this.level = level;
		propertyChangeSupport.firePropertyChange("level", old, level);
	}
	public Grandma withLevel(LevelType level) {
		this.level = level;
		return this;
	}
	
	public Article getCurrentAction() {
		return currentAction;
	}
	public void setCurrentAction(Article currentAction) {
	    	Article old = this.currentAction;
		this.currentAction = currentAction;
		propertyChangeSupport.firePropertyChange("currentAction", old, currentAction);
	}
	public Grandma withCurrentAction(Article currentAction) {
		this.currentAction = currentAction;
		return this;
	}
	
	public HashMap<Intent, PendingIntent> getAlarms() {
	    return alarms;
	}
	public void setAlarms(HashMap<Intent, PendingIntent> alarms) {
	    this.alarms = alarms;
	}
	public Grandma withAlarms(HashMap<Intent, PendingIntent> alarms) {
	    this.alarms = alarms;
	    return this;
	}
	
	
	public void addWish(Article wish) {
	    List<Article> old = this.wishes;
	    this.wishes.add(wish);
	    propertyChangeSupport.firePropertyChange("wishes", old, wishes);
	}
	public void removeWish(Article wish) {
	    List<Article> old = this.wishes;
	    this.wishes.remove(wish);
	    propertyChangeSupport.firePropertyChange("wishes", old, wishes);
	}
	public List<Article> getWishList() {
	    return this.wishes;
	}
	
	public List<Article> getShoppingList() {
		return shoppingList;
	}
	public void setShoppingList(List<Article> shoppingList) {
		this.shoppingList = shoppingList;
	}
	public Grandma withShoppingList(List<Article> shoppingList) {
		this.shoppingList = shoppingList;
		return this;
	}
	
	public List<Food> getPantry() {
		return pantry;
	}
	public void setPantry(List<Food> pantry) {
		this.pantry = pantry;
	}
	public Grandma withPantry(List<Food> pantry) {
		this.pantry = pantry;
		return this;
	}
	
	public List<Article> getInventory() {
		return inventory;
	}
	public void setInventory(List<Article> inventory) {
		this.inventory = inventory;
	}
	public Grandma withInventory(List<Article> inventory) {
		this.inventory = inventory;
		return this;
	}
	
	public int getWarderobeCount() {
	    return warderobeCount;
	}
	public void setWarderobeCount(int warderobeCount) {
	    this.warderobeCount = warderobeCount;
	}
	public Grandma withWarderobeCount(int warderobeCount) {
	    this.warderobeCount = warderobeCount;
	    return this;
	}
	
	
	
	
	
	public Grandma() {
	}

	public Grandma(String name, LevelType level) {
		this.name = name;
		this.level = level;
		this.warderobeCount = 7;
		
		this.alarms = new HashMap<Intent, PendingIntent>();
		
		this.wishes = new ArrayList<Article>();
		
		this.shoppingList = new ArrayList<Article>();
		
		this.pantry = new ArrayList<Food>();
		
		this.inventory = new ArrayList<Article>();
		
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	
	
	// essen
	public boolean eat(Food f) {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    Article wish = null;
	    for (Article wishItem: this.getWishList()) {
		if (wishItem instanceof Food && ((Food)wishItem).getType() == f.getType()) {
		    wish = wishItem;
		}
	    }
	    
	    // checke ob Essen vorraetig
	    for (Food availableFood: this.getPantry()) {
		if (availableFood.getType() == f.getType()) {
		    this.getPantry().remove(availableFood);
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	// trinken
	public boolean drink(Drink d) {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof Drink) {
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	// medicine
	public boolean cure(Medicine m) {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof Medicine && ((Medicine)wish).getTyp() == m.getTyp()) {
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	// kaufen
	public void buy(Article a) {
	    if (a instanceof Food) {
		this.pantry.add((Food)a);
	    } else {
		this.inventory.add(a);
	    }
	}
	
	
	 // einkleiden
	public boolean clothe() {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof Clothing) {
		    this.warderobeCount--;
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}

	// washen
	public boolean washClothes() {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof Washer) {
		    this.warderobeCount = 7;
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	// putzen
	public boolean clean() {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof House) {
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	// spuelen
	public boolean washDishes() {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof Dishes) {
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	//schlafen
	public boolean sleep() {
	    if (this.getCurrentAction() != null)
		return false;
	    
	    // suche entsprechenden Wunsch
	    for (Article wish: this.getWishList()) {
		if (wish instanceof Bed) {
		    this.setCurrentAction(wish);
		    return true;
		}
	    }
	    
	    return false;
	}
	
	// default-Zustand (null)
	public void idle() {
	    this.setCurrentAction(null);
	}

	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	
	/*
	public void fernsehn() {
		System.out.println("TODO: Oma.fernsehn();");
	}
	//public void gesundEssen(Essen e) {
	//	System.out.println("TODO: Oma.gesundEssen(Essen e);");
	//}
	public void spazieren() {
		System.out.println("TODO: Oma.spazieren();");
	}
	public void musikHoeren() {
		System.out.println("TODO: Oma.musikHoeren();");
	}
	public void spielen(Raetsel r) {
		// evtl. Sudoku-Raetsel?
		System.out.println("TODO: Oma.spielen(Raetsel r);");
	}
	*/
	
}
