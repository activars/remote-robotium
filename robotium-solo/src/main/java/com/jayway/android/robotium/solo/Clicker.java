package com.jayway.android.robotium.solo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.Assert;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * This class contains various click methods. Examples are: clickOnButton(),
 * clickOnText(), clickOnScreen().
 * 
 * @author Renas Reda, renas.reda@jayway.com
 * 
 */

class Clicker {
	
	private final String LOG_TAG = "Robotium";
	private final ActivityUtils activityUtils;
	private final ViewFetcher viewFetcher;
	private final Scroller scroller;
	private final Instrumentation inst;
	private final RobotiumUtils robotiumUtils;
	private int countMatches=0;
	private final int PAUS = 500;
	private final int MINIPAUS = 300;
	private final int TIMEOUT = 10000;
	private final int CLICKTIMEOUT = 5000;	
	private final static int MENU = 7;


	/**
	 * Constructs this object.
	 * 
	 * @param ativityUtils the {@link ActivityUtils} instance.
	 * @param viewFetcher the {@link ViewFetcher} instance.
	 * @param scroller the {@link Scroller} instance.
	 * @param robotiumUtils the {@link RobotiumUtils} instance.
	 * @param inst the {@link Instrumentation} instance.
	 */

	public Clicker(ActivityUtils ativityUtils, ViewFetcher viewFetcher,
			Scroller scroller, RobotiumUtils robotiumUtils, Instrumentation inst) {

		this.activityUtils = ativityUtils;
		this.viewFetcher = viewFetcher;
		this.scroller = scroller;
		this.robotiumUtils = robotiumUtils;
		this.inst = inst;
	}
	
	/**
	 * Clicks on a specific coordinate on the screen
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */
	
	public void clickOnScreen(float x, float y) {
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis();
		MotionEvent event = MotionEvent.obtain(downTime, eventTime,
				MotionEvent.ACTION_DOWN, x, y, 0);
		MotionEvent event2 = MotionEvent.obtain(downTime, eventTime,
				MotionEvent.ACTION_UP, x, y, 0);
		try{
			inst.sendPointerSync(event);
			inst.sendPointerSync(event2);
		}catch(Throwable e){
			Assert.assertTrue("Click can not be completed! Something is in the way e.g. the keyboard.", false);
		}
	}
	
	/**
	 * Long clicks a specific coordinate on the screen
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */
	
