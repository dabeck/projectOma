package de.unikassel.projectoma.model;

import java.util.List;

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
	
	/* Wuensche */
	private List<Article> wishes;
	/* Einkaufsliste */
	private List<Article> shoppingList;
	
	/* Speisekammer */
	private List<Food> pantry;
	/* Vorratskammer */
	private List<Article> inventory;
	/* Kleiderschrank */
	private List<Clothing> wardrobe;
	
	
	
	
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
		this.name = name;
	}
	public Grandma withName(String name) {
		this.name = name;
		return this;
	}
	
	public LevelType getLevel() {
		return level;
	}
	public void setLevel(LevelType level) {
		this.level = level;
	}
	public Grandma withLevel(LevelType level) {
		this.level = level;
		return this;
	}
	
	public Article getCurrentAction() {
		return currentAction;
	}
	public void setCurrentAction(Article currentAction) {
		this.currentAction = currentAction;
	}
	public Grandma withCurrentAction(Article currentAction) {
		this.currentAction = currentAction;
		return this;
	}
	
	public List<Article> getWishes() {
		return wishes;
	}
	public void setWishes(List<Article> wishes) {
		this.wishes = wishes;
	}
	public Grandma withWishes(List<Article> wishes) {
		this.setWishes(wishes);
		return this;
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
	
	public List<Clothing> getWardrobe() {
		return wardrobe;
	}
	public void setWardrobe(List<Clothing> wardrobe) {
		this.wardrobe = wardrobe;
	}
	public Grandma withWardrobe(List<Clothing> wardrobe) {
		this.wardrobe = wardrobe;
		return this;
	}
	
	
	public Grandma() {
	}

	public Grandma(String name, LevelType level) {
		this.name = name;
		this.level = level;
	}
	
	
	
	public void eat(Food f) {
		System.out.println("TODO: Oma.eat(Food f);");
	}
	public void drink(Drink d) {
		System.out.println("TODO: Oma.drink(Drink d);");
	}
	public void cure(Medicine m) {
		System.out.println("TODO: Oma.cure(Medicine m);");
	}
	public void buy(Article a) {
		System.out.println("TODO: Oma.buy(Artikel a);");
	}
	
	
	
	public void clean() {
		System.out.println("TODO: Oma.clean();");
	}
	public void washClothes() {
		System.out.println("TODO: Oma.washClothes();");
	}
	public void washDishes() {
		System.out.println("TODO: Oma.washDishes();");
	}
	public void sleep() {
		System.out.println("TODO: Oma.sleep();");
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
