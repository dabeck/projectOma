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

	public static void setGrandmaRequestHelp(Callback callback)
	{
		setImage("grandma_help.gif", callback);
	}

	public static void setGrandmaRequestFood(Callback callback)
	{
		setImage("grandma_hungry.gif", callback);
	}

	public static void setGrandmaRequestMedicine(Callback callback)
	{
		setImage("grandma_pills.gif", callback);
	}

	public static void setGrandmaActionCooking(Callback callback)
	{
		setImage("grandma_cook.gif", callback);
	}

	public static void setGrandmaActionWashing(final Callback callback)
	{
		setImage("grandma_washing.gif", callback);
	}

	public static void setGrandmaActionInCar(Callback callback)
	{
		setImage("grandma_incar.gif", callback);
	}

	public static void setGrandmaActionMedicine(Callback callback)
	{
		setImage("grandma_medicine.gif", callback);
	}

	public static void setGrandmaRequestCleanDishes(Callback callback)
	{
		setImage("grandma_clean_dishes.gif", callback);
	}

	public static void setGrandmaRequestShopping(Callback callback)
	{
		setImage("grandma_shopping.gif", callback);
	}

	public static void setGrandmaRequestSleep(Callback callback)
	{
		setImage("grandma_tired.gif", callback);
	}

	public static void setGrandmaRequestDrink(Callback callback)
	{
		setImage("grandma_thirsty.gif", callback);
	}

	public static void setGrandmaActionSleep(Callback callback)
	{
		setImage("grandma_gosleep.gif", callback);
	}
	
	public static void setGrandmaRequestCleanCar(Callback callback)
	{
		setImage("grandma_request_clean.gif", callback);
	}

	public static void setGrandmaActionCleanCar(Callback callback)
	{
		setImage("grandma_clean_car.gif", callback);
	}

	public static void setGrandmaActionWalk(Callback callback)
	{
		setImage("grandma_take_walk.gif", callback);
	}

	public static void setGrandmaActionEatHeavy(Callback callback)
	{
		setImage("grandma_food_heavy.gif", callback);
	}
	
	public static void setGrandmaActionEatLight(Callback callback)
	{
		setImage("grandma_food_light.gif", callback);
	}

	public static void setGrandmaActionDrink(Callback callback)
	{
		setImage("grandma_drink.gif", callback);
	}
	
	public static void setGrandmaActionDrinkHot(Callback callback)
	{
		setImage("grandma_drink_hot.gif", callback);
	}

	public static void setGrandmaActionCleanDishes(Callback callback)
	{
		setImage("grandma_action_clean_dishes.gif", callback);
	}

	public static void setGrandmaActionShopping(Callback callback)
	{
		setImage("grandma_action_shopping.gif", callback);
	}
	
	public static void setGrandmaRequestDress(Callback callback)
	{
		setImage("grandma_request_dress.gif", callback);
	}
	
	public static void setGrandmaActionMusic(Callback callback)
	{
	    setImage("grandma_music_short.gif", callback);
	}
}
