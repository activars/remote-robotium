package com.jayway.android.robotium.solo;

import java.util.ArrayList;
import junit.framework.Assert;
import android.app.Activity;
import android.app.Instrumentation;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

class RobotiumUtils {	
	
	private ViewFetcher viewFetcher;
	private Searcher searcher;
	private ActivityUtils activityUtils;
	private Instrumentation inst;
	private final int TIMEOUT = 20000;
	private final int PAUS = 500;
	private final static int RIGHT = 2;
	private final static int LEFT = 3;
	private final static int UP = 4;
	private final static int DOWN = 5;
	private final static int ENTER = 6;
	private final static int MENU = 7;
	private final static int DELETE = 8;
	
	/**
	 * Constructs this object.
	 * 
	 * @param activityUtils the {@link Activity} instance.
	 * @param searcher the {@link Searcher} instance.
	 * @param viewFetcher the {@link ViewFetcher} instance.
	 * @param inst the {@link Instrumentation} instance.
	 */
	
	public RobotiumUtils(ActivityUtils activityUtils, Searcher searcher,
			ViewFetcher viewFetcher, Instrumentation inst) {
		this.activityUtils = activityUtils;
		this.searcher = searcher;
		this.viewFetcher = viewFetcher;
		this.inst = inst;
	}
	
	
	/**
	 * Used to trigger a sleep.
	 *
	 * @param time the length of the sleep
	 *
	 */
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {}
	}
	
	
	 /**
     * Clears the value of an edit text
     * 
     * @param index the index of the edit text that should be cleared
     */
	
	public void clearEditText(int index)
	{
		waitForIdle();    
		try{
			final EditText	editText = viewFetcher.getCurrentEditTexts().get(index);
			if(editText != null){
				activityUtils.getCurrentActivity().runOnUiThread(new Runnable()
				{
					public void run()
					{
						editText.setText("");
					}
				});
			}
		}catch(Throwable e){
			Assert.assertTrue("No edit text with index " + index + " is found", false);
		}
	}
    
    /**
	 * Private method used instead of instrumentation.waitForIdleSync().
	 *
	 */
   
    public void waitForIdle() {
		sleep(PAUS);
		long startTime = System.currentTimeMillis();
		long timeout = 10000;
		long endTime = startTime + timeout;
		View decorView;
		ArrayList<View> touchItems = new ArrayList<View>();
		while (System.currentTimeMillis() <= endTime) {
			decorView = viewFetcher.getActiveDecorView();
			if(decorView != null)
			touchItems = decorView.getTouchables();
			if (touchItems.size() > 0)  
				break;
			RobotiumUtils.sleep(PAUS);
		}
	}
    
    /**
	 * Waits for a text to be shown. Default timeout is 20 seconds. 
	 * 
	 * @param text the text that needs to be shown
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */
	
	public boolean waitForText(String text) {

		return waitForText(text, 0, TIMEOUT, true);
	}
	
	 /**
	 * Waits for a text to be shown. Default timeout is 20 seconds. 
	 * 
	 * @param text the text that needs to be shown
	 * @param matches the number of matches of text that must be shown. 0 means any number of matches
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */
	
	public boolean waitForText(String text, int matches) {

		return waitForText(text, matches, TIMEOUT, true);
	}
	
	 /**
	 * Waits for a text to be shown. 
	 * 
	 * @param text the text that needs to be shown
	 * @param matches the number of matches of text that must be shown. 0 means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */
	
	public boolean waitForText(String text, int matches, long timeout)
	{
		return waitForText(text, matches, timeout, true);
	}

	
	 /**
	 * Waits for a text to be shown. 
	 * 
	 * @param text the text that needs to be shown
	 * @param matches the number of matches of text that must be shown. 0 means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait
	 * @param scroll true if scrolling should be performed
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */
	
	public boolean waitForText(String text, int matches, long timeout, boolean scroll)
    {
		long now = System.currentTimeMillis();
        final long endTime = now + timeout;

        while (!searcher.searchForText(text, matches, scroll) && !searcher.searchForEditText(text, scroll) && now < endTime)
        {
        	now = System.currentTimeMillis();	
        }    
        if (now > endTime)
        	return false;
        
       return true;
    }
	
	/**
	 * Checks if a radio button with a given index is checked
	 * 
	 * @param index of the radio button to check
	 * @return true if radio button is checked and false if it is not checked
	 */
	
	public boolean isRadioButtonChecked(int index)
	{
		ArrayList<RadioButton> radioButtonList = viewFetcher.getCurrentRadioButtons();
		if(index < 0 || index > radioButtonList.size()-1)
			Assert.assertTrue("No radio button with index " + index + " is found", false);
		return radioButtonList.get(index).isChecked();
	}
	
	/**
	 * Checks if a check box with a given index is checked
	 * 
	 * @param index of the check box to check
	 * @return true if check box is checked and false if it is not checked
	 */
	
	public boolean isCheckBoxChecked(int index)
	{
		ArrayList<CheckBox> checkBoxList = viewFetcher.getCurrentCheckBoxes();
		if(index < 0 || index > checkBoxList.size()-1)
			Assert.assertTrue("No checkbox with index " + index + " is found", false);
		return checkBoxList.get(index).isChecked();
	}
	
	/**
	 * Tells Robotium to send a key: Right, Left, Up, Down or Enter
	 * @param key the key to be sent. Use Solo.RIGHT/LEFT/UP/DOWN/MENU/ENTER/DELETE
	 * 
	 */
	
	public void sendKey(int key)
	{
		RobotiumUtils.sleep(PAUS);
		inst.waitForIdleSync();
		try{
			switch (key) {
			case RIGHT:
				inst.sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
				break;
			case LEFT:
				inst.sendCharacterSync(KeyEvent.KEYCODE_DPAD_LEFT);
				break;
			case UP:
				inst.sendCharacterSync(KeyEvent.KEYCODE_DPAD_UP);
				break;
			case DOWN:
				inst.sendCharacterSync(KeyEvent.KEYCODE_DPAD_DOWN);
				break;
			case ENTER:
				inst.sendCharacterSync(KeyEvent.KEYCODE_ENTER);
				break;
			case MENU:
				inst.sendCharacterSync(KeyEvent.KEYCODE_MENU);
				break;
			case DELETE:
				inst.sendCharacterSync(KeyEvent.KEYCODE_DEL);
				break;
			default:
				break;
			}
		}catch(Throwable e){
			Assert.assertTrue("Can not complete action!", false);
		}

	}


	

}
