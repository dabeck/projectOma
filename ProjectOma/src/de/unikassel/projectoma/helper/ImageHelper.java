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
}
