package de.unikassel.projectoma.helper;

import de.unikassel.projectoma.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import net.frakbot.imageviewex.Converters;
import net.frakbot.imageviewex.ImageViewEx;

public class ImageHelper {

	private static Context appContext;
	private static Activity mainActivity;
	private static ImageViewEx mainImage;
	private static String selectedImg;
	private static byte[] gifImage;
	private static ProgressDialog loadingDialog;

	/**
	 * This method sets a new animated gif based on the given parameters.
	 * 
	 * @param img The name of the asset to load.
	 * @param duration How long should this gif be played? (0 for infinite)
	 * @param reset Should the default Image be set after finished playing?
	 */
	private static void setOneTimeImg(final String img,final int duration, final boolean reset)
	{
		if(appContext instanceof Context && img.contains(".gif") && !selectedImg.equals(img)){

			selectedImg = img;

			mainActivity.runOnUiThread(new Runnable()
			{
				public void run() 
				{ 
					loadingDialog = new ProgressDialog(mainActivity);
					loadingDialog.setMessage(appContext.getString(R.string.loading));
					loadingDialog.setCancelable(false);
					loadingDialog.show();
				}
			}
			);

			Thread thread = new Thread()
			{
				@Override
				public void run() {
					gifImage = Converters.assetToByteArray(appContext.getAssets(), img);

					mainActivity.runOnUiThread(new Runnable()
					{
						public void run() 
						{ 
							loadingDialog.dismiss();
							mainImage.setSource(gifImage);
							mainImage.setFramesDuration(1);
						}
					});

					if(duration == 0)
					{
						return;
					}

					long endTimeMillis = System.currentTimeMillis() + duration * 1000;
					while (true)
					{
						if (System.currentTimeMillis() > endTimeMillis)
						{
							if(reset)
							{
								setGrandmaTypeInCar();
							}
							else
							{
								mainImage.stop();
							}

							return;
						}
					}
				}
			};
			thread.start();

		}
	}

	private static void setInfiniteImg(final String img)
	{
		setOneTimeImg(img, 0, false);
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
		setOneTimeImg("grandma_cook.gif", 10, true);
	}

	public static void setGrandmaTypeWashing()
	{
		setOneTimeImg("grandma_washing.gif",10, true);
	}

	public static void setGrandmaTypeInCar()
	{
		setInfiniteImg("grandma_incar.gif");
	}

	public static void setGrandmaTypeMedicine()
	{
		setOneTimeImg("grandma_medicine.gif", 5, true);
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
		setOneTimeImg("grandma_gosleep.gif", 3, false);
	}

	public static void setGrandmaTypeCleanCar()
	{
		setOneTimeImg("grandma_clean_car.gif", 5, true);
	}

	public static void setGrandmaTypeWalk()
	{
		setInfiniteImg("grandma_take_walk.gif");
	}
	
	public static void setGrandmaTypeEat()
	{
		setOneTimeImg("grandma_food_heavy.gif", 5, true);
	}
	
	public static void setGrandmaTypeDrink()
	{
		setOneTimeImg("grandma_drink.gif", 5, true);
	}
	
	public static void setGrandmaTypeDoCleanDishes()
	{
		setOneTimeImg("grandma_do_clean_dishes.gif", 10, true);
	}
	
	public static void setGrandmaTypeGoShopping()
	{
		setOneTimeImg("grandma_take_walk.gif", 10, true);
	}
}