	public void clickLongOnScreen(float x, float y) {
		long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
        try{
        	inst.sendPointerSync(event);
        }catch(Throwable e){
        	Assert.assertTrue("Click can not be completed! Something is in the way e.g. the keyboard.", false);
        }
        inst.waitForIdleSync();
        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 
                x + ViewConfiguration.getTouchSlop() / 2,
                y + ViewConfiguration.getTouchSlop() / 2, 0);
        inst.sendPointerSync(event);
        inst.waitForIdleSync();
        RobotiumUtils.sleep((int)(ViewConfiguration.getLongPressTimeout() * 1.5f));
        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
        inst.sendPointerSync(event);
        inst.waitForIdleSync();
		RobotiumUtils.sleep(PAUS);

	}
	
	
	/**
	 * Method used to click on a specific view.
	 *
	 * @param view the view that should be clicked
	 *
	 */
	
	public void clickOnScreen(View view) {
		clickOnScreen(view, false);
	}
	
	/**
	 * Private method used to click on a specific view.
	 *
	 * @param view the view that should be clicked
	 * @param longClick true if the click should be a long click
	 *
	 */
	
	private void clickOnScreen(View view, boolean longClick) {
		int[] xy = new int[2];
		long now = System.currentTimeMillis();
		final long endTime = now + CLICKTIMEOUT;
		while ((!view.isShown() || view.isLayoutRequested()) && now < endTime) {
			RobotiumUtils.sleep(PAUS);
			now = System.currentTimeMillis();
		}
		if(!view.isShown())
			Assert.assertTrue("View can not be clicked!", false);
		view.getLocationOnScreen(xy);
		while (xy[1] + 10> activityUtils.getCurrentActivity().getWindowManager() 
				.getDefaultDisplay().getHeight() && scroller.scrollDown()) {
			view.getLocationOnScreen(xy);
		}
		RobotiumUtils.sleep(MINIPAUS);
		view.getLocationOnScreen(xy);
		final int viewWidth = view.getWidth();
		final int viewHeight = view.getHeight();
		final float x = xy[0] + (viewWidth / 2.0f);
		final float y = xy[1] + (viewHeight / 2.0f);

		if (longClick)
			clickLongOnScreen(x, y);
		else
			clickOnScreen(x, y);
	}
	
	/**
	 * Method used to long click on a specific view.
	 *
	 * @param view the view that should be long clicked
	 *
	 */
	
	public void clickLongOnScreen(View view) {
		clickOnScreen(view, true);
		
	}
	
	/**
	 * This method is used to click on a specific text view displaying a certain
	 * text.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 *
	 */
	
	public void clickOnText(String text) {
		clickOnText(text, false, 1, true);
	}
	
	/**
	 * This method is used to click on a specific text view displaying a certain
	 * text.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param matches the match that should be clicked on 
	 *
	 */
	
	public void clickOnText(String text, int matches) {
		clickOnText(text, false, matches, true);
	}
	
	/**
	 * This method is used to click on a specific text view displaying a certain
	 * text.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param matches the match that should be clicked on 
	 * @param scroll true if scrolling should be performed
	 *
	 */
	
	public void clickOnText(String text, int matches, boolean scroll) {
		clickOnText(text, false, matches, scroll);
	}
	
	
	/**
	 * This method is used to long click on a specific text view and then selecting
	 * an item from the menu that appears.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param index the index of the menu item that should be pressed
	 *
	 */
	
	public void clickLongOnTextAndPress(String text, int index)
	{
		clickOnText(text, true, 0, true);
		try{
			inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		}catch(Throwable e){
			Assert.assertTrue("Can not press the context menu!", false);
		}
		for(int i = 0; i < index; i++)
		{
			RobotiumUtils.sleep(MINIPAUS);
			inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		}
		inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
	}
	
	/**
	 * Long clicks on a specific text view. ClickOnText() can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 *
	 */
	
	public void clickLongOnText(String text)
	{
		clickOnText(text, true, 1, true);
	}
	
	/**
	 * Long clicks on a specific text view. ClickOnText() can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 *
	 */
	
	public void clickLongOnText(String text, int matches)
	{
		clickOnText(text, true, matches, true);
	}
	
	/**
	 * Long clicks on a specific text view. ClickOnText() can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param match the match that should be clicked on 
	 * @param scroll true if scrolling should be performed
	 *
	 */
	
	public void clickLongOnText(String text, int matches, boolean scroll)
	{
		clickOnText(text, true, matches, scroll);
	}
	
	/**
	 * Clicks on a menu item with a given text
	 * @param text the menu text that should be clicked on. Regular expressions are supported 
	 * 
	 */
	
	public void clickOnMenuItem(String text)
	{	
		RobotiumUtils.sleep(PAUS);
		inst.waitForIdleSync();
		try{
			robotiumUtils.sendKey(MENU);
		}catch(Throwable e){
			Assert.assertTrue("Can not open the menu!", false);
		}
		clickOnText(text);
	}
	
	/**
	 * Clicks on a menu item with a given text
	 * 
	 * @param text the menu text that should be clicked on. Regular expressions are supported 
	 * @param subMenu true if the menu item could be located in a sub menu
	 * 
	 */
	
	public void clickOnMenuItem(String text, boolean subMenu)
	{
		RobotiumUtils.sleep(PAUS);
		inst.waitForIdleSync();
		TextView textMore = null;
		int [] xy = new int[2];
		int x = 0;
		int y = 0;
		
		try{
		robotiumUtils.sendKey(MENU);
		}catch(Throwable e){
			Assert.assertTrue("Can not open the menu!", false);
		}
		if(subMenu && (viewFetcher.getCurrentTextViews(null).size() > 5) && !robotiumUtils.waitForText(text, 1, 1500, false)){
			for(TextView textView : viewFetcher.getCurrentTextViews(null)){
				x = xy[0];
				y = xy[1];
				textView.getLocationOnScreen(xy);

				if(xy[0] > x || xy[1] > y)
						textMore = textView;
				}
		}
		if(textMore != null)
			clickOnScreen(textMore);
		
		clickOnText(text);
	}
	
	
	/**
	 * Private method that is used to click on a specific text view displaying a certain
	 * text.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param longClick true if the click should be a long click 
	 * @param match the match that should be clicked on 
	 *
	 */

	private void clickOnText(String text, boolean longClick, int match, boolean scroll) {
		Pattern p = Pattern.compile(text);
		Matcher matcher; 
		robotiumUtils.waitForText(text, 0, TIMEOUT, scroll);
		TextView textToClick = null;
		ArrayList <TextView> textViewList = viewFetcher.getCurrentTextViews(null);
		if(match == 0)
			match = 1;
		for(TextView textView : textViewList){
			matcher = p.matcher(textView.getText().toString());
			if(matcher.matches()){	
				countMatches++;
			}
			if (countMatches == match) {
				countMatches = 0;
				textToClick = textView;
				break;
			}
		}
		if (textToClick != null) {
			clickOnScreen(textToClick, longClick);
		} else if (scroll && scroller.scrollDown()) {
			clickOnText(text, longClick, match, scroll);
		} else {
			if (countMatches > 0)
				Assert.assertTrue("There are only " + countMatches + " matches of " + text, false);
			else {
				for (int i = 0; i < textViewList.size(); i++)
					Log.d(LOG_TAG, text + " not found. Have found: " + textViewList.get(i).getText());
				Assert.assertTrue("The text: " + text + " is not found!", false);
			}
			countMatches = 0;
		}
	}
	
	
	
	/**
	 * This method is used to click on a button with a specific index.
	 *
	 * @param index the index number of the button
	 *
	 */
	
	public void clickOnButton(int index) {
		robotiumUtils.waitForIdle();
		try {
			clickOnScreen(viewFetcher.getButton(index));
		}catch (IndexOutOfBoundsException e) {
			Assert.assertTrue("Index is not valid!", false);
		}
	}
	

	
	/**
	 * Method used to click on a button with a given text.
	 *
	 * @param name the name of the button presented to the user. Regular expressions are supported
	 *
	 */
	
	public void clickOnButton(String name) {
		Pattern p = Pattern.compile(name);
		Matcher matcher;
		Button buttonToClick = null;
		robotiumUtils.waitForText(name, 0, TIMEOUT, true);
		ArrayList<Button> buttonList = viewFetcher.getCurrentButtons();
		for(Button button : buttonList){
			matcher = p.matcher(button.getText().toString());
			if(matcher.matches()){	
				buttonToClick = button;
				break;
			}
		}
		if (buttonToClick != null) {
			clickOnScreen(buttonToClick);
		} else if (scroller.scrollDown()){
			clickOnButton(name);
		}else
		{
			for (int i = 0; i < buttonList.size(); i++)
				Log.d(LOG_TAG, name + " not found. Have found: " + buttonList.get(i).getText());
			Assert.assertTrue("Button with the text: " + name + " is not found!", false);
		}

	}
	
	/**
	 * Method used to click on a toggle button with a given text.
	 * 
	 * @param name the name of the toggle button presented to the user. Regular expressions are supported
	 * 
	 */

	public void clickOnToggleButton(String name) {
		Pattern p = Pattern.compile(name);
		Matcher matcher;
		ToggleButton buttonToClick = null;
		robotiumUtils.waitForText(name, 0, TIMEOUT, true);
		ArrayList<ToggleButton> toggleButtonList = viewFetcher
				.getCurrentToggleButtons();
		for(ToggleButton toggleButton : toggleButtonList){
			matcher = p.matcher(toggleButton.getText().toString());
			if (matcher.matches()) {
				buttonToClick = toggleButton;
				break;
			}
		}
		if (buttonToClick != null) {
			clickOnScreen(buttonToClick);
		} else if (scroller.scrollDown()) {
			clickOnButton(name);
		} else {
			for (int i = 0; i < toggleButtonList.size(); i++)
				Log.d(LOG_TAG, name + " not found. Have found: " + toggleButtonList.get(i).getText());
			Assert.assertTrue("ToggleButton with the text: " + name + " is not found!", false);
		}

	}

	
	/**
	 * This method is used to click on an image with a certain index.
	 *
	 * @param index the index of the image to be clicked
	 *
	 */
	
	public void clickOnImage(int index) {
		robotiumUtils.waitForIdle();
		try {
			clickOnScreen(viewFetcher.getCurrentImageViews().get(index));
		} catch (IndexOutOfBoundsException e) {
			Assert.assertTrue("Index is not valid!", false);
		}
	}
	
	/**
	 * This method is used to click on an image button with a certain index.
	 *
	 * @param index the index of the image button to be clicked
	 *
	 */
	
	public void clickOnImageButton(int index) {
		robotiumUtils.waitForIdle();
		try {
			clickOnScreen(viewFetcher.getCurrentImageButtons().get(index));
		} catch (IndexOutOfBoundsException e) {
			Assert.assertTrue("Index is not valid!", false);
		}
	}
	
	/**
	 * This method is used to click on a radio button with a certain index.
	 *
	 * @param index the index of the radio button to be clicked
	 *
	 */
	
	public void clickOnRadioButton(int index) {
		robotiumUtils.waitForIdle();
		try {
			clickOnScreen(viewFetcher.getCurrentRadioButtons().get(index));
		} catch (IndexOutOfBoundsException e) {
			Assert.assertTrue("Index is not valid!", false);
		}
	}
	
	/**
	 * This method is used to click on a check box with a certain index.
	 *
	 * @param index the index of the check box to be clicked
	 *
	 */
	
	public void clickOnCheckBox(int index) {
		robotiumUtils.waitForIdle();
		try {
			clickOnScreen(viewFetcher.getCurrentCheckBoxes().get(index));
		} catch (IndexOutOfBoundsException e) {
			Assert.assertTrue("Index is not valid!", false);
		}
	}
	
	/**
	 * Clicks on an edit text with a certain index.
	 *
	 * @param index the index of the edit text to be clicked
	 *
	 */
	
	public void clickOnEditText(int index) {
		robotiumUtils.waitForIdle();
		try {
			clickOnScreen(viewFetcher.getCurrentEditTexts().get(index));
		} catch (IndexOutOfBoundsException e) {
			Assert.assertTrue("Index is not valid!", false);
		}
	}
	
	/**
	 * Method used to simulate pressing the hard key back
	 * 
	 */
	
	public void goBack() {
		RobotiumUtils.sleep(PAUS);
		inst.waitForIdleSync();
		try {
			inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
			RobotiumUtils.sleep(PAUS);
		} catch (Throwable e) {}
	}
	
	
	
	/**
	 * Method that will click on a certain list line and return the text views that
	 * the list line is showing. Will use the first list it finds.
	 * 
	 * @param line the line that should be clicked
	 * @return an array list of the text views located in the list line
	 */

	public ArrayList<TextView> clickInList(int line) {
		return clickInList(line, 0);
	}
	
	/**
	 * Method that will click on a certain list line on a specified List and 
	 * return the text views that the list line is showing. 
	 * 
	 * @param line the line that should be clicked
	 * @param index the index of the list. E.g. Index 1 if two lists are available
	 * @return an array list of the text views located in the list line
	 */
	
	public ArrayList<TextView> clickInList(int line, int index) {	
		robotiumUtils.waitForIdle();
		RobotiumUtils.sleep(PAUS);
		long now = System.currentTimeMillis();
		final long endTime = now + CLICKTIMEOUT;
		int size = viewFetcher.getCurrentListViews().size();
		while((size > 0 && size <index+1) && now < endTime)
		{
			RobotiumUtils.sleep(PAUS);
		}
		if (now > endTime)
			Assert.assertTrue("No ListView with index " + index + " is available", false);

		ArrayList<TextView> textViews = null;
		try{
			textViews = viewFetcher.getCurrentTextViews(viewFetcher
					.getCurrentListViews().get(index));
		}catch(IndexOutOfBoundsException e){
			Assert.assertTrue("Index is not valid!", false);
		}
		ArrayList<TextView> textViewGroup = new ArrayList<TextView>();
		int myLine = 0;
		if(textViews !=null ){
			for (int i = 0; i < textViews.size(); i++) {
				View view = viewFetcher.getListItemParent(textViews.get(i));
				try {
					if (view.equals(viewFetcher.getListItemParent(textViews.get(i + 1)))) {
						textViewGroup.add(textViews.get(i));
					} else {
						textViewGroup.add(textViews.get(i));
						myLine++;
						if (myLine == line)
							break;
						else
							textViewGroup.clear();
					}
				} catch (IndexOutOfBoundsException e) {textViewGroup.add(textViews.get(i));}
			}
		}
		if (textViewGroup.size() != 0)
			clickOnScreen(textViewGroup.get(0));
		return textViewGroup;
	}
}
