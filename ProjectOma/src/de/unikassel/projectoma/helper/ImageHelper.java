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

	public interface Callback {
		/**
		 * Gets called when a setter finishes its work.
		 */ 
		public void onFinished();
	}

	/**
	 * This method sets a new animated gif based on the given parameters.
	 * 
	 * @param img The name of the asset to load.
	 * @param duration How long should this gif be played? (0 for infinite)
	 * @param callback The callback gets called when the duration time is exceeded 
	 * 					or instantly after setting the image on infinite images
	 */
	private static void setOneTimeImg(final String img,final int duration, final Callback callback)
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
						if(callback != null)
						{
							callback.onFinished();
						}
						
						return;
					}

					long endTimeMillis = System.currentTimeMillis() + duration * 1000;
					while (true)
					{
						if (System.currentTimeMillis() > endTimeMillis)
						{

							mainImage.stop();

							if(callback != null)
							{
								callback.onFinished();
							}

							return;
						}
					}
				}
			};
			thread.start();

		}
	}

	private static void setInfiniteImg(final String img, Callback callback)
	{
		setOneTimeImg(img, 0, callback);
	}


	public static void setContext(Context c)
	{
		appContext = c.getApplicationContext();
		mainActivity = (Activity)c;
		mainImage = (ImageViewEx)mainActivity.findViewById(R.id.imageViewEx1);
		selectedImg = "";
	}

	public static void setGrandmaTypeHelp(Callback callback)
	{
		setInfiniteImg("grandma_help.gif", callback);
	}

	public static void setGrandmaTypeMusic(Callback callback)
	{
		setInfiniteImg("grandma_music_short.gif", callback);
	}

	public static void setGrandmaTypeHungry(Callback callback)
	{
		setInfiniteImg("grandma_hungry.gif", callback);
	}

	public static void setGrandmaTypeIll(Callback callback)
	{
		setInfiniteImg("grandma_ill.gif", callback);
	}

	public static void setGrandmaTypeCooking(Callback callback)
	{
		setOneTimeImg("grandma_cook.gif", 10, callback);
	}

	public static void setGrandmaTypeWashing(final Callback callback)
	{
		setOneTimeImg("grandma_washing.gif",10, callback);
	}

	public static void setGrandmaTypeInCar(Callback callback)
	{
		setInfiniteImg("grandma_incar.gif", callback);
	}

	public static void setGrandmaTypeMedicine(Callback callback)
	{
		setOneTimeImg("grandma_medicine.gif", 6, callback);
	}

	public static void setGrandmaTypeCleanDishes(Callback callback)
	{
		setInfiniteImg("grandma_clean_dishes.gif", callback);
	}

	public static void setGrandmaTypeShopping(Callback callback)
	{
		setInfiniteImg("grandma_shopping.gif", callback);
	}

	public static void setGrandmaTypeTired(Callback callback)
	{
		setInfiniteImg("grandma_tired.gif", callback);
	}

	public static void setGrandmaTypeThirsty(Callback callback)
	{
		setInfiniteImg("grandma_thirsty.gif", callback);
	}

	public static void setGrandmaTypePills(Callback callback)
	{
		setInfiniteImg("grandma_pills.gif", callback);
	}

	public static void setGrandmaTypeSleep(Callback callback)
	{
		setOneTimeImg("grandma_gosleep.gif", 3, callback);
	}

	public static void setGrandmaTypeCleanCar(Callback callback)
	{
		setOneTimeImg("grandma_clean_car.gif", 5, callback);
	}

	public static void setGrandmaTypeWalk(Callback callback)
	{
		setInfiniteImg("grandma_take_walk.gif", callback);
	}

	public static void setGrandmaTypeEat(Callback callback)
	{
		setOneTimeImg("grandma_food_heavy.gif", 6, callback);
	}

	public static void setGrandmaTypeDrink(Callback callback)
	{
		setOneTimeImg("grandma_drink.gif", 5, callback);
	}

	public static void setGrandmaTypeDoCleanDishes(Callback callback)
	{
		setOneTimeImg("grandma_do_clean_dishes.gif", 10, callback);
	}

	public static void setGrandmaTypeGoShopping(Callback callback)
	{
		setOneTimeImg("grandma_take_walk.gif", 10, callback);
	}
}
