package de.unikassel.projectoma.helper;

import de.unikassel.projectoma.R;
import android.app.Activity;
import android.content.Context;
import net.frakbot.imageviewex.Converters;
import net.frakbot.imageviewex.ImageViewEx;

public class ImageHelper {

	private static Context appContext;
	private static Activity mainActivity;
	private static ImageViewEx mainImage;
	
	private static void changeImg(String img)
	{
		if(appContext instanceof Context && img.length() >= 4){
			mainImage.setSource(Converters.assetToByteArray(appContext.getAssets(), img));
			mainImage.setFramesDuration(1);
		}
	}
	
	public static void setContext(Context c)
	{
		appContext = c.getApplicationContext();
		mainActivity = (Activity)c;
		mainImage = (ImageViewEx)mainActivity.findViewById(R.id.imageViewEx1);
	}
	
	public static void setGrandmaTypeHelp()
	{
		changeImg("grandma_help.gif");
	}
	
	public static void setGrandmaTypeMusic()
	{
		changeImg("grandma_music_short.gif");
	}
	
	public static void setGrandmaTypeHungry()
	{
		changeImg("grandma_hungry.gif");
	}

	public static void setGrandmaTypeIll()
	{
		changeImg("grandma_ill.gif");
	}
	
	public static void setGrandmaTypeCooking()
	{
		changeImg("grandma_cook.gif");
	}
	
	public static void setGrandmaTypeWashing()
	{
		changeImg("grandma_washing.gif");
	}
	
	public static void setGrandmaTypeInCar()
	{
		changeImg("grandma_incar.gif");
	}
	
	public static void setGrandmaTypeMedicine()
	{
		changeImg("grandma_medicine.gif");
	}
	
	public static void setGrandmaTypeCleanDishes()
	{
		changeImg("grandma_clean_dishes.gif");
	}
	
	public static void setGrandmaTypeShopping()
	{
		changeImg("grandma_shopping.gif");
	}
	
	public static void setGrandmaTypeTired()
	{
		changeImg("grandma_tired.gif");
	}
	
	public static void setGrandmaTypeThirsty()
	{
		changeImg("grandma_thirsty.gif");
	}
	
	public static void setGrandmaTypePills()
	{
		changeImg("grandma_pills.gif");
	}
}
