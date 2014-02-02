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
	private static String selectedImg;
	private static byte[] gifImage;

	private static void setInfiniteImg(String img)
	{
		if(appContext instanceof Context && img.contains(".gif") && !selectedImg.equals(img)){
			selectedImg = img;


			Thread thread = new Thread() {
				@Override
				public void run() {
					gifImage = Converters.assetToByteArray(appContext.getAssets(), selectedImg);

					mainActivity.runOnUiThread(new Runnable() //run on ui thread
					{
						public void run() 
						{ 
							mainImage.setSource(gifImage);
							mainImage.setFramesDuration(1);
						}
					});
				}
			};
			thread.start();
		}
	}

	private static void setOneTimeImg(String img)
	{
		if(appContext instanceof Context && img.contains(".gif") && !selectedImg.equals(img)){
			selectedImg = img;

			Thread thread = new Thread() {
				@Override
				public void run() {
					gifImage = Converters.assetToByteArray(appContext.getAssets(), selectedImg);

					mainActivity.runOnUiThread(new Runnable() //run on ui thread
					{
						public void run() 
						{ 
							mainImage.setSource(gifImage);
							mainImage.setFramesDuration(1);
						}
					});

					long endTimeMillis = System.currentTimeMillis() + 5000;	//Sets the image for 5 seconds
					while (true) {
						// method logic
						if (System.currentTimeMillis() > endTimeMillis) {
							setGrandmaTypeInCar();

							return;
						}
					}
				}
			};
			thread.start();

		}
	}



	public static void setContext(Context c)
	{
		appContext = c.getApplicationContext();
		mainActivity = (Activity)c;
		mainImage = (ImageViewEx)mainActivity.findViewById(R.id.imageViewEx1);
		selectedImg = "";
	}

	public static void setGrandmaTypeHelp()
	{
		setInfiniteImg("grandma_help.gif");
	}

	public static void setGrandmaTypeMusic()
	{
		setInfiniteImg("grandma_music_short.gif");
	}

	public static void setGrandmaTypeHungry()
	{
		setInfiniteImg("grandma_hungry.gif");
	}

	public static void setGrandmaTypeIll()
	{
		setInfiniteImg("grandma_ill.gif");
	}

	public static void setGrandmaTypeCooking()
	{
		setOneTimeImg("grandma_cook.gif");
	}

	public static void setGrandmaTypeWashing()
	{
		setInfiniteImg("grandma_washing.gif");
	}

	public static void setGrandmaTypeInCar()
	{
		setInfiniteImg("grandma_incar.gif");
	}

	public static void setGrandmaTypeMedicine()
	{
		setInfiniteImg("grandma_medicine.gif");
	}

	public static void setGrandmaTypeCleanDishes()
	{
		setInfiniteImg("grandma_clean_dishes.gif");
	}

	public static void setGrandmaTypeShopping()
	{
		setInfiniteImg("grandma_shopping.gif");
	}

	public static void setGrandmaTypeTired()
	{
		setInfiniteImg("grandma_tired.gif");
	}

	public static void setGrandmaTypeThirsty()
	{
		setInfiniteImg("grandma_thirsty.gif");
	}

	public static void setGrandmaTypePills()
	{
		setInfiniteImg("grandma_pills.gif");
	}

	public static void setGrandmaTypeSleep()
	{
		setInfiniteImg("grandma_gosleep.gif");
	}

	public static void setGrandmaTypeCleanCar()
	{
		setInfiniteImg("grandma_clean_car.gif");
	}

	public static void setGrandmaTypeWalk()
	{
		setInfiniteImg("grandma_take_walk.gif");
	}
}
