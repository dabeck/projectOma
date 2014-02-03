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
	 * @param callback The callback gets called when the image loading did finish
	 */
	private static void setImage(final String img, final Callback callback)
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
							
							if(callback != null)
							{
								callback.onFinished();
							}
						}
					});
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

	public static void setGrandmaTypeHelp(Callback callback)
	{
		setImage("grandma_help.gif", callback);
	}

	public static void setGrandmaTypeMusic(Callback callback)
	{
		setImage("grandma_music_short.gif", callback);
	}

	public static void setGrandmaTypeHungry(Callback callback)
	{
		setImage("grandma_hungry.gif", callback);
	}

	public static void setGrandmaTypeIll(Callback callback)
	{
		setImage("grandma_ill.gif", callback);
	}

	public static void setGrandmaTypeCooking(Callback callback)
	{
		setImage("grandma_cook.gif", callback);
	}

	public static void setGrandmaTypeWashing(final Callback callback)
	{
		setImage("grandma_washing.gif", callback);
	}

	public static void setGrandmaTypeInCar(Callback callback)
	{
		setImage("grandma_incar.gif", callback);
	}

	public static void setGrandmaTypeMedicine(Callback callback)
	{
		setImage("grandma_medicine.gif", callback);
	}

	public static void setGrandmaTypeCleanDishes(Callback callback)
	{
		setImage("grandma_clean_dishes.gif", callback);
	}

	public static void setGrandmaTypeShopping(Callback callback)
	{
		setImage("grandma_shopping.gif", callback);
	}

	public static void setGrandmaTypeTired(Callback callback)
	{
		setImage("grandma_tired.gif", callback);
	}

	public static void setGrandmaTypeThirsty(Callback callback)
	{
		setImage("grandma_thirsty.gif", callback);
	}

	public static void setGrandmaTypePills(Callback callback)
	{
		setImage("grandma_pills.gif", callback);
	}

	public static void setGrandmaTypeSleep(Callback callback)
	{
		setImage("grandma_gosleep.gif", callback);
	}

	public static void setGrandmaTypeCleanCar(Callback callback)
	{
		setImage("grandma_clean_car.gif", callback);
	}

	public static void setGrandmaTypeWalk(Callback callback)
	{
		setImage("grandma_take_walk.gif", callback);
	}

	public static void setGrandmaTypeEatHeavy(Callback callback)
	{
		setImage("grandma_food_heavy.gif", callback);
	}
	
	public static void setGrandmaTypeEatLight(Callback callback)
	{
		setImage("grandma_food_light.gif", callback);
	}

	public static void setGrandmaTypeDrink(Callback callback)
	{
		setImage("grandma_drink.gif", callback);
	}

	public static void setGrandmaTypeDoCleanDishes(Callback callback)
	{
		setImage("grandma_do_clean_dishes.gif", callback);
	}

	public static void setGrandmaTypeGoShopping(Callback callback)
	{
		setImage("grandma_take_walk.gif", callback);
	}
}
