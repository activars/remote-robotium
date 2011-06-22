package com.jayway.android.robotium.solo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public interface ISolo {

	public final static int LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; // 0
	public final static int PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; // 1
	public final static int RIGHT = KeyEvent.KEYCODE_DPAD_RIGHT;
	public final static int LEFT = KeyEvent.KEYCODE_DPAD_LEFT;
	public final static int UP = KeyEvent.KEYCODE_DPAD_UP;
	public final static int DOWN = KeyEvent.KEYCODE_DPAD_DOWN;
	public final static int ENTER = KeyEvent.KEYCODE_ENTER;
	public final static int MENU = KeyEvent.KEYCODE_MENU;
	public final static int DELETE = KeyEvent.KEYCODE_DEL;
	public final static int CLOSED = 0;
	public final static int OPENED = 1;

	/**
	 * Returns the ActivityMonitor used by Robotium.
	 * 
	 * @return the ActivityMonitor used by Robotium
	 */

	public abstract ActivityMonitor getActivityMonitor();

	/**
	 * Returns an ArrayList of all the View objects located in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link View} objects located in the focused window
	 *
	 */

	public abstract ArrayList<View> getViews();

	/**
	 * Returns an ArrayList of the View objects contained in the parent View.
	 *
	 * @param parent the parent view from which to return the views
	 * @return an {@code ArrayList} of the {@link View} objects contained in the given {@code View}
	 *
	 */

	public abstract ArrayList<View> getViews(View parent);

	/**
	 * Returns the absolute top parent View for a given View.
	 *
	 * @param view the {@link View} whose top parent is requested
	 * @return the top parent {@link View}
	 *
	 */

	public abstract View getTopParent(View view);

	/**
	 * Clears the value of an EditText.
	 * 
	 * @param index the index of the {@link EditText} that should be cleared. 0 if only one is available
	 *
	 */

	public abstract void clearEditText(int index);

	/**
	 * Clears the value of an EditText.
	 * 
	 * @param editText the {@link EditText} that should be cleared
	 *
	 */

	public abstract void clearEditText(EditText editText);

	/**
	 * Waits for a text to be shown. Default timeout is 20 seconds. 
	 * 
	 * @param text the text to wait for
	 * @return {@code true} if text is shown and {@code false} if it is not shown before the timeout
	 * 
	 */

	public abstract boolean waitForText(String text);

	/**
	 * Waits for a text to be shown. 
	 * 
	 * @param text the text to wait for
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait 
	 * @return {@code true} if text is shown and {@code false} if it is not shown before the timeout
	 * 
	 */

	public abstract boolean waitForText(String text,
			int minimumNumberOfMatches, long timeout);

	/**
	 * Waits for a text to be shown. 
	 * 
	 * @param text the text to wait for
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 * @return {@code true} if text is shown and {@code false} if it is not shown before the timeout
	 * 
	 */

	public abstract boolean waitForText(String text,
			int minimumNumberOfMatches, long timeout, boolean scroll);

	/**
	 * Waits for a View to be shown. Default timeout is 20 seconds. 
	 * 
	 * @param viewClass the {@link View} class to wait for
	 */

	public abstract <T extends View> boolean waitForView(
			final Class<T> viewClass);

	/**
	 * Waits for a View to be shown.
	 * 
	 * @param viewClass the {@link View} class to wait for
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if view is shown and {@code false} if it is not shown before the timeout
	 */

	public abstract <T extends View> boolean waitForView(
			final Class<T> viewClass, final int minimumNumberOfMatches,
			final int timeout);

	/**
	 * Waits for a View to be shown.
	 * 
	 * @param viewClass the {@link View} class to wait for
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 * @return {@code true} if the {@link View} is shown and {@code false} if it is not shown before the timeout
	 */

	public abstract <T extends View> boolean waitForView(
			final Class<T> viewClass, final int minimumNumberOfMatches,
			final int timeout, final boolean scroll);

	/**
	 * Searches for a text string in the EditText objects currently shown and returns true if found. Will automatically scroll when needed.
	 *
	 * @param text the text to search for
	 * @return {@code true} if an {@link EditText} with the given text is found or {@code false} if it is not found
	 *
	 */

	public abstract boolean searchEditText(String text);

	/**
	 * Searches for a Button with the given text string and returns true if at least one Button
	 * is found. Will automatically scroll when needed. 
	 *
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @return {@code true} if a {@link Button} with the given text is found and {@code false} if it is not found
	 *
	 */

	public abstract boolean searchButton(String text);

	/**
	 * Searches for a Button with the given text string and returns true if at least one Button
	 * is found. Will automatically scroll when needed. 
	 *
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @param onlyVisible {@code true} if only {@link Button} visible on the screen should be searched
	 * @return {@code true} if a {@link Button} with the given text is found and {@code false} if it is not found
	 *
	 */

	public abstract boolean searchButton(String text, boolean onlyVisible);

	/**
	 * Searches for a ToggleButton with the given text string and returns {@code true} if at least one ToggleButton
	 * is found. Will automatically scroll when needed. 
	 *
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @return {@code true} if a {@link ToggleButton} with the given text is found and {@code false} if it is not found
	 *
	 */

	public abstract boolean searchToggleButton(String text);

	/**
	 * Searches for a Button with the given text string and returns {@code true} if the
	 * searched Button is found a given number of times. Will automatically scroll when needed.
	 * 
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @param minimumNumberOfMatches the minimum number of matches expected to be found. {@code 0} matches means that one or more
	 * matches are expected to be found
	 * @return {@code true} if a {@link Button} with the given text is found a given number of times and {@code false}
	 * if it is not found
	 *  
	 */

	public abstract boolean searchButton(String text, int minimumNumberOfMatches);

	/**
	 * Searches for a Button with the given text string and returns {@code true} if the
	 * searched Button is found a given number of times. Will automatically scroll when needed.
	 * 
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @param minimumNumberOfMatches the minimum number of matches expected to be found. {@code 0} matches means that one or more
	 * matches are expected to be found
	 * @param onlyVisible {@code true} if only {@link Button} visible on the screen should be searched
	 * @return {@code true} if a {@link Button} with the given text is found a given number of times and {@code false}
	 * if it is not found
	 *  
	 */

	public abstract boolean searchButton(String text,
			int minimumNumberOfMatches, boolean onlyVisible);

	/**
	 * Searches for a ToggleButton with the given text string and returns {@code true} if the
	 * searched ToggleButton is found a given number of times. Will automatically scroll when needed.
	 * 
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @param minimumNumberOfMatches the minimum number of matches expected to be found. {@code 0} matches means that one or more
	 * matches are expected to be found
	 * @return {@code true} if a {@link ToggleButton} with the given text is found a given number of times and {@code false}
	 * if it is not found
	 *  
	 */

	public abstract boolean searchToggleButton(String text,
			int minimumNumberOfMatches);

	/**
	 * Searches for a text string and returns {@code true} if at least one item
	 * is found with the expected text. Will automatically scroll when needed. 
	 *
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @return {@code true} if the search string is found and {@code false} if it is not found
	 *
	 */

	public abstract boolean searchText(String text);

	/**
	 * Searches for a text string and returns {@code true} if at least one item
	 * is found with the expected text. Will automatically scroll when needed. 
	 *
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @param onlyVisible {@code true} if only texts visible on the screen should be searched
	 * @return {@code true} if the search string is found and {@code false} if it is not found
	 *
	 */

	public abstract boolean searchText(String text, boolean onlyVisible);

	/**
	 * Searches for a text string and returns {@code true} if the searched text is found a given
	 * number of times. Will automatically scroll when needed. 
	 * 
	 * @param text the text to search for. The parameter will be interpreted as a regular expression
	 * @param minimumNumberOfMatches the minimum number of matches expected to be found. {@code 0} matches means that one or more
	 * matches are expected to be found
	 * @return {@code true} if text string is found a given number of times and {@code false} if the text string
	 * is not found
	 *  
	 */

	public abstract boolean searchText(String text, int minimumNumberOfMatches);

	/**
	 * Searches for a text string and returns {@code true} if the searched text is found a given
	 * number of times.
	 * 
	 * @param text the text to search for. The parameter will be interpreted as a regular expression.
	 * @param minimumNumberOfMatches the minimum number of matches expected to be found. {@code 0} matches means that one or more
	 * matches are expected to be found
	 * @param scroll {@code true} if scrolling should be performed
	 * @return {@code true} if text string is found a given number of times and {@code false} if the text string
	 * is not found
	 *  
	 */

	public abstract boolean searchText(String text, int minimumNumberOfMatches,
			boolean scroll);

	/**
	 * Searches for a text string and returns {@code true} if the searched text is found a given
	 * number of times.
	 * 
	 * @param text the text to search for. The parameter will be interpreted as a regular expression.
	 * @param minimumNumberOfMatches the minimum number of matches expected to be found. {@code 0} matches means that one or more
	 * matches are expected to be found
	 * @param scroll {@code true} if scrolling should be performed
	 * @param onlyVisible {@code true} if only texts visible on the screen should be searched
	 * @return {@code true} if text string is found a given number of times and {@code false} if the text string
	 * is not found
	 *  
	 */

	public abstract boolean searchText(String text, int minimumNumberOfMatches,
			boolean scroll, boolean onlyVisible);

	/**
	 * Sets the Orientation (Landscape/Portrait) for the current activity.
	 * 
	 * @param orientation the orientation to be set. <code>Solo.</code>{@link #LANDSCAPE} for landscape or
	 * <code>Solo.</code>{@link #PORTRAIT} for portrait.
	 *
	 */

	public abstract void setActivityOrientation(int orientation);

	/**
	 * Returns an ArrayList of all the opened/active activities.
	 * 
	 * @return an ArrayList of all the opened/active activities
	 *
	 */

	public abstract ArrayList<Activity> getAllOpenedActivities();

	/**
	 * Returns the current Activity.
	 *
	 * @return the current Activity
	 *
	 */

	public abstract Activity getCurrentActivity();

	/**
	 * Asserts that the expected Activity is the currently active one.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param name the name of the {@link Activity} that is expected to be active e.g. {@code "MyActivity"}
	 * 
	 */

	public abstract void assertCurrentActivity(String message, String name);

	/**
	 * Asserts that the expected Activity is the currently active one.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param expectedClass the {@code Class} object that is expected to be active e.g. {@code MyActivity.class}
	 * 
	 */

	@SuppressWarnings("unchecked")
	public abstract void assertCurrentActivity(String message,
			Class expectedClass);

	/**
	 * Asserts that the expected Activity is the currently active one, with the possibility to
	 * verify that the expected Activity is a new instance of the Activity.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param name the name of the activity that is expected to be active e.g. {@code "MyActivity"}
	 * @param isNewInstance {@code true} if the expected {@link Activity} is a new instance of the {@link Activity}
	 * 
	 */

	public abstract void assertCurrentActivity(String message, String name,
			boolean isNewInstance);

	/**
	 * Asserts that the expected Activity is the currently active one, with the possibility to
	 * verify that the expected Activity is a new instance of the Activity.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param expectedClass the {@code Class} object that is expected to be active e.g. {@code MyActivity.class}
	 * @param isNewInstance {@code true} if the expected {@link Activity} is a new instance of the {@link Activity}
	 * 
	 */

	@SuppressWarnings("unchecked")
	public abstract void assertCurrentActivity(String message,
			Class expectedClass, boolean isNewInstance);

	/**
	 * Asserts that the available memory in the system is not low.
	 * 
	 */

	public abstract void assertMemoryNotLow();

	/**
	 * Waits for a Dialog to close.
	 * 
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if the {@link android.app.Dialog} is closed before the timeout and {@code false} if it is not closed
	 * 
	 */

	public abstract boolean waitForDialogToClose(long timeout);

	/**
	 * Simulates pressing the hardware back key.
	 * 
	 */

	public abstract void goBack();

	/**
	 * Clicks on a given coordinate on the screen.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */

	public abstract void clickOnScreen(float x, float y);

	/**
	 * Long clicks a given coordinate on the screen.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */

	public abstract void clickLongOnScreen(float x, float y);

	/**
	 * Long clicks a given coordinate on the screen for a given amount of time.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param time the amount of time to long click
	 *
	 */

	public abstract void clickLongOnScreen(float x, float y, int time);

	/**
	 * Clicks on a Button with a given text. Will automatically scroll when needed. 
	 *
	 * @param name the name of the {@link Button} presented to the user. The parameter will be interpreted as a regular expression
	 *
	 */

	public abstract void clickOnButton(String name);

	/**
	 * Clicks on an ImageButton with a given index.
	 *
	 * @param index the index of the {@link ImageButton} to be clicked. 0 if only one is available
	 *
	 */

	public abstract void clickOnImageButton(int index);

	/**
	 * Clicks on a ToggleButton with a given text.
	 * 
	 * @param name the name of the {@link ToggleButton} presented to the user. The parameter will be interpreted as a regular expression
	 * 
	 */

	public abstract void clickOnToggleButton(String name);

	/**
	 * Clicks on a MenuItem with a given text.
	 * @param text the menu text that should be clicked on. The parameter will be interpreted as a regular expression
	 *
	 */

	public abstract void clickOnMenuItem(String text);

	/**
	 * Clicks on a MenuItem with a given text.
	 * 
	 * @param text the menu text that should be clicked on. The parameter will be interpreted as a regular expression
	 * @param subMenu true if the menu item could be located in a sub menu
	 * 
	 */

	public abstract void clickOnMenuItem(String text, boolean subMenu);

	/**
	 * Presses a MenuItem with a given index. Index {@code 0} is the first item in the
	 * first row, Index {@code 3} is the first item in the second row and
	 * index {@code 6} is the first item in the third row.
	 * 
	 * @param index the index of the {@link android.view.MenuItem} to be pressed
	 * 
	 */

	public abstract void pressMenuItem(int index);

	/**
	 * Presses a MenuItem with a given index. Supports three rows with a given amount
	 * of items. If itemsPerRow equals 5 then index 0 is the first item in the first row, 
	 * index 5 is the first item in the second row and index 10 is the first item in the third row.
	 * 
	 * @param index the index of the {@link android.view.MenuItem} to be pressed
	 * @param itemsPerRow the amount of menu items there are per row.   
	 * 
	 */

	public abstract void pressMenuItem(int index, int itemsPerRow);

	/**
	 * Presses on a Spinner (drop-down menu) item.
	 * 
	 * @param spinnerIndex the index of the {@link Spinner} menu to be used
	 * @param itemIndex the index of the {@link Spinner} item to be pressed relative to the currently selected item
	 * A Negative number moves up on the {@link Spinner}, positive moves down
	 * 
	 */

	public abstract void pressSpinnerItem(int spinnerIndex, int itemIndex);

	/**
	 * Clicks on a given View.
	 *
	 * @param view the {@link View} that should be clicked
	 *
	 */

	public abstract void clickOnView(View view);

	/**
	 * Long clicks on a given View.
	 *
	 * @param view the {@link View} that should be long clicked
	 *
	 */

	public abstract void clickLongOnView(View view);

	/**
	 * Long clicks on a given View for a given amount of time.
	 *
	 * @param view the {@link View} that should be long clicked
	 * @param time the amount of time to long click
	 *
	 */

	public abstract void clickLongOnView(View view, int time);

	/**
	 * Clicks on a View displaying a given
	 * text. Will automatically scroll when needed. 
	 *
	 * @param text the text that should be clicked. The parameter will be interpreted as a regular expression
	 *
	 */

	public abstract void clickOnText(String text);

	/**
	 * Clicks on a View displaying a given text. Will automatically scroll when needed.
	 *
	 * @param text the text that should be clicked. The parameter will be interpreted as a regular expression
	 * @param match the match of the text that should be clicked
	 *
	 */

	public abstract void clickOnText(String text, int match);

	/**
	 * Clicks on a View displaying a given text.
	 *
	 * @param text the text that should be clicked on. The parameter will be interpreted as a regular expression
	 * @param match the match of the text that should be clicked
	 * @param scroll true if scrolling should be performed
	 *
	 */

	public abstract void clickOnText(String text, int match, boolean scroll);

	/**
	 * Long clicks on a given View. Will automatically scroll when needed. {@link #clickOnText(String)} can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked. The parameter will be interpreted as a regular expression
	 *
	 */

	public abstract void clickLongOnText(String text);

	/**
	 * Long clicks on a given View. Will automatically scroll when needed. {@link #clickOnText(String)} can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked. The parameter will be interpreted as a regular expression
	 * @param match the match of the text that should be clicked 
	 *
	 */

	public abstract void clickLongOnText(String text, int match);

	/**
	 * Long clicks on a given View. {@link #clickOnText(String)} can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked. The parameter will be interpreted as a regular expression
	 * @param match the match of the text that should be clicked
	 * @param scroll true if scrolling should be performed
	 *
	 */

	public abstract void clickLongOnText(String text, int match, boolean scroll);

	/**
	 * Long clicks on a given View. {@link #clickOnText(String)} can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked. The parameter will be interpreted as a regular expression
	 * @param match the match of the text that should be clicked 
	 * @param time the amount of time to long click 
	 */

	public abstract void clickLongOnText(String text, int match, int time);

	/**
	 * Long clicks on a given View and then selects
	 * an item from the context menu that appears. Will automatically scroll when needed. 
	 *
	 * @param text the text to be clicked. The parameter will be interpreted as a regular expression
	 * @param index the index of the menu item to be pressed. {@code 0} if only one is available
	 *
	 */

	public abstract void clickLongOnTextAndPress(String text, int index);

	/**
	 * Clicks on a Button with a given index.
	 *
	<<<<<<< HEAD
	<<<<<<< HEAD
	<<<<<<< HEAD
	 * @param index the index number of the button
	=======
	 * @param index the index number of the {@code Button}. 0 if only one is available
	>>>>>>> 3958c76a064fa944ce35111acbd0d8392b21f1bb
	=======
	 * @param index the index number of the {@code Button}. {@code 0} if only one is available
	>>>>>>> a866efbb7db928958bd1b93d33650acae03fd6fd
	=======
	 * @param index the index of the {@link Button} to be clicked. {@code 0} if only one is available
	>>>>>>> 6c668e1cec88f7d1927bd6bb1c3e2169ff861ff2
	 *
	 */

	public abstract void clickOnButton(int index);

	/**
	 * Clicks on a RadioButton with a given index.
	 *
	 * @param index the index of the {@link RadioButton} to be clicked. {@code 0} if only one is available
	 *
	 */

	public abstract void clickOnRadioButton(int index);

	/**
	 * Clicks on a CheckBox with a given index.
	 *
	 * @param index the index of the {@link CheckBox} to be clicked. {@code 0} if only one is available
	 *
	 */

	public abstract void clickOnCheckBox(int index);

	/**
	 * Clicks on an EditText with a given index.
	 *
	 * @param index the index of the {@link EditText} to be clicked. {@code 0} if only one is available
	 *
	 */

	public abstract void clickOnEditText(int index);

	/**
	 * Clicks on a given list line and returns an ArrayList of the TextView objects that
	 * the list line is showing. Will use the first list it finds.
	 * 
	 * @param line the line that should be clicked
	 * @return an {@code ArrayList} of the {@link TextView} objects located in the list line
	 *
	 */

	public abstract ArrayList<TextView> clickInList(int line);

	/**
	 * Clicks on a given list line on a specified list and 
	 * returns an ArrayList of the TextView objects that the list line is showing.
	 * 
	 * @param line the line that should be clicked
	 * @param index the index of the list. 1 if two lists are available
	 * @return an {@code ArrayList} of the {@link TextView} objects located in the list line
	 *
	 */

	public abstract ArrayList<TextView> clickInList(int line, int index);

	/**
	 * Long clicks on a given list line and returns an ArrayList of the TextView objects that
	 * the list line is showing. Will use the first list it finds.
	 * 
	 * @param line the line that should be clicked
	 * @return an {@code ArrayList} of the {@link TextView} objects located in the list line
	 *
	 */
	public abstract ArrayList<TextView> clickLongInList(int line);

	/**
	 * Long clicks on a given list line on a specified list and 
	 * returns an ArrayList of the TextView objects that the list line is showing.
	 * 
	 * @param line the line that should be clicked
	 * @param index the index of the list. 1 if two lists are available
	 * @return an {@code ArrayList} of the {@link TextView} objects located in the list line
	 *
	 */
	public abstract ArrayList<TextView> clickLongInList(int line, int index);

	/**
	 * Long clicks on a given list line on a specified list and 
	 * returns an ArrayList of the TextView objects that the list line is showing.
	 * 
	 * @param line the line that should be clicked
	 * @param index the index of the list. 1 if two lists are available
	 * @param time the amount of time to long click
	 * @return an {@code ArrayList} of the {@link TextView} objects located in the list line
	 *
	 */
	public abstract ArrayList<TextView> clickLongInList(int line, int index,
			int time);

	/**
	 * Simulate touching a given location and dragging it to a new location.
	 *
	 * This method was copied from {@code TouchUtils.java} in the Android Open Source Project, and modified here.
	 *
	 * @param fromX X coordinate of the initial touch, in screen coordinates
	 * @param toX X coordinate of the drag destination, in screen coordinates
	 * @param fromY X coordinate of the initial touch, in screen coordinates
	 * @param toY Y coordinate of the drag destination, in screen coordinates
	 * @param stepCount How many move steps to include in the drag
	 *
	 */

	public abstract void drag(float fromX, float toX, float fromY, float toY,
			int stepCount);

	/**
	 * Scrolls down the screen.
	 *
	 * @return {@code true} if more scrolling can be done and {@code false} if it is at the end of
	 * the screen 
	 *
	 */

	public abstract boolean scrollDown();

	/**
	 * Scrolls up the screen.
	 *
	 * @return {@code true} if more scrolling can be done and {@code false} if it is at the top of
	 * the screen 
	 *
	 */

	public abstract boolean scrollUp();

	/**
	 * Scrolls down a list with a given index.
	 * 
	 * @param index the {@link ListView} to be scrolled. {@code 0} if only one list is available
	 * @return {@code true} if more scrolling can be done
	 * 
	 */

	public abstract boolean scrollDownList(int index);

	/**
	 * Scrolls up a list with a given index.
	 * 
	 * @param index the {@link ListView} to be scrolled. {@code 0} if only one list is available
	 * @return {@code true} if more scrolling can be done
	 * 
	 */

	public abstract boolean scrollUpList(int index);

	/**
	 * Scrolls horizontally.
	 *
	 * @param side the side to which to scroll; {@link #RIGHT} or {@link #LEFT}
	 *
	 */

	public abstract void scrollToSide(int side);

	/**
	 * Sets the date in a DatePicker with a given index.
	 *
	 * @param index the index of the {@link DatePicker}. {@code 0} if only one is available
	 * @param year the year e.g. 2011
	 * @param monthOfYear the month e.g. 03
	 * @param dayOfMonth the day e.g. 10
	 *
	 */

	public abstract void setDatePicker(int index, int year, int monthOfYear,
			int dayOfMonth);

	/**
	 * Sets the date in a given DatePicker.
	 *
	 * @param datePicker the {@link DatePicker} object.
	 * @param year the year e.g. 2011
	 * @param monthOfYear the month e.g. 03
	 * @param dayOfMonth the day e.g. 10
	 *
	 */

	public abstract void setDatePicker(DatePicker datePicker, int year,
			int monthOfYear, int dayOfMonth);

	/**
	 * Sets the time in a TimePicker with a given index.
	 *
	 * @param index the index of the {@link TimePicker}. {@code 0} if only one is available
	 * @param hour the hour e.g. 15
	 * @param minute the minute e.g. 30
	 *
	 */

	public abstract void setTimePicker(int index, int hour, int minute);

	/**
	 * Sets the time in a given TimePicker.
	 *
	 * @param timePicker the {@link TimePicker} object.
	 * @param hour the hour e.g. 15
	 * @param minute the minute e.g. 30
	 *
	 */

	public abstract void setTimePicker(TimePicker timePicker, int hour,
			int minute);

	/**
	 * Sets the progress of a ProgressBar with a given index. Examples are SeekBar and RatingBar.
	 *
	 * @param index the index of the {@link ProgressBar}
	 * @param progress the progress that the {@link ProgressBar} should be set to
	 * 
	 */

	public abstract void setProgressBar(int index, int progress);

	/**
	 * Sets the progress of a given ProgressBar. Examples are SeekBar and RatingBar.
	 *
	 * @param progressBar the {@link ProgressBar}
	 * @param progress the progress that the {@link ProgressBar} should be set to
	 * 
	 */

	public abstract void setProgressBar(ProgressBar progressBar, int progress);

	/**
	 * Sets the status of a SlidingDrawer with a given index. Examples are Solo.CLOSED and Solo.OPENED.
	 *
	 * @param index the index of the {@link SlidingDrawer}
	 * @param status the status that the {@link SlidingDrawer} should be set to
	 * 
	 */

	public abstract void setSlidingDrawer(int index, int status);

	/**
	 * Sets the status of a given SlidingDrawer. Examples are Solo.CLOSED and Solo.OPENED.
	 *
	 * @param slidingDrawer the {@link SlidingDrawer}
	 * @param status the status that the {@link SlidingDrawer} should be set to
	 * 
	 */

	public abstract void setSlidingDrawer(SlidingDrawer slidingDrawer,
			int status);

	/**
	 * Enters text into an EditText with a given index.
	 *
	 * @param index the index of the {@link EditText}. {@code 0} if only one is available
	 * @param text the text string to enter into the {@link EditText} field
	 *
	 */

	public abstract void enterText(int index, String text);

	/**
	 * Enters text into a given EditText.
	 *
	 * @param editText the {@link EditText} to enter text into
	 * @param text the text string to enter into the {@link EditText} field
	 *
	 */

	public abstract void enterText(EditText editText, String text);

	/**
	 * Clicks on an ImageView with a given index.
	 *
	 * @param index the index of the {@link ImageView} to be clicked. {@code 0} if only one is available
	 *
	 */

	public abstract void clickOnImage(int index);

	/**
	 * Returns an EditText with a given index.
	 *
	 * @param index the index of the {@link EditText}. {@code 0} if only one is available
	 * @return the {@link EditText} with a specified index or {@code null} if index is invalid
	 *
	 */

	public abstract EditText getEditText(int index);

	/**
	 * Returns a Button with a given index.
	 *
	 * @param index the index of the {@link Button}. {@code 0} if only one is available
	 * @return the {@link Button} with a specified index or {@code null} if index is invalid
	 *
	 */

	public abstract Button getButton(int index);

	/**
	 * Returns a TextView with a given index.
	 *
	 * @param index the index of the {@link TextView}. {@code 0} if only one is available
	 * @return the {@link TextView} with a specified index or {@code null} if index is invalid
	 *
	 */

	public abstract TextView getText(int index);

	/**
	 * Returns an ImageView with a given index.
	 *
	 * @param index the index of the {@link ImageView}. {@code 0} if only one is available
	 * @return the {@link ImageView} with a specified index or {@code null} if index is invalid
	 *
	 */

	public abstract ImageView getImage(int index);

	/**
	 * Returns an ImageButton with a given index.
	 *
	 * @param index the index of the {@link ImageButton}. {@code 0} if only one is available
	 * @return the {@link ImageButton} with a specified index or {@code null} if index is invalid
	 *
	 */

	public abstract ImageButton getImageButton(int index);

	/**
	 * Returns a TextView which shows a given text. 
	 * 
	 * @param text the text that is shown
	 * @return the {@link TextView} that shows the given text
	 */

	public abstract TextView getText(String text);

	/**
	 * Returns a Button which shows a given text.
	 * 
	 * @param text the text that is shown
	 * @return the {@link Button} that shows the given text
	 */

	public abstract Button getButton(String text);

	/**
	 * Returns an EditText which shows a given text.
	 * 
	 * @param text the text that is shown
	 * @return the {@link EditText} which shows the given text
	 */

	public abstract EditText getEditText(String text);

	/**
	 * Returns a View with a given id. 
	 * 
	 * @param id the R.id of the {@link View} to be returned 
	 * @return a {@link View} with a given id
	 */

	public abstract View getView(int id);

	/**
	 * Returns an ArrayList of the View objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link View} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<View> getCurrentViews();

	/**
	 * Returns an ArrayList of the ImageView objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link ImageView} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<ImageView> getCurrentImageViews();

	/**
	 * Returns an ArrayList of the EditText objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link EditText} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<EditText> getCurrentEditTexts();

	/**
	 * Returns an ArrayList of the ListView objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link ListView}  objects currently shown in the
	 * focused window
	 * 
	 */

	public abstract ArrayList<ListView> getCurrentListViews();

	/**
	 * Returns an ArrayList of the ScrollView objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link ScrollView} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<ScrollView> getCurrentScrollViews();

	/**
	 * Returns an ArrayList of the Spinner objects (drop-down menus) currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link Spinner} objects (drop-down menus) currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<Spinner> getCurrentSpinners();

	/**
	 * Returns an ArrayList of the TextView objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @param parent the parent {@link View} from which the {@link TextView} objects should be returned. {@code null} if
	 * all TextView objects from the currently focused window e.g. Activity should be returned
	 *
	 * @return an {@code ArrayList} of the {@link TextView} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<TextView> getCurrentTextViews(View parent);

	/**
	 * Returns an ArrayList of the GridView objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link GridView} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<GridView> getCurrentGridViews();

	/**
	 * Returns an ArrayList of the Button objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link Button} objects currently shown in the
	 * focused window
	 * 
	 */

	public abstract ArrayList<Button> getCurrentButtons();

	/**
	 * Returns an ArrayList of the ToggleButton objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link ToggleButton} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<ToggleButton> getCurrentToggleButtons();

	/**
	 * Returns an ArrayList of the RadioButton objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link RadioButton} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<RadioButton> getCurrentRadioButtons();

	/**
	 * Returns an ArrayList of the CheckBox objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link CheckBox} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<CheckBox> getCurrentCheckBoxes();

	/**
	 * Returns an ArrayList of the ImageButton objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link ImageButton} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<ImageButton> getCurrentImageButtons();

	/**
	 * Returns an ArrayList of the DatePicker objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link DatePicker} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<DatePicker> getCurrentDatePickers();

	/**
	 * Returns an ArrayList of the TimePicker objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link TimePicker} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<TimePicker> getCurrentTimePickers();

	/**
	 * Returns an ArrayList of the SlidingDrawer objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link SlidingDrawer} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<SlidingDrawer> getCurrentSlidingDrawers();

	/**
	 * Returns an ArrayList of the ProgressBar objects currently shown in the focused 
	 * Activity or Dialog.
	 *
	 * @return an {@code ArrayList} of the {@link ProgressBar} objects currently shown in the
	 * focused window
	 *
	 */

	public abstract ArrayList<ProgressBar> getCurrentProgressBars();

	/**
	 * Checks if a RadioButton with a given index is checked.
	 *
	 * @param index of the {@link RadioButton} to check. {@code 0} if only one is available
	 * @return {@code true} if {@link RadioButton} is checked and {@code false} if it is not checked
	 * 
	 */

	public abstract boolean isRadioButtonChecked(int index);

	/**
	 * Checks if a RadioButton with a given text is checked.
	 *
	 * @param text the text that the {@link RadioButton} shows
	 * @return {@code true} if a {@link RadioButton} with the given text is checked and {@code false} if it is not checked
	 *
	 */

	public abstract boolean isRadioButtonChecked(String text);

	/**
	 * Checks if a CheckBox with a given index is checked.
	 * 
	 * @param index of the {@link CheckBox} to check. {@code 0} if only one is available
	 * @return {@code true} if {@link CheckBox} is checked and {@code false} if it is not checked
	 * 
	 */

	public abstract boolean isCheckBoxChecked(int index);

	/**
	 * Checks if a ToggleButton with a given text is checked.
	 *
	 * @param text the text that the {@link ToggleButton} shows
	 * @return {@code true} if a {@link ToggleButton} with the given text is checked and {@code false} if it is not checked
	 *
	 */

	public abstract boolean isToggleButtonChecked(String text);

	/**
	 * Checks if a ToggleButton with a given index is checked.
	 * 
	 * @param index of the {@link ToggleButton} to check. {@code 0} if only one is available
	 * @return {@code true} if {@link ToggleButton} is checked and {@code false} if it is not checked
	 * 
	 */

	public abstract boolean isToggleButtonChecked(int index);

	/**
	 * Checks if a CheckBox with a given text is checked.
	 *
	 * @param text the text that the {@link CheckBox} shows
	 * @return {@code true} if a {@link CheckBox} with the given text is checked and {@code false} if it is not checked
	 *
	 */

	public abstract boolean isCheckBoxChecked(String text);

	/**
	 * Checks if the given text is checked.
	 *
	 * @param text the text that the {@link CheckedTextView} or {@link CompoundButton} objects show
	 * @return {@code true} if the given text is checked and {@code false} if it is not checked
	 */

	public abstract boolean isTextChecked(String text);

	/**
	 * Checks if a given text is selected in any Spinner located in the current screen.
	 *
	 * @param text the text that is expected to be selected
	 * @return {@code true} if the given text is selected in any {@link Spinner} and false if it is not
	 * 
	 */

	public abstract boolean isSpinnerTextSelected(String text);

	/**
	 * Checks if a given text is selected in a given Spinner. 
	 *
	 * @param index the index of the spinner to check. {@code 0} if only one spinner is available
	 * @param text the text that is expected to be selected
	 * @return true if the given text is selected in the given {@link Spinner} and false if it is not
	 */

	public abstract boolean isSpinnerTextSelected(int index, String text);

	/**
	 * Sends a key: Right, Left, Up, Down, Enter, Menu or Delete.
	 * 
	 * @param key the key to be sent. Use {@code Solo.}{@link #RIGHT}, {@link #LEFT}, {@link #UP}, {@link #DOWN}, 
	 * {@link #ENTER}, {@link #MENU}, {@link #DELETE}
	 * 
	 */

	public abstract void sendKey(int key);

	/**
	 * Returns to the given Activity.
	 *
	 * @param name the name of the {@link Activity} to return to, e.g. {@code "MyActivity"}
	 *
	 */

	public abstract void goBackToActivity(String name);

	/**
	 * Waits for the given Activity.
	 *
	 * @param name the name of the {@link Activity} to wait for e.g. {@code "MyActivity"}
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if {@link Activity} appears before the timeout and {@code false} if it does not
	 * 
	 */

	public abstract boolean waitForActivity(String name, int timeout);

	/**
	 * Returns a localized string.
	 * 
	 * @param resId the resource ID for the string
	 * @return the localized string
	 *
	 */

	public abstract String getString(int resId);

	/**
	 * Robotium will sleep for a specified time.
	 * 
	 * @param time the time in milliseconds that Robotium should sleep 
	 * 
	 */

	public abstract void sleep(int time);

	/**
	 *
	 * All activites that have been active are finished.
	 *
	 */

	public abstract void finalize() throws Throwable;

}